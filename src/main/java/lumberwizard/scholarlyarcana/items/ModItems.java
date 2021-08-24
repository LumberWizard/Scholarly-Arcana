package lumberwizard.scholarlyarcana.items;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
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
            () -> new ItemEssenceExtractor(new Item.Properties().durability(128).tab(CreativeModeTab.TAB_TOOLS)));
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

    public static void registerItems() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
