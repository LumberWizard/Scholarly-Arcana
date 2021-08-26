package lumberwizard.scholarlyarcana.world.item;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.world.level.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            ScholarlyArcana.MODID);

    public static final RegistryObject<Item> ESSENCE_EXTRACTOR = ITEMS.register("essence_extractor",
            () -> new EssenceExtractorItem(new Item.Properties().durability(128).tab(CreativeModeTab.TAB_TOOLS)));
    public static final RegistryObject<Item> ESSENCE_FLASK = ITEMS.register("essence_flask",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));
    public static final RegistryObject<Item> FIRE_FLASK = ITEMS.register("fire_flask",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(ESSENCE_FLASK.get())));
    public static final RegistryObject<Item> WATER_FLASK = ITEMS.register("water_flask",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(ESSENCE_FLASK.get())));
    public static final RegistryObject<Item> AIR_FLASK = ITEMS.register("air_flask",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(ESSENCE_FLASK.get())));
    public static final RegistryObject<Item> EARTH_FLASK = ITEMS.register("earth_flask",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(ESSENCE_FLASK.get())));

    public static final RegistryObject<Item> ZINC_ORE_ITEM = ITEMS.register("zinc_ore",
            () -> new BlockItem(ModBlocks.ZINC_ORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> DEEPSLATE_ZINC_ORE_ITEM = ITEMS.register("deepslate_zinc_ore",
            () -> new BlockItem(ModBlocks.DEEPSLATE_ZINC_ORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));


    public static void registerItems() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}