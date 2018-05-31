package com.andy.parser;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON中的类型判断
 *
 * Created by Andy on 2018/5/27.
 */

public class TypeMatcher {

    public static boolean isJsonObject(String json) {
        try {
            new JSONObject(json);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static boolean isJsonArray(String json) {
        try {
            new JSONArray(json);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static boolean isJson(String input) {
        return isJsonObject(input) || isJsonArray(input);
    }

    public static <T> boolean is(Class<T> tClass, @NonNull String key,
            @NonNull JSONObject jsonObject) {

        if (tClass == int.class || tClass == Integer.class) {
            return isInt(key, jsonObject);
        } else if (tClass == long.class || tClass == Long.class) {
            return isLong(key, jsonObject);
        } else if (tClass == double.class || tClass == Double.class) {
            return isDouble(key, jsonObject);
        } else if (tClass == String.class) {
            return isString(key, jsonObject);
        } else if (tClass == boolean.class || tClass == Boolean.class) {
            return isBoolean(key, jsonObject);
        } else if (tClass == JSONObject.class) {
            return isJsonObject(key, jsonObject);
        } else if (tClass == JSONArray.class) {
            return isJsonArray(key, jsonObject);
        }

        return false;
    }

    public static boolean isInt(@NonNull String key, @NonNull JSONObject jsonObject) {
        String value;

        try {
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            // ignore
            value = null;
        }

        return value != null && isInt(value);
    }

    public static boolean isLong(@NonNull String key, @NonNull JSONObject jsonObject) {
        String value;

        try {
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            // ignore
            value = null;
        }

        return value != null && isLong(value);

    }

    public static boolean isDouble(@NonNull String key, @NonNull JSONObject jsonObject) {
        String value;

        try {
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            // ignore
            value = null;
        }

        return value != null && isDouble(value);
    }

    public static boolean isString(@NonNull String key, @NonNull JSONObject jsonObject) {
        String value;

        try {
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            // ignore
            value = null;
        }

        return value != null && isString(value);
    }

    public static boolean isBoolean(@NonNull String key, @NonNull JSONObject jsonObject) {
        Boolean value;

        try {
            value = jsonObject.getBoolean(key);
        } catch (JSONException e) {
            // ignore
            value = null;
        }

        return value != null;
    }

    public static boolean isJsonObject(@NonNull String key, @NonNull JSONObject jsonObject) {
        JSONObject value;

        try {
            value = jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            // ignore
            value = null;
        }

        return value != null;
    }

    public static boolean isJsonArray(@NonNull String key, @NonNull JSONObject jsonObject) {
        JSONArray value;

        try {
            value = jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            // ignore
            value = null;
        }

        return value != null;
    }

    public static boolean isIntArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return true;
        }

        String firstItem = null;
        try {
            firstItem = jsonArray.getString(0);
        } catch (JSONException e) {
            // ignored
        }

        return firstItem != null && isInt(firstItem);
    }

    public static boolean isLongArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return true;
        }

        String firstItem = null;
        try {
            firstItem = jsonArray.getString(0);
        } catch (JSONException e) {
            // ignored
        }

        return firstItem != null && isLong(firstItem);
    }

    public static boolean isDoubleArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return true;
        }

        String firstItem = null;
        try {
            firstItem = jsonArray.getString(0);
        } catch (JSONException e) {
            // ignored
        }

        return firstItem != null && isDouble(firstItem);
    }

    public static boolean isBooleanArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return true;
        }

        String firstItem = null;
        try {
            firstItem = jsonArray.getString(0);
        } catch (JSONException e) {
            // ignored
        }

        return firstItem != null && isBoolean(firstItem);
    }

    public static boolean isStringArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return true;
        }

        String firstItem = null;
        try {
            firstItem = jsonArray.getString(0);
        } catch (JSONException e) {
            // ignored
        }

        return firstItem != null && isString(firstItem);
    }

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

        if (isJson(str)) {
            return false;
        }

        return true;
    }
}
