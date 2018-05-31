package com.andy.parser.type;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author wuhaibo
 * create date: 2018/5/31.
 */
public class JSONTypeMatcher {
    public static boolean isJsonObject(@NonNull String json) {
        if (json.isEmpty()) {
            return false;
        }

        try {
            new JSONObject(json);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static boolean isJsonArray(@NonNull String json) {
        if (json.isEmpty()) {
            return false;
        }

        try {
            new JSONArray(json);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static boolean isJson(@NonNull String input) {
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

        return value != null && StringTypeMatcher.isInt(value);
    }

    public static boolean isLong(@NonNull String key, @NonNull JSONObject jsonObject) {
        String value;

        try {
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            // ignore
            value = null;
        }

        return value != null && StringTypeMatcher.isLong(value);

    }

    public static boolean isDouble(@NonNull String key, @NonNull JSONObject jsonObject) {
        String value;

        try {
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            // ignore
            value = null;
        }

        return value != null && StringTypeMatcher.isDouble(value);
    }

    public static boolean isString(@NonNull String key, @NonNull JSONObject jsonObject) {
        String value;

        try {
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            // ignore
            value = null;
        }

        return value != null && StringTypeMatcher.isString(value);
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

        return firstItem != null && StringTypeMatcher.isInt(firstItem);
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

        return firstItem != null && StringTypeMatcher.isLong(firstItem);
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

        return firstItem != null && StringTypeMatcher.isDouble(firstItem);
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

        return firstItem != null && StringTypeMatcher.isBoolean(firstItem);
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

        return firstItem != null && StringTypeMatcher.isString(firstItem);
    }

    public static boolean isJsonObjectArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return true;
        }

        String firstItem;

        try {
            firstItem = jsonArray.getString(0);
        } catch (JSONException e) {
            firstItem = null;
        }

        if (firstItem == null) {
            return false;
        }

        return JSONTypeMatcher.isJsonObject(firstItem);
    }

    public static boolean arrayInArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return true;
        }

        String firstItem;

        try {
            firstItem = jsonArray.getString(0);
        } catch (JSONException e) {
            firstItem = null;
        }

        if (firstItem == null) {
            return false;
        }

        return JSONTypeMatcher.isJsonArray(firstItem);
    }
}
