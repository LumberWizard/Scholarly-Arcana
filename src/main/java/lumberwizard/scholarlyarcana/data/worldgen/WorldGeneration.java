package lumberwizard.scholarlyarcana.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.world.block.ModBlocks;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Set;

public class WorldGeneration {

    private static final Set<Biome.BiomeCategory> OVERWORLD_BIOME_CATEGORIES = ImmutableSet.of(Biome.BiomeCategory.JUNGLE, Biome.BiomeCategory.BEACH, Biome.BiomeCategory.DESERT, Biome.BiomeCategory.FOREST, Biome.BiomeCategory.EXTREME_HILLS, Biome.BiomeCategory.ICY, Biome.BiomeCategory.MESA, Biome.BiomeCategory.MUSHROOM, Biome.BiomeCategory.OCEAN, Biome.BiomeCategory.PLAINS, Biome.BiomeCategory.RIVER, Biome.BiomeCategory.SAVANNA, Biome.BiomeCategory.SWAMP, Biome.BiomeCategory.TAIGA, Biome.BiomeCategory.UNDERGROUND);

    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_ZINC_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, ModBlocks.ZINC_ORE.get().defaultBlockState()), OreConfiguration.target(OreConfiguration.Predicates.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_ZINC_ORE.get().defaultBlockState()));

    private static ConfiguredFeature zincOre;

    public static void registerFeatures() {
        zincOre = BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(ScholarlyArcana.MODID, "ore_zinc"), Feature.ORE.configured(new OreConfiguration(ORE_ZINC_TARGET_LIST, 7)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(80)).squared().count(12));
    }

    @SubscribeEvent(priority=EventPriority.HIGH)
    public static void loadBiome(BiomeLoadingEvent event) {
        if (OVERWORLD_BIOME_CATEGORIES.contains(event.getCategory())) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(() -> zincOre);
        }
    }

}
