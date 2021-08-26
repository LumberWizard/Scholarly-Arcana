package lumberwizard.scholarlyarcana.event;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.common.capability.ModCapabilities;
import lumberwizard.scholarlyarcana.common.capability.SpellCostModifier;
import lumberwizard.scholarlyarcana.common.capability.SimpleCapabilityProvider;
import lumberwizard.scholarlyarcana.world.entity.spell.FireboltEntity;
import lumberwizard.scholarlyarcana.world.entity.spell.ModEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ScholarlyArcana.MODID)
public class DebugEvents {

    @SubscribeEvent
    public static void onItemUse(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getPlayer();
        Level level = event.getWorld();
        ItemStack item = event.getItemStack();
        if (item.getItem() == Items.BLAZE_POWDER) {
            if (player.getItemBySlot(EquipmentSlot.HEAD).getCapability(ModCapabilities.SPELL_COST_MODIFIER, null).orElse(new SpellCostModifier(0)).getModifierComponent() < 0.5) {
                item.shrink(1);
            }
            if (item.getCount() == 0) {
                player.getInventory().removeItem(item);
            }
            if (!level.isClientSide()) {
                FireboltEntity firebolt = new FireboltEntity(ModEntityTypes.FIREBOLT.get(), level, player.getX(), player.getEyeY(), player.getZ(), player);
                firebolt.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 4.0F, 0.5F);
                level.addFreshEntity(firebolt);
            }

        }
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack item = event.getObject();
        if (item.getItem() == Items.GOLDEN_HELMET) {
            SimpleCapabilityProvider provider = new SimpleCapabilityProvider(new SpellCostModifier(1));
            event.addCapability(new ResourceLocation(ScholarlyArcana.MODID, "cost_reudction"), provider);
            event.addListener(provider::invalidate);
        }
    }

}
