package lumberwizard.scholarlyarcana.common.capability;

public class MageArmor implements IMageArmor {

    private int level;

    public MageArmor(int level) {
        this.level = level;
    }

    public MageArmor() {
        this(0);
    }

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

}
