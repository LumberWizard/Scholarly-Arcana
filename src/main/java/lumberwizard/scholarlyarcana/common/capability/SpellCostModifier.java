package lumberwizard.scholarlyarcana.common.capability;

import java.util.List;

public class SpellCostModifier implements ISpellCostModifier {

    private double modifierComponent;

    public SpellCostModifier(double modifierComponent) {
        this.modifierComponent = modifierComponent;
    }

    @Override
    public double getModifierComponent() {
        return modifierComponent;
    }

}
