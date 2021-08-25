package lumberwizard.scholarlyarcana.event;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.world.entity.spell.FireboltEntity;
import lumberwizard.scholarlyarcana.world.entity.spell.ModEntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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
            item.shrink(1);
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

}
