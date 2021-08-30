package lumberwizard.scholarlyarcana.world.level.block;

import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nullable;

public final class TickingEntityBlockHelper {

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> checkType, BlockEntityType<E> type, BlockEntityTicker<? super E> ticker) {
        return type == checkType ? (BlockEntityTicker<A>)ticker : null;
    }

}
