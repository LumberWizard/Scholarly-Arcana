package lumberwizard.scholarlyarcana.world.entity.spell;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ScholarlyArcana.MODID);

    public static final RegistryObject<EntityType<FireboltEntity>> FIREBOLT = ENTITIES.register("firebolt",
            () -> EntityType.Builder.<FireboltEntity>of(FireboltEntity::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(ScholarlyArcana.MODID + ":firebolt"));

    public static void registerEntityTypes() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
