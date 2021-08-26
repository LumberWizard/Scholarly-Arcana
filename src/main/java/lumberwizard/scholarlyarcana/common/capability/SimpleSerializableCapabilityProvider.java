package lumberwizard.scholarlyarcana.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SimpleSerializableCapabilityProvider<S extends Tag, T extends SerializableModCapability<?, S>> implements ICapabilitySerializable<S> {

    private T capability;
    private final LazyOptional<T> capabilityOptional = LazyOptional.of(() -> this.capability);

    public SimpleSerializableCapabilityProvider(T capability) {
        this.capability = capability;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == capability.getCapabilityInstance()) {
            return capabilityOptional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public S serializeNBT() {
        return capability.serialize();
    }

    @Override
    public void deserializeNBT(S nbt) {
        capability.deserialize(nbt);
    }

    public void invalidate() {
        capabilityOptional.invalidate();
    }
}
