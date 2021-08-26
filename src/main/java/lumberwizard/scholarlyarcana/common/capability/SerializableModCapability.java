package lumberwizard.scholarlyarcana.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public interface SerializableModCapability<T, S extends Tag> extends ModCapability<T> {

    S serialize();

    void deserialize(S tag);

}
