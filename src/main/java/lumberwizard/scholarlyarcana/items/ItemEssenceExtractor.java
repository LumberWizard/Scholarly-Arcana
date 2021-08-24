package lumberwizard.scholarlyarcana.items;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ItemEssenceExtractor extends Item {

    public ItemEssenceExtractor(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (ctx.getHand() == InteractionHand.OFF_HAND) return super.useOn(ctx);
        Level level = ctx.getLevel();
        BlockPos position = ctx.getClickedPos();
        Block clickedBlock = level.getBlockState(position).getBlock();
        Player player = ctx.getPlayer();
        ItemStack essenceExtractor = ctx.getItemInHand();
        ItemStack offHandItem = player.getOffhandItem();
        if (offHandItem.getItem() != ModItems.ESSENCE_FLASK.get()) return InteractionResult.FAIL;
        if (clickedBlock == Blocks.STONE || clickedBlock == Blocks.FIRE) {
            if (!level.isClientSide()) {
                ItemUtils.createFilledResult(offHandItem, player, new ItemStack(clickedBlock == Blocks.STONE ?
                        ModItems.EARTH_FLASK.get() : ModItems.FIRE_FLASK.get()));
                essenceExtractor.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(ctx.getHand()));
                level.destroyBlock(position, false, player);
            }
            if (clickedBlock == Blocks.FIRE) {
                player.playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(ctx);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (hand == InteractionHand.OFF_HAND) return super.use(level, player, hand);
        ItemStack offHandItem = player.getOffhandItem();
        ItemStack essenceExtractor = player.getItemInHand(hand);
        HitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (offHandItem.getItem() != ModItems.ESSENCE_FLASK.get())
            return InteractionResultHolder.fail(essenceExtractor);
        if (hitResult.getType() == HitResult.Type.MISS) {
            if (!level.isClientSide()) {
                ItemUtils.createFilledResult(offHandItem, player, new ItemStack(ModItems.AIR_FLASK.get()));
                essenceExtractor.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            }
            player.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
            return InteractionResultHolder.success(essenceExtractor);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockResult = (BlockHitResult) hitResult;
            BlockPos position = blockResult.getBlockPos();
            BlockState clickedBlockstate = level.getBlockState(position);
            Block clickedBlock = clickedBlockstate.getBlock();
            if (clickedBlock == Blocks.WATER) {
                ScholarlyArcana.LOGGER.info(blockResult.getDirection());
                ItemUtils.createFilledResult(offHandItem, player, new ItemStack(ModItems.WATER_FLASK.get()));
                essenceExtractor.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                BucketPickup bucketPickup = (BucketPickup) clickedBlock;
                bucketPickup.pickupBlock(level, position, clickedBlockstate);
                bucketPickup.getPickupSound().ifPresent((sound) -> {
                    player.playSound(sound, 1.0F, 1.0F);
                });
                level.gameEvent(player, GameEvent.FLUID_PICKUP, position);
                return InteractionResultHolder.success(player.getItemInHand(hand));
            }
        }
        return super.use(level, player, hand);
    }
}
