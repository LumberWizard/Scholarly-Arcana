package lumberwizard.scholarlyarcana.world.level.material;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluids {

    private static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, ScholarlyArcana.MODID);

    public static final RegistryObject<FlowingFluid> FIRE_ESSENCE = FLUIDS.register("fire_essence",
            () -> new EssenceFluid.Source(EssenceFluid.EssenceType.FIRE));
    public static final RegistryObject<FlowingFluid> FLOWING_FIRE_ESSENCE = FLUIDS.register("flowing_fire_essence",
            () -> new EssenceFluid.Flowing(EssenceFluid.EssenceType.FIRE));
    public static final RegistryObject<FlowingFluid> AIR_ESSENCE = FLUIDS.register("air_essence",
            () -> new EssenceFluid.Source(EssenceFluid.EssenceType.AIR));
    public static final RegistryObject<FlowingFluid> FLOWING_AIR_ESSENCE = FLUIDS.register("flowing_air_essence",
            () -> new EssenceFluid.Flowing(EssenceFluid.EssenceType.AIR));
    public static final RegistryObject<FlowingFluid> WATER_ESSENCE = FLUIDS.register("water_essence",
            () -> new EssenceFluid.Source(EssenceFluid.EssenceType.WATER));
    public static final RegistryObject<FlowingFluid> FLOWING_WATER_ESSENCE = FLUIDS.register("flowing_water_essence",
            () -> new EssenceFluid.Flowing(EssenceFluid.EssenceType.WATER));
    public static final RegistryObject<FlowingFluid> EARTH_ESSENCE = FLUIDS.register("earth_essence",
            () -> new EssenceFluid.Source(EssenceFluid.EssenceType.EARTH));
    public static final RegistryObject<FlowingFluid> FLOWING_EARTH_ESSENCE = FLUIDS.register("flowing_earth_essence",
            () -> new EssenceFluid.Flowing(EssenceFluid.EssenceType.EARTH));

    public static void registerFluids() {
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
