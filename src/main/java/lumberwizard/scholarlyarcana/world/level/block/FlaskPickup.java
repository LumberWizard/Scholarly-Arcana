package lumberwizard.scholarlyarcana.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public interface FlaskPickup {
    ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state);

    Optional<SoundEvent> getPickupSound();
}
