package lumberwizard.scholarlyarcana.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MageEquipmentProvider implements ICapabilitySerializable<CompoundTag> {

    private ISpellCostModifier spellCostModifier;
    private final LazyOptional<ISpellCostModifier> modifierOptional = LazyOptional.of(() -> spellCostModifier);
    private IMageArmor mageArmor;
    private final LazyOptional<IMageArmor> mageArmorOptional = LazyOptional.of(() -> mageArmor);

    public MageEquipmentProvider() {

    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ModCapabilities.MAGE_ARMOR) {
            return mageArmorOptional.cast();
        }
        if (cap == ModCapabilities.SPELL_COST_MODIFIER) {
            return modifierOptional.cast();
        }
        return LazyOptional.empty();
    }

    public void setSpellCostModifier(double modifier) {
        spellCostModifier = new SpellCostModifier(modifier);
    }

    public void initializeMageArmor() {
        mageArmor = new MageArmor();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        mageArmorOptional.ifPresent(cap -> tag.putInt("MageArmor", cap.getMageArmorLevel()));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("MageArmor")) {
            mageArmor.setMageArmorLevel(nbt.getInt("MageArmor"));
        }
    }

    public void invalidate() {
        modifierOptional.invalidate();
        mageArmorOptional.invalidate();
    }
}
