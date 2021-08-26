package lumberwizard.scholarlyarcana.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public interface ISpellCostModifier extends ModCapability<ISpellCostModifier> {

    double getModifierComponent();

    @Override
    default Capability<ISpellCostModifier> getCapabilityInstance() {
        return ModCapabilities.SPELL_COST_MODIFIER;
    }

}
