package lumberwizard.scholarlyarcana.common.capability;

import net.minecraftforge.common.capabilities.Capability;

public interface ModCapability<T> {

    Capability<T> getCapabilityInstance();

}
