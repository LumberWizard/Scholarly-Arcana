package lumberwizard.scholarlyarcana.world.level.block.entity;

import com.google.common.collect.ImmutableList;
import lumberwizard.scholarlyarcana.world.level.material.EssenceFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class EssenceTankBlockEntity extends BlockEntity {

    private final EssenceTankHandler tankHandler = new EssenceTankHandler(this::setChanged);
    private final LazyOptional<IFluidHandler> tankHandlerOptional = LazyOptional.of(() -> tankHandler);
    private final Map<Direction, IFluidHandler> adjacentFluidHandlers = new HashMap<>();
    private final Map<Direction, EssenceTankHandler.InternalEssenceTank> internalTanks = new HashMap<>();
    private final Map<Direction, LazyOptional<? extends IFluidHandler>> internalTankCaps = new HashMap<>();

    public EssenceTankBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntitiyTypes.ESSENCE_TANK_ENTITY.get(), position, state);
        for (Direction side : Direction.Plane.HORIZONTAL) {
            int index = subtractHorizontal(side, getBlockState().getValue(HorizontalDirectionalBlock.FACING));
            internalTanks.put(side, tankHandler.tanks[index]);
            internalTankCaps.put(side, tankHandler.getTank(index));
        }
    }

    public static BlockEntityTicker<EssenceTankBlockEntity> ticker = (level, pos, state, blockEntity) -> blockEntity.serverTick(level, pos);

    private void serverTick(Level level, BlockPos pos) {
        for (Direction side : Direction.Plane.HORIZONTAL) {
            if (level.getBlockEntity(pos) != null) {
                LazyOptional<IFluidHandler> adjacentCapOptional = level.getBlockEntity(pos).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite());
                adjacentCapOptional.ifPresent(cap -> adjacentFluidHandlers.put(side, cap));
                adjacentCapOptional.addListener(cap -> adjacentFluidHandlers.remove(side));
            }
            if (adjacentFluidHandlers.containsKey(side)) {
                EssenceTankHandler.InternalEssenceTank tank = internalTanks.get(side);
                IFluidHandler adjacentTank = adjacentFluidHandlers.get(side);
                adjacentTank.fill(tank.drain(adjacentTank.fill(tank.drain(1000, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }

    public static int subtractHorizontal(Direction source, Direction rotation) {
        if (source.get2DDataValue() == -1 || rotation == Direction.UP) return source.get2DDataValue();
        if (rotation == Direction.DOWN) return source.getOpposite().get2DDataValue();
        return (source.get2DDataValue() - rotation.get2DDataValue() + 4) % 4;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            if (side == null || side.getAxis().isVertical()) {
                return tankHandlerOptional.cast();
            } else {
                return internalTankCaps.get(side).cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        for (EssenceFluid.EssenceType type : EssenceFluid.EssenceType.values()) {
            CompoundTag typeTankTag = new CompoundTag();
            tankHandler.getTank(type.ordinal()).ifPresent(cap -> cap.writeToNBT(typeTankTag));
            tag.put(type + "Tank", typeTankTag);
        }
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        for (EssenceFluid.EssenceType type : EssenceFluid.EssenceType.values()) {
            if (tag.contains(type + "Tank") && tag.get(type + "Tank").getType() == CompoundTag.TYPE) {
                tankHandler.getTank(type.ordinal()).ifPresent(cap -> cap.readFromNBT((CompoundTag) tag.get(type + "Tank")));
            }
        }
    }

    @Override
    public void invalidateCaps() {
        tankHandler.invalidate();
        tankHandlerOptional.invalidate();
    }

    private static class EssenceTankHandler implements IFluidHandler {

        private static final int CAPACITY = 128000;
        private final List<LazyOptional<InternalEssenceTank>> tankOptionals;
        private final InternalEssenceTank[] tanks = new InternalEssenceTank[EssenceFluid.EssenceType.values().length];

        public EssenceTankHandler(Runnable setChanged) {
            ImmutableList.Builder builder = ImmutableList.builder();
            for (int i = 0; i < tanks.length; i++) {
                Fluid essenceFluid = EssenceFluid.EssenceType.values()[i].getSource().get();
                InternalEssenceTank tank = new InternalEssenceTank(CAPACITY, fluid -> fluid.getFluid() == essenceFluid, setChanged);
                tanks[i] = tank;
                builder.add(LazyOptional.of(() -> tank));
            }
            tankOptionals = builder.build();
            setChanged.run();
        }

        protected LazyOptional<InternalEssenceTank> getTank(int tank) {
            return tankOptionals.get(tank);
        }

        @Override
        public int getTanks() {
            return 4;
        }

        @Nonnull
        @Override
        public FluidStack getFluidInTank(int tank) {
            return tanks[tank].getFluid();
        }

        @Override
        public int getTankCapacity(int tank) {
            return tanks[tank].getCapacity();
        }

        @Override
        public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
            return tanks[tank].isFluidValid(stack);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!(resource.getFluid() instanceof EssenceFluid)) return 0;
            return tanks[((EssenceFluid) resource.getFluid()).getEssenceType().ordinal()].fillInternal(resource, action);
        }

        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return FluidStack.EMPTY;
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return FluidStack.EMPTY;
        }

        protected void invalidate() {
            tankOptionals.forEach(LazyOptional::invalidate);
        }

        private static class InternalEssenceTank extends FluidTank {

            private final Runnable setChanged;

            public InternalEssenceTank(int capacity, Predicate<FluidStack> validator, Runnable setChanged) {
                super(capacity, validator);
                this.setChanged = setChanged;
            }

            @Override
            public int fill(FluidStack resource, FluidAction action) {
                return 0;
            }

            private int fillInternal(FluidStack resource, FluidAction action) {
                if (action.execute()) setChanged.run();
                return super.fill(resource, action);
            }

            @Nonnull
            @Override
            public FluidStack drain(FluidStack resource, FluidAction action) {
                if (action.execute()) setChanged.run();
                return super.drain(resource, action);
            }

            @Nonnull
            @Override
            public FluidStack drain(int maxDrain, FluidAction action) {
                if (action.execute()) setChanged.run();
                return super.drain(maxDrain, action);
            }
        }
    }
}
