package lumberwizard.scholarlyarcana.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;

public class MageArmor implements IMageArmor {

    private int level = 0;

    @Override
    public boolean setMageArmorLevel(int level) {
        if (level < 0 || level > 3) {
            return false;
        }
        this.level = level;
        return true;
    }

    @Override
    public int getMageArmorLevel() {
        return level;
    }

    @Override
    public IntTag serialize() {
        return IntTag.valueOf(level);
    }

    @Override
    public void deserialize(IntTag tag) {
        level = tag.getAsInt();
    }
}
