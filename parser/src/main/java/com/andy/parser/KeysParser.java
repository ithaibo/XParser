package com.andy.parser;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 *
 * 解析JSON中的key
 *
 * Created by Andy on 2018/5/27.
 */

public class KeysParser {

    public static Iterator<String> parse(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        // 如果不是jsonObject，没有keys
        if (!TypeMatcher.isJsonObject(json)) {
            return null;
        }

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            jsonObject = null;
        }

        if (jsonObject == null) {
            return null;
        }

        return parse(jsonObject);
    }

    public static Iterator<String> parse(@NonNull JSONObject jsonObject) {
        return jsonObject.keys();
    }
}
