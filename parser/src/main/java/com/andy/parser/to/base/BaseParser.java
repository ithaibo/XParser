package com.andy.parser.to.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.andy.parser.from.json.JSONParser;
import com.andy.parser.type.TypeMatcher;

/**
 * @author wuhaibo
 * create date: 2018/5/30.
 */
public class BaseParser {

    @Nullable
    public static Byte parseByte(@Nullable String input) {
        if (input.isEmpty()) {
            return null;
        }

        if (!TypeMatcher.isByte(input)) {
            return null;
        }

        try {
            return Byte.parseByte(input);
        } catch (NumberFormatException ignored) {
            // ignored
            return null;
        }
    }

    @Nullable
    public static Short parseShort(@Nullable String input) {
        if (input.isEmpty()) {
            return null;
        }

        if (!TypeMatcher.isShort(input)) {
            return null;
        }

        try {
            return Short.parseShort(input);
        } catch (NumberFormatException ignored) {
            // ignored
            return null;
        }
    }

    @Nullable
    public static Integer parseInt(@Nullable String input) {
        if (input.isEmpty()) {
            return null;
        }

        if (!TypeMatcher.isInt(input)) {
            return null;
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ignored) {
            // ignored
            return null;
        }
    }

    @Nullable
    public static Long parseLong(@Nullable String input) {
        if (input.isEmpty()) {
            return null;
        }

        if (!TypeMatcher.isLong(input)) {
            return null;
        }

        try {
            return Long.parseLong(input);
        } catch (NumberFormatException ignored) {
            // ignored
            return null;
        }
    }

    @Nullable
    public static Float parseFloat(@Nullable String input) {
        if (input.isEmpty()) {
            return null;
        }

        if (!TypeMatcher.isFloat(input)) {
            return null;
        }

        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException ignored) {
            // ignored
            return null;
        }
    }

    @Nullable
    public static Double parseDouble(@Nullable String input) {
        if (input.isEmpty()) {
            return null;
        }

        if (!TypeMatcher.isDouble(input)) {
            return null;
        }

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException ignored) {
            // ignored
            return null;
        }
    }

    @Nullable
    public static Bundle parseJsonObject(@Nullable String input) {
        if (input.isEmpty()) {
            return null;
        }

        if (!TypeMatcher.isJsonObject(input)) {
            return null;
        }

        try {
            return JSONParser.parse(input);
        } catch (NumberFormatException ignored) {
            // ignored
            return null;
        }
    }

    public static <T> T parse(@Nullable String input, Class<T> tClass) {
        if (tClass.equals(Byte.class)) {
            return (T) parseByte(input);
        } else if (tClass.equals(Short.class)) {
            return (T) parseShort(input);
        } else if (tClass.equals(Integer.class)) {
            return (T) parseInt(input);
        } else if (tClass.equals(Long.class)) {
            return (T) parseLong(input);
        } else if (tClass.equals(Float.class)) {
            return (T) parseFloat(input);
        } else if (tClass.equals(Double.class)) {
            return (T) parseDouble(input);
        } else if (tClass.equals(Bundle.class)) {
            return (T) JSONParser.parse(input);
        }

        return null;
    }
}
