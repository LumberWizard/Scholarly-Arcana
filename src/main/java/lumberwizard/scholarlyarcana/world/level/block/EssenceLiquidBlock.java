package lumberwizard.scholarlyarcana.world.level.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class EssenceLiquidBlock extends Block {


    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL;
    protected final FlowingFluid fluid;
    private final List<FluidState> stateCache;
    public static final VoxelShape STABLE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    public static final ImmutableList<Direction> POSSIBLE_FLOW_DIRECTIONS = ImmutableList.of(Direction.DOWN, Direction.SOUTH, Direction.NORTH, Direction.EAST, Direction.WEST);

    public EssenceLiquidBlock(Supplier<? extends FlowingFluid> fluidSupplier, Properties properties) {
        super(properties);
        this.fluid = null;
        this.stateCache = Lists.newArrayList();
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(0)));
        this.supplier = fluidSupplier;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return ctx.isAbove(STABLE_SHAPE, pos, true) && state.getValue(LEVEL) == 0 && ctx.canStandOnFluid(level.getFluidState(pos.above()), getFluid()) ? STABLE_SHAPE : Shapes.empty();
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getFluidState().isRandomlyTicking();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random rand) {
        state.getFluidState().randomTick(level, pos, rand);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter p_54705_, BlockPos p_54706_, PathComputationType p_54707_) {
        //TODO: implement betterly
        return !this.getFluid().is(FluidTags.LAVA);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        int i = state.getValue(LEVEL);
        if (!fluidStateCacheInitialized) initFluidStateCache();
        return this.stateCache.get(Math.min(i, 8));
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction side) {
        return adjacentState.getFluidState().getType().isSame(this.getFluid());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return Collections.emptyList();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return Shapes.empty();
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState p_54757_, boolean p_54758_) {
        level.getLiquidTicks().scheduleTick(pos, state.getFluidState().getType(), this.getFluid().getTickDelay(level));
    }

    public BlockState updateShape(BlockState state, Direction side, BlockState adjacentState, LevelAccessor level, BlockPos pos, BlockPos adjacentPos) {
        if (state.getFluidState().isSource() || adjacentState.getFluidState().isSource()) {
            level.getLiquidTicks().scheduleTick(pos, state.getFluidState().getType(), this.getFluid().getTickDelay(level));
        }

        return super.updateShape(state, side, adjacentState, level, pos, adjacentPos);
    }

    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighborPos, boolean p_54714_) {
        level.getLiquidTicks().scheduleTick(pos, state.getFluidState().getType(), this.getFluid().getTickDelay(level));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    // Forge start
    private final java.util.function.Supplier<? extends net.minecraft.world.level.material.Fluid> supplier;

    public FlowingFluid getFluid() {
        return (FlowingFluid) supplier.get();
    }

    private boolean fluidStateCacheInitialized = false;

    protected synchronized void initFluidStateCache() {
        if (fluidStateCacheInitialized == false) {
            this.stateCache.add(getFluid().getSource(false));

            for (int i = 1; i < 8; ++i)
                this.stateCache.add(getFluid().getFlowing(8 - i, false));

            this.stateCache.add(getFluid().getFlowing(8, true));
            fluidStateCacheInitialized = true;
        }
    }

}
