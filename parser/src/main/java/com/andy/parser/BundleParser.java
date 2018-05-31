package com.andy.parser;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by Andy on 2018/5/27.
 */

public class BundleParser {
    /**
     * 将JOSN字符串解析为Bundle
     *
     * @param json
     * @return
     */
    public static Bundle parse(@NonNull String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        if (!TypeMatcher.isJsonObject(json)) {
            return null;
        }

        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            jsonObject = null;
        }

        return jsonObject == null ? null : parse(jsonObject);
    }

    /**
     * 将jsonObject中某项解析为Bundle
     *
     * @param key        需要解析的项的key
     * @param jsonObject 待解析
     * @return
     */
    public static Bundle parse(@NonNull String key, @NonNull JSONObject jsonObject) {
        try {
            JSONObject childObj = jsonObject.getJSONObject(key);
            return childObj == null ? null : parse(childObj);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将某个JSONObject解析为Bundle
     *
     * @param jsonObject
     * @return
     */
    public static Bundle parse(@NonNull JSONObject jsonObject) {
        // 获取所有的key
        Iterator<String> keys = KeysParser.parse(jsonObject);

        Bundle bundle = new Bundle();

        while (keys.hasNext()) {
            String key = keys.next();
            try {
                if (TypeMatcher.isBoolean(key, jsonObject)) {
                    bundle.putBoolean(key, jsonObject.getBoolean(key));
                } else if (TypeMatcher.isInt(key, jsonObject)) {
                    bundle.putInt(key, jsonObject.getInt(key));
                } else if (TypeMatcher.isLong(key, jsonObject)) {
                    bundle.putLong(key, jsonObject.getLong(key));
                } else if (TypeMatcher.isDouble(key, jsonObject)) {
                    bundle.putDouble(key, jsonObject.getDouble(key));
                } else if (TypeMatcher.isJsonObject(key, jsonObject)) {
                    // parse Bundle
                    Bundle bundleChild = parse(key, jsonObject);
                    if (bundleChild != null) {
                        bundle.putBundle(key, bundleChild);
                    }
                } else if (TypeMatcher.isJsonArray(key, jsonObject)) {
                    // parse array
                    JSONArray jsonArray = jsonObject.getJSONArray(key);
                    if (jsonArray == null) {
                        continue;
                    }
                    if (TypeMatcher.isIntArray(jsonArray)) {
                        bundle.putIntArray(key, ArrayParser.parseIntArray(jsonArray));
                    } else if (TypeMatcher.isLongArray(jsonArray)) {
                        bundle.putLongArray(key, ArrayParser.parseLongArray(jsonArray));
                    } else if (TypeMatcher.isDoubleArray(jsonArray)) {
                        bundle.putDoubleArray(key, ArrayParser.parseDoubleArray(jsonArray));
                    } else if (TypeMatcher.isBooleanArray(jsonArray)) {
                        bundle.putBooleanArray(key, ArrayParser.parseBooleanArray(jsonArray));
                    } else if (TypeMatcher.isStringArray(jsonArray)) {
                        bundle.putStringArray(key, ArrayParser.parseStringArray(jsonArray));
                    }
                } else if (TypeMatcher.isString(key, jsonObject)) {
                    bundle.putString(key, jsonObject.getString(key));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return bundle;
    }
}
