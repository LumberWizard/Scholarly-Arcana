package lumberwizard.scholarlyarcana.world.entity.ai.attributes;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.util.MiscUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = ScholarlyArcana.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModAttributes {

    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ScholarlyArcana.MODID);

    public static final RegistryObject<Attribute> SPELL_COST_MODIFIER = ATTRIBUTES.register("spell_cost_modifier",
            () -> new RangedAttribute(MiscUtils.createTranslationKey("generic", "spell_cost_modifier"), 0.0, 0.0, 1.0));

    public static void registerAtrributes() {
        ATTRIBUTES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, SPELL_COST_MODIFIER.get());
    }
}
