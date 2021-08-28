package lumberwizard.scholarlyarcana.compat.curios;

import lumberwizard.scholarlyarcana.common.capability.ISpellCostModifier;
import lumberwizard.scholarlyarcana.common.capability.ModCapabilities;
import net.minecraft.Util;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MageCurioHelper {

    public static double getSpellCostModifier(Player player) {
        return Util.toStream(player.getCapability(CuriosCapability.INVENTORY, null).resolve())
                .map(ICuriosItemHandler::getCurios)
                .map(Map::values)
                .flatMap(Collection::stream)
                .flatMap(MageCurioHelper::stream)
                .map(stack -> stack.getCapability(ModCapabilities.SPELL_COST_MODIFIER, null))
                .filter(LazyOptional::isPresent)
                .map(LazyOptional::resolve)
                .map(Optional::get)
                .mapToDouble(ISpellCostModifier::getModifierComponent)
                .sum();
    }

    public static Stream<ItemStack> stream(ICurioStacksHandler handler) {
        return IntStream.range(0, handler.getSlots()).mapToObj(handler.getStacks()::getStackInSlot);
    }

}
