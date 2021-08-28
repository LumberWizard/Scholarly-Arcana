package lumberwizard.scholarlyarcana.util;

import net.minecraft.core.Direction;

public class DirectionHelper {

    public static Direction subtractHorizontal(Direction source, Direction rotation) {
        if (source.get2DDataValue() == -1 || rotation == Direction.UP) return source;
        if (rotation == Direction.DOWN) return source.getOpposite();
        return Direction.from2DDataValue((source.get2DDataValue() - rotation.get2DDataValue() + 4) % 4);
    }

}
