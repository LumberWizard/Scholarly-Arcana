package lumberwizard.scholarlyarcana.world.spell;

import lumberwizard.scholarlyarcana.ScholarlyArcana;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;

public class Spells {

    public static final DeferredRegister<Spell> SPELLS = DeferredRegister.create(Spell.class, ScholarlyArcana.MODID);

    static {
        SPELLS.makeRegistry("spells", RegistryBuilder::new);
    }

    public static final RegistryObject<Spell> FIREBOLT = SPELLS.register("firebolt", () -> new Firebolt());

    public static void registerSpells() {
        SPELLS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }




}
