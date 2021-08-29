package lumberwizard.scholarlyarcana;

import lumberwizard.scholarlyarcana.data.worldgen.WorldGeneration;
import lumberwizard.scholarlyarcana.world.entity.ModEntityTypes;
import lumberwizard.scholarlyarcana.world.entity.ai.attributes.ModAttributes;
import lumberwizard.scholarlyarcana.world.item.ModItems;
import lumberwizard.scholarlyarcana.world.level.block.ModBlocks;
import lumberwizard.scholarlyarcana.world.level.block.entity.ModBlockEntitiyTypes;
import lumberwizard.scholarlyarcana.world.level.material.ModFluids;
import lumberwizard.scholarlyarcana.world.spell.Spells;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ScholarlyArcana.MODID)
public class ScholarlyArcana {
    public static final String MODID = "scholarlyarcana";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public ScholarlyArcana() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(WorldGeneration.class);

        ModBlocks.registerBlocks();
        ModItems.registerItems();
        ModFluids.registerFluids();
        ModBlockEntitiyTypes.registerBlockEntityTypes();
        ModEntityTypes.registerEntityTypes();
        Spells.registerSpells();
        ModAttributes.registerAtrributes();
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(WorldGeneration::registerFeatures);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {

    }
}
