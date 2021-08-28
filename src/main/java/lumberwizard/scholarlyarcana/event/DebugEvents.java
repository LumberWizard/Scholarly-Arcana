package lumberwizard.scholarlyarcana.event;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.common.capability.MageEquipmentProvider;
import lumberwizard.scholarlyarcana.util.MageEquipmentHelper;
import lumberwizard.scholarlyarcana.world.entity.ModEntityTypes;
import lumberwizard.scholarlyarcana.world.entity.spell.FireboltEntity;
import lumberwizard.scholarlyarcana.world.item.ModItems;
import lumberwizard.scholarlyarcana.world.level.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
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
            if (MageEquipmentHelper.getSpellCostModifier(player) < 0.5) {
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
    public static void onItemUseOnBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        ItemStack item = event.getItemStack();
        if (item.getItem() == ModItems.FIRE_FLASK.get() && !level.isClientSide()) {
            level.setBlock(event.getPos(), ModBlocks.FIRE_ESSENCE.get().defaultBlockState(), 11);
        }
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack item = event.getObject();
        if (item.getItem() == Items.GOLDEN_HELMET) {
            MageEquipmentProvider provider = new MageEquipmentProvider(0.4);
            event.addCapability(new ResourceLocation(ScholarlyArcana.MODID, "cost_reudction"), provider);
            event.addListener(provider::invalidate);
        }
    }

}
