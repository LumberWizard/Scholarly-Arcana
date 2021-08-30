package lumberwizard.scholarlyarcana.event;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.world.entity.ModEntityTypes;
import lumberwizard.scholarlyarcana.world.entity.ai.attributes.ModAttributes;
import lumberwizard.scholarlyarcana.world.entity.spell.FireboltEntity;
import lumberwizard.scholarlyarcana.world.item.ModItems;
import lumberwizard.scholarlyarcana.world.level.block.ModBlocks;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = ScholarlyArcana.MODID)
public class DebugEvents {

    @SubscribeEvent
    public static void onItemUse(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        Level level = event.getWorld();
        ItemStack item = event.getItemStack();
        if (item.getItem() == Items.BLAZE_POWDER) {
            if (!level.isClientSide()) {
                if (player.getAttribute(ModAttributes.SPELL_COST_MODIFIER.get()).getValue() < 0.5) {
                    item.shrink(1);
                    if (item.getCount() == 0) {
                        player.getInventory().removeItem(item);
                    }
                }
                FireboltEntity firebolt = new FireboltEntity(ModEntityTypes.FIREBOLT.get(), level, player.getX(), player.getEyeY(), player.getZ(), player);
                firebolt.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 4.0F, 0.5F);
                level.addFreshEntity(firebolt);
            }
        }
        if (item.getItem() == Items.GOLDEN_HELMET) {
            item.addAttributeModifier(ModAttributes.SPELL_COST_MODIFIER.get(), new AttributeModifier(UUID.fromString("CF183459-14F2-4222-B23E-A94171511DE3"), "Spell cost modifier", 0.4, AttributeModifier.Operation.ADDITION), EquipmentSlot.HEAD);
        }
        if (item.getItem() == Items.SLIME_BALL) {
            ScholarlyArcana.LOGGER.info(player.getAttribute(ModAttributes.SPELL_COST_MODIFIER.get()).getValue());
            player.getAttribute(ModAttributes.SPELL_COST_MODIFIER.get()).getModifiers().forEach(modifier -> {
                ScholarlyArcana.LOGGER.info(modifier.getId() + " " + modifier.getAmount());
            });
        }
    }

    @SubscribeEvent
    public static void onItemUseOnBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        ItemStack item = event.getItemStack();
        if (item.getItem() == ModItems.FIRE_FLASK.get() && !level.isClientSide()) {
            level.setBlock(event.getPos(), ModBlocks.FIRE_ESSENCE.get().defaultBlockState(), 11);
        }
    }

}
