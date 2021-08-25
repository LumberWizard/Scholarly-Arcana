package lumberwizard.scholarlyarcana.world.spell;

import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Firebolt extends Spell {

    public Firebolt(){
        super(TargetType.ENTITY);
    }

    @Override
    public InteractionResultHolder<ItemStack> castSpell(Level level, Player player, ItemStack spellbook) {
        return null;
    }
}
