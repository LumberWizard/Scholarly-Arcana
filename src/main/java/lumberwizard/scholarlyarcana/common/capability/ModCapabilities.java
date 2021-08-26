package lumberwizard.scholarlyarcana.common.capability;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ScholarlyArcana.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModCapabilities {

    @CapabilityInject(ISpellCostModifier.class)
    public static final Capability<ISpellCostModifier> SPELL_COST_MODIFIER = null;

    @CapabilityInject(IMageArmor.class)
    public static final Capability<IMageArmor> MAGE_ARMOR = null;

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ISpellCostModifier.class);
        event.register(IMageArmor.class);
    }

}
