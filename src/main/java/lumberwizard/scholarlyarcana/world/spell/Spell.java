package lumberwizard.scholarlyarcana.world.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class Spell implements IForgeRegistryEntry<Spell> {

    private final TargetType targetType;
    private ResourceLocation registryName;

    protected Spell(TargetType targetType) {
        this.targetType = targetType;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public abstract InteractionResultHolder<ItemStack> castSpell(Level level, Player player, ItemStack spellbook);

    @Override
    public ResourceLocation getRegistryName() {
        return registryName;
    }

    @Override
    public Spell setRegistryName(ResourceLocation registryName) {
        this.registryName = registryName;
        return this;
    }

    @Override
    public Class<Spell> getRegistryType() {
        return Spell.class;
    }

    public enum TargetType {
        BLOCK,
        ENTITY
    }

}
