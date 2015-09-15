package com.poker.dscott.pfatrainer.utils;

/**
 * Created by dscott on 9/14/2015.
 */
public class FormattingUtils {

    public static final String STRONG_BEGIN = "<strong>";
    public static final String STRONG_END = "</strong>";
    public static final String EM_START = "<em>";
    public static final String EM_END = "</em>";
    public static final String RED_TEXT = "<font color='red'>";
    public static final String END_COLORED_TEXT = "</font>";

    public static String strongText(String text) {
        return STRONG_BEGIN + text + STRONG_END;
    }

    public static String foldText(String text) {
        return EM_START + text + EM_END;
    }

    public static String raiseText(String text) {
        return RED_TEXT + text + END_COLORED_TEXT;
    }

}
