package com.andy.parser.to.list;

import android.support.annotation.NonNull;

import com.andy.parser.ValueWrapper;
import com.andy.parser.type.TypeMatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * 将JSONArray解析为List
 *
 * Created by Andy on 2018/5/27.
 */

public class ListParser {

    public static List<ValueWrapper> parse(@NonNull String json) {
        if (TypeMatcher.isJsonArray(json)) {
            JSONArray array;
            try {
                array = new JSONArray(json);
            } catch (JSONException e) {
                array = null;
            }

            if (array != null) {
                return parse(array);
            }
        }

        return null;
    }

    public static List<ValueWrapper> parse(@NonNull JSONArray jsonArray) {
        int size = jsonArray.length();
        if (size <= 0) {
            return null;
        }

        List<ValueWrapper> list = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            try {
                Boolean bV = jsonArray.getBoolean(i);
                if (bV != null) {
                    list.add(new ValueWrapper(bV, Boolean.class));
                    continue;
                }

                Integer iV = jsonArray.getInt(i);
                if (bV != null) {
                    list.add(new ValueWrapper(iV, Integer.class));
                    continue;
                }

                Long lv = jsonArray.getLong(i);
                if (bV != null) {
                    list.add(new ValueWrapper(lv, Long.class));
                    continue;
                }

                Double dv = jsonArray.getDouble(i);
                if (bV != null) {
                    list.add(new ValueWrapper(dv, Long.class));
                    continue;
                }

                String sv= jsonArray.getString(i);
                if (bV != null) {
                    list.add(new ValueWrapper(sv, String.class));
                    continue;
                }

                JSONObject jov = jsonArray.getJSONObject(i);
                if (bV != null) {
                    list.add(new ValueWrapper(jov, JSONObject.class));
                    continue;
                }

                JSONArray jav = jsonArray.getJSONArray(i);
                if (bV != null) {
                    list.add(new ValueWrapper(jav, JSONArray.class));
                    continue;
                }

                // unknown type
                Object ov = jsonArray.get(i);
                if (bV != null) {
                    list.add(new ValueWrapper(ov, Object.class));
                    continue;
                }

            } catch (JSONException e) {
                // ignores
            }
        }

        return list;
    }

    public static List<ValueWrapper> parse(@NonNull String key, @NonNull JSONObject jsonObject) {
        if (TypeMatcher.isJsonArray(key, jsonObject)) {
            JSONArray jsonArray;
            try {
                jsonArray = jsonObject.getJSONArray(key);
            } catch (JSONException e) {
                jsonArray = null;
            }

            if (jsonArray != null) {
                return parse(jsonArray);
            }
        }

        return null;
    }


}
