package lumberwizard.scholarlyarcana.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class EssenceTankBlockEntity extends BlockEntity {

//    private IFluidHandler fireTank;
//    private IFluidHandler airTank;
//    private IFluidHandler waterTank;
//    private IFluidHandler earthTank;
//
//    private final LazyOptional<IFluidHandler> fireTankOptional = LazyOptional.of(() -> fireTank);
//    private final LazyOptional<IFluidHandler> airTankOptional = LazyOptional.of(() -> airTank);
//    private final LazyOptional<IFluidHandler> waterTankOptional = LazyOptional.of(() -> waterTank);
//    private final LazyOptional<IFluidHandler> earthTankOptional = LazyOptional.of(() -> earthTank);

    public EssenceTankBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntitiyTypes.ESSENCE_TANK_ENTITY.get(), position, state);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        //todo: complete
//        if (cap == #ESSENCE_CAP && side.get2DDataValue() != -1) {
//            return switch (DirectionHelper.subtractHorizontal(side, getBlockState().getValue(HorizontalDirectionalBlock.FACING))) {
//                case NORTH -> fireTankOptional.cast();
//                case WEST -> airTankOptional.cast();
//                case SOUTH -> waterTankOptional.cast();
//                case EAST -> earthTankOptional.cast();
//                default -> super.getCapability(cap, side);
//            };
//        }
        return super.getCapability(cap, side);
    }
}
