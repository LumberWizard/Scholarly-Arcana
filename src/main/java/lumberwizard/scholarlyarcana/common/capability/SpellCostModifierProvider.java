package lumberwizard.scholarlyarcana.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.DoubleTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SpellCostModifierProvider implements ICapabilitySerializable<DoubleTag> {

    private ISpellCostModifier modifier;
    private final LazyOptional<ISpellCostModifier> modifierOptional = LazyOptional.of(() -> this.modifier);

    public SpellCostModifierProvider(double modifierComponent) {
        modifier = new SpellCostModifier(modifierComponent);
    }

    public SpellCostModifierProvider() {
        this(0);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == Capabilities.SPELL_COST_MODIFIER) {
            return modifierOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public DoubleTag serializeNBT() {
        return DoubleTag.valueOf(modifier.getModifierComponent());
    }

    @Override
    public void deserializeNBT(DoubleTag nbt) {
        modifier = new SpellCostModifier(nbt.getAsDouble());
    }

    public void invalidate() {
        modifierOptional.invalidate();
    }
}
