package lumberwizard.scholarlyarcana.util;

import lumberwizard.scholarlyarcana.ScholarlyArcana;

public class MiscUtils {

    public static String createTranslationKey(String type, String name) {
        return new StringBuilder(type).append('.').append(ScholarlyArcana.MODID).append('.').append(name).toString();
    }

}
