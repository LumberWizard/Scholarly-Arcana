package lumberwizard.scholarlyarcana.world.item;

import lumberwizard.scholarlyarcana.world.level.block.FlaskPickup;
import lumberwizard.scholarlyarcana.world.level.material.EssenceFluid;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class EssenceFlaskItem extends Item {

    private final Supplier<? extends Fluid> contentFluid;

    public EssenceFlaskItem(Supplier<? extends Fluid> supplier, Properties properties) {
        super(properties);
        contentFluid = supplier;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, this.contentFluid.get() == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        } else {
            BlockPos clickedPos = hitResult.getBlockPos();
            Direction direction = hitResult.getDirection();
            BlockPos fillPos = clickedPos.relative(direction);
            ItemStack singleItemCopy = stack.copy();
            singleItemCopy.setCount(1);
            if (level.mayInteract(player, clickedPos) && player.mayUseItemAt(fillPos, direction, stack)) {
                BlockEntity blockEntity = level.getBlockEntity(clickedPos);
                if (this.contentFluid.get() == Fluids.EMPTY) {
                    if (!player.isShiftKeyDown() && blockEntity != null && blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction).isPresent()) {
                        IFluidHandler tank = blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction).orElseThrow(IllegalStateException::new);
                        IFluidHandlerItem flask = singleItemCopy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElseThrow(() -> new IllegalStateException("Essence flask does not have fluid handler capability, report to mod author!"));
                        int fill = flask.fill(tank.drain(flask.fill(tank.drain(FluidAttributes.BUCKET_VOLUME, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                        ItemStack filled = flask.getContainer();
                        if (fill != 0 && !filled.isEmpty()) {
                            ItemStack filledResultStack = ItemUtils.createFilledResult(stack, player, filled);
                            return InteractionResultHolder.sidedSuccess(filledResultStack, level.isClientSide());
                        }
                        return InteractionResultHolder.fail(stack);
                    }
                    BlockState clickedBlock = level.getBlockState(clickedPos);
                    if (clickedBlock.getBlock() instanceof FlaskPickup flaskPickup) {
                        ItemStack filled = flaskPickup.pickupBlock(level, clickedPos, clickedBlock);
                        if (!filled.isEmpty()) {
                            player.awardStat(Stats.ITEM_USED.get(this));
                            flaskPickup.getPickupSound().ifPresent((sound) -> {
                                player.playSound(sound, 1.0F, 1.0F);
                            });
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, clickedPos);
                            ItemStack filledResultStack = ItemUtils.createFilledResult(stack, player, filled);
                            //TODO: implement?
//                            if (!level.isClientSide()) {
//                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, filled);
//                            }

                            return InteractionResultHolder.sidedSuccess(filledResultStack, level.isClientSide());
                        }
                    }

                    return InteractionResultHolder.fail(stack);
                } else {
                    if (!player.isShiftKeyDown() && blockEntity != null && blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction).isPresent()) {
                        IFluidHandler tank = blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction).orElseThrow(IllegalStateException::new);
                        IFluidHandlerItem flask = singleItemCopy.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElseThrow(() -> new IllegalStateException("Essence flask does not have fluid handler capability, report to mod author!"));
                        int fill = tank.fill(flask.drain(tank.fill(flask.drain(FluidAttributes.BUCKET_VOLUME, IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                        ItemStack emptied = flask.getContainer();
                        if (fill != 0 && !emptied.isEmpty()) {
                            ItemStack emptiedResultStack = ItemUtils.createFilledResult(stack, player, emptied);
                            return InteractionResultHolder.sidedSuccess(emptiedResultStack, level.isClientSide());
                        }
                        return InteractionResultHolder.fail(stack);
                    }
                    BlockState state = level.getBlockState(clickedPos);
                    BlockPos placePos = canBlockContainFluid(level, clickedPos, state) ? clickedPos : fillPos;
                    if (this.emptyContents(player, level, placePos, hitResult)) {
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, placePos, stack);
                        }
                        player.awardStat(Stats.ITEM_USED.get(this));
                        ItemStack emptied = ItemUtils.createFilledResult(stack, player, new ItemStack(ModItems.ESSENCE_FLASK.get()));
                        return InteractionResultHolder.sidedSuccess(emptied, level.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(stack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(stack);
            }
        }
    }

    private boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate) {
        return blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer) blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, this.contentFluid.get());
    }

    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (!(this.contentFluid.get() instanceof FlowingFluid)) {
            return false;
        } else {
            BlockState state = level.getBlockState(pos);
            Block block = state.getBlock();
            Material material = state.getMaterial();
            boolean canReplaceBlock = state.canBeReplaced(this.contentFluid.get());
            boolean canBePlaced = state.isAir() || canReplaceBlock || block instanceof LiquidBlockContainer && ((LiquidBlockContainer) block).canPlaceLiquid(level, pos, state, this.contentFluid.get());
            if (!canBePlaced) {
                return hitResult != null && this.emptyContents(player, level, hitResult.getBlockPos().relative(hitResult.getDirection()), null);
            } else if (block instanceof LiquidBlockContainer && ((LiquidBlockContainer) block).canPlaceLiquid(level, pos, state, contentFluid.get())) {
                ((LiquidBlockContainer) block).placeLiquid(level, pos, state, ((FlowingFluid) this.contentFluid.get()).getSource(false));
                this.playEmptySound(player, level, pos);
                return true;
            } else {
                if (!level.isClientSide() && canReplaceBlock && !material.isLiquid()) {
                    level.destroyBlock(pos, true);
                }
                if (!level.setBlock(pos, this.contentFluid.get().defaultFluidState().createLegacyBlock(), 11) && !state.getFluidState().isSource()) {
                    return false;
                } else {
                    this.playEmptySound(player, level, pos);
                    return true;
                }
            }
        }
    }

    protected void playEmptySound(@Nullable Player player, LevelAccessor level, BlockPos pos) {
        SoundEvent sound = this.contentFluid.get().getAttributes().getEmptySound();
        //TODO: add sound?
        if (sound == null) sound = SoundEvents.BUCKET_EMPTY;
        level.playSound(player, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(player, GameEvent.FLUID_PLACE, pos);
    }

    public Fluid getFluid() {
        return contentFluid.get();
    }

    @Override
    @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new EssenceFlaskFluidCapabilityProvider(stack);
    }

    private static class EssenceFlaskFluidCapabilityProvider implements IFluidHandlerItem, ICapabilityProvider {

        private final LazyOptional<IFluidHandlerItem> holder = LazyOptional.of(() -> this);

        @Nonnull
        private ItemStack container;

        public EssenceFlaskFluidCapabilityProvider(@Nonnull ItemStack container) {
            this.container = container;
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.orEmpty(cap, holder);
        }

        @Nonnull
        public FluidStack getFluid() {
            Item item = container.getItem();
            if (item instanceof EssenceFlaskItem) {
                return new FluidStack(((EssenceFlaskItem) item).getFluid(), FluidAttributes.BUCKET_VOLUME);
            }
            return FluidStack.EMPTY;
        }

        protected void setFluid(@Nonnull FluidStack fluidStack) {
            if (fluidStack.isEmpty() || !(fluidStack.getFluid() instanceof EssenceFluid))
                container = new ItemStack(ModItems.ESSENCE_FLASK.get());
            else
                container = new ItemStack(((EssenceFluid) fluidStack.getFluid()).getFlask());
        }

        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid() instanceof EssenceFluid;
        }

        @Nonnull
        @Override
        public ItemStack getContainer() {
            return container;
        }

        @Override
        public int getTanks() {
            return 1;
        }

        @Nonnull
        @Override
        public FluidStack getFluidInTank(int tank) {
            return getFluid();
        }

        @Override
        public int getTankCapacity(int tank) {
            return FluidAttributes.BUCKET_VOLUME;
        }

        @Override
        public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
            return stack.getFluid() instanceof EssenceFluid;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (container.getCount() != 1 || resource.getAmount() < FluidAttributes.BUCKET_VOLUME || !getFluid().isEmpty() || !canFillFluidType(resource)) {
                return 0;
            }
            if (action.execute()) {
                setFluid(resource);
            }
            return FluidAttributes.BUCKET_VOLUME;
        }

        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (container.getCount() != 1 || resource.getAmount() < FluidAttributes.BUCKET_VOLUME) {
                return FluidStack.EMPTY;
            }
            FluidStack fluidStack = getFluid();
            if (!fluidStack.isEmpty() && fluidStack.isFluidEqual(resource)) {
                if (action.execute()) {
                    setFluid(FluidStack.EMPTY);
                }
                return fluidStack;
            }

            return FluidStack.EMPTY;
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            if (container.getCount() != 1 || maxDrain < FluidAttributes.BUCKET_VOLUME) {
                return FluidStack.EMPTY;
            }

            FluidStack fluidStack = getFluid();
            if (!fluidStack.isEmpty()) {
                if (action.execute()) {
                    setFluid(FluidStack.EMPTY);
                }
                return fluidStack;
            }

            return FluidStack.EMPTY;
        }
    }

}
