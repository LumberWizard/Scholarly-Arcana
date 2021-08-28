package lumberwizard.scholarlyarcana.world.level.block;

import lumberwizard.scholarlyarcana.world.level.block.entity.EssenceTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class EssenceTankBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public EssenceTankBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos position, BlockState state) {
        return new EssenceTankBlockEntity(position, state);
    }
}
