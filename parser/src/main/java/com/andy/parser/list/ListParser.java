package com.andy.parser.list;

import android.support.annotation.NonNull;

import com.andy.parser.base.BaseParser;
import com.andy.parser.type.JSONTypeMatcher;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * 将JSONArray解析为List
 *
 * Created by Andy on 2018/5/27.
 */

public class ListParser {

    public static <T> ArrayList<T> parse(Class<T> tClass, @NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return null;
        }

        if (JSONTypeMatcher.isArray(tClass, jsonArray)) {
            ArrayList<T> byteList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                String valStr;
                try {
                    valStr = jsonArray.getString(i);
                } catch (JSONException e) {
                    continue;
                }
                if (valStr != null) {
                    T item = BaseParser.parse(valStr, tClass);
                    if (item != null) {
                        byteList.add(item);
                    }
                }
            }

            return byteList;
        }

        return null;
    }
}
