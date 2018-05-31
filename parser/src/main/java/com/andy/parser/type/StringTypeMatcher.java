package com.andy.parser.type;

import android.support.annotation.NonNull;

/**
 * @author wuhaibo
 * create date: 2018/5/31.
 */
public class StringTypeMatcher {

    public static boolean isNumber(@NonNull String input) {
        return input.matches("^[+-]?\\d+(.\\d+)?$");
    }

    public static boolean isByte(@NonNull String input) {
        if (input.isEmpty()) {
            return false;
        }

        try {
            Byte.parseByte(input);
            return true;
        } catch (NumberFormatException ignored) {
            // ignored
            return false;
        }
    }

    public static boolean isShort(@NonNull String input) {
        if (input.isEmpty()) {
            return false;
        }

        try {
            Short.parseShort(input);
            return true;
        } catch (NumberFormatException ignored) {
            // ignored
            return false;
        }
    }

    public static boolean isInt(@NonNull String input) {
        if (input.isEmpty()) {
            return false;
        }

        if (!isNumber(input)) {
            return false;
        }

        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ignored) {
            // ignored
            return false;
        }
    }

    public static boolean isLong(@NonNull String input) {
        if (input.isEmpty()) {
            return false;
        }

        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException ignored) {
            // ignored
            return false;
        }
    }

    public static boolean isFloat(@NonNull String input) {
        if (input.isEmpty()) {
            return false;
        }

        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException ignored) {
            // ignored
            return false;
        }
    }

    public static boolean isDouble(@NonNull String input) {
        if (input.isEmpty()) {
            return false;
        }

        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException ignored) {
            // ignored
            return false;
        }
    }

    /**
     * 是否为Boolean
     * @param str
     * @return
     */
    public static boolean isBoolean(@NonNull String str) {
        return str.toLowerCase().matches("^true$") ||
                str.toLowerCase().matches("^false$");
    }

    /**
     * 是否为普通字符串
     * @param str
     * @return
     */
    public static boolean isString(String str) {
        if (str == null) {
            return false;
        }

        if (isNumber(str)) {
            return false;
        }

        if (JSONTypeMatcher.isJson(str)) {
            return false;
        }

        return true;
    }
}
