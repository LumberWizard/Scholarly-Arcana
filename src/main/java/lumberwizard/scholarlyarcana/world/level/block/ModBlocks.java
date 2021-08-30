package lumberwizard.scholarlyarcana.world.level.block;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.world.level.material.ModFluids;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            ScholarlyArcana.MODID);

    public static final RegistryObject<Block> ZINC_ORE = BLOCKS.register("zinc_ore",
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F)));
    public static final RegistryObject<Block> DEEPSLATE_ZINC_ORE = BLOCKS.register("deepslate_zinc_ore",
            () -> new OreBlock(BlockBehaviour.Properties.copy(ZINC_ORE.get()).color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> ESSENCE_TANK = BLOCKS.register("essence_tank",
            () -> new EssenceTankBlock(BlockBehaviour.Properties.of(Material.GLASS).requiresCorrectToolForDrops().strength(1.0F, 5.0F)));
    public static final RegistryObject<Block> FIRE_ESSENCE = BLOCKS.register("fire_essence",
            () -> new EssenceLiquidBlock(ModFluids.FIRE_ESSENCE, BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.FIRE).noCollission().randomTicks().strength(100.0F).lightLevel(state -> 8).noDrops()));
    public static final RegistryObject<Block> AIR_ESSENCE = BLOCKS.register("air_essence",
            () -> new EssenceLiquidBlock(ModFluids.AIR_ESSENCE, BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.COLOR_LIGHT_BLUE).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> WATER_ESSENCE = BLOCKS.register("water_essence",
            () -> new EssenceLiquidBlock(ModFluids.WATER_ESSENCE, BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.FIRE).noCollission().strength(100.0F).noDrops()));
    public static final RegistryObject<Block> EARTH_ESSENCE = BLOCKS.register("earth_essence",
            () -> new EssenceLiquidBlock(ModFluids.EARTH_ESSENCE, BlockBehaviour.Properties.of(Material.WATER).color(MaterialColor.DIRT).noCollission().strength(100.0F).noDrops()));


    public static void registerBlocks() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
