package lumberwizard.scholarlyarcana.common.capability;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SimpleCapabilityProvider<T extends ModCapability<?>> implements ICapabilityProvider {

    private T capability;
    private final LazyOptional<T> capabilityOptional = LazyOptional.of(() -> this.capability);

    public SimpleCapabilityProvider(T capability) {
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

    public void invalidate() {
        capabilityOptional.invalidate();
    }
}
