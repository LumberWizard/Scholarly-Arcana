package lumberwizard.scholarlyarcana.util;

import lumberwizard.scholarlyarcana.common.capability.ISpellCostModifier;
import lumberwizard.scholarlyarcana.common.capability.ModCapabilities;
import lumberwizard.scholarlyarcana.compat.curios.MageCurioHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;

import java.util.Arrays;
import java.util.Optional;

public class MageEquipmentHelper {

    public static double getSpellCostModifier(Player player) {
        double modifier = Arrays.stream(EquipmentSlot.values())
                .map(player::getItemBySlot)
                .map(stack -> stack.getCapability(ModCapabilities.SPELL_COST_MODIFIER))
                .filter(LazyOptional::isPresent)
                .map(LazyOptional::resolve)
                .map(Optional::get)
                .mapToDouble(ISpellCostModifier::getModifierComponent)
                .sum();
        if (ModList.get().isLoaded("curios")) {
            modifier += MageCurioHelper.getSpellCostModifier(player);
        }
        return modifier;
    }

}
