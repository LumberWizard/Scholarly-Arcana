package lumberwizard.scholarlyarcana.common.capability;

import net.minecraft.nbt.IntTag;
import net.minecraftforge.common.capabilities.Capability;

public interface IMageArmor extends SerializableModCapability<IMageArmor, IntTag> {

    boolean setMageArmorLevel(int level);

    int getMageArmorLevel();

    @Override
    default Capability<IMageArmor> getCapabilityInstance() {
        return ModCapabilities.MAGE_ARMOR;
    }

}
