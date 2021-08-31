package lumberwizard.scholarlyarcana.world.item;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import lumberwizard.scholarlyarcana.world.level.block.ModBlocks;
import lumberwizard.scholarlyarcana.world.level.material.ModFluids;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;
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
            () -> new EssenceFlaskItem(Fluids.EMPTY.delegate, new Item.Properties().tab(CreativeModeTab.TAB_BREWING)));
    public static final RegistryObject<Item> FIRE_FLASK = ITEMS.register("fire_flask",
            () -> new EssenceFlaskItem(ModFluids.FIRE_ESSENCE, new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(ESSENCE_FLASK.get())));
    public static final RegistryObject<Item> WATER_FLASK = ITEMS.register("water_flask",
            () -> new EssenceFlaskItem(ModFluids.WATER_ESSENCE, new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(ESSENCE_FLASK.get())));
    public static final RegistryObject<Item> AIR_FLASK = ITEMS.register("air_flask",
            () -> new EssenceFlaskItem(ModFluids.AIR_ESSENCE, new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(ESSENCE_FLASK.get())));
    public static final RegistryObject<Item> EARTH_FLASK = ITEMS.register("earth_flask",
            () -> new EssenceFlaskItem(ModFluids.EARTH_ESSENCE, new Item.Properties().tab(CreativeModeTab.TAB_BREWING).craftRemainder(ESSENCE_FLASK.get())));
    public static final RegistryObject<Item> ROBES_HELMET = ITEMS.register("magical_hat",
            () -> new RobesItem(ModArmorMaterials.ARCANE_THREAD, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT), 0.05));
    public static final RegistryObject<Item> ROBES_CHESTPLATE = ITEMS.register("arcane_robes",
            () -> new RobesItem(ModArmorMaterials.ARCANE_THREAD, EquipmentSlot.CHEST, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT), 0.05));
    public static final RegistryObject<Item> ROBES_LEGGINGS = ITEMS.register("mage_trousers",
            () -> new RobesItem(ModArmorMaterials.ARCANE_THREAD, EquipmentSlot.LEGS, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT), 0.05));
    public static final RegistryObject<Item> ROBES_BOOTS = ITEMS.register("mage_shoes",
            () -> new RobesItem(ModArmorMaterials.ARCANE_THREAD, EquipmentSlot.FEET, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT), 0.05));


    public static final RegistryObject<Item> ZINC_ORE_ITEM = ITEMS.register("zinc_ore",
            () -> new BlockItem(ModBlocks.ZINC_ORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> DEEPSLATE_ZINC_ORE_ITEM = ITEMS.register("deepslate_zinc_ore",
            () -> new BlockItem(ModBlocks.DEEPSLATE_ZINC_ORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> ESSENCE_TANK_ITEM = ITEMS.register("essence_tank",
            () -> new BlockItem(ModBlocks.ESSENCE_TANK.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));


    public static void registerItems() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
