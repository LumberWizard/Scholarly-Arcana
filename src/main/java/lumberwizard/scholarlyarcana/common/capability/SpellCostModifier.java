package lumberwizard.scholarlyarcana.common.capability;

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
