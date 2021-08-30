package lumberwizard.scholarlyarcana.world.level.block.entity;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.world.level.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockEntitiyTypes {

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ScholarlyArcana.MODID);

    public static void registerBlockEntityTypes() {
        BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<BlockEntityType<EssenceTankBlockEntity>> ESSENCE_TANK_ENTITY = BLOCK_ENTITIES.register("essence_tank",
            () -> BlockEntityType.Builder.of(EssenceTankBlockEntity::new, ModBlocks.ESSENCE_TANK.get()).build(null));


}
