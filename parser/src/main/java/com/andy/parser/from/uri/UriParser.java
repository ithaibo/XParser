package com.andy.parser.from.uri;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andy.parser.UnWrapUtil;
import com.andy.parser.from.json.JSONParser;
import com.andy.parser.to.base.BaseParser;
import com.andy.parser.to.list.ListParser;
import com.andy.parser.type.JSONTypeMatcher;
import com.andy.parser.type.StringTypeMatcher;
import com.andy.parser.type.TypeMatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author wuhaibo
 * create date: 2018/5/30.
 */
public class UriParser {

    public static Bundle parse(@NonNull Uri uri) {
        String url = uri.toString();
        if (url.isEmpty()) {
            return null;
        }

        return parse(url);
    }

    @Nullable
    public static Bundle parse(@NonNull String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.isEmpty()) {
            return null;
        }


        Uri uri = null;
        try {
            uri = Uri.parse(url);
        } catch (Exception e) {
            // ignored
        }

        if (uri == null) {
            return null;
        }

        Set<String> keySet = uri.getQueryParameterNames();
        if (keySet == null || keySet.isEmpty()) {
            return null;
        }

        Bundle bundle = new Bundle();
        for (String key : keySet) {
            String valStr = uri.getQueryParameter(key);
            if (valStr == null || valStr.isEmpty()) {
                continue;
            }
            if (StringTypeMatcher.isByte(valStr)) {
                Byte valByte = BaseParser.parseByte(valStr);
                if (valByte != null) {
                    bundle.putByte(key, valByte);
                }
            } else if (StringTypeMatcher.isShort(valStr)) {
                Short valShort = BaseParser.parseShort(valStr);
                if (valShort != null) {
                    bundle.putShort(key, valShort);
                }
            } else if (StringTypeMatcher.isInt(valStr)) {
                Integer valInteger = BaseParser.parseInt(valStr);
                if (valInteger != null) {
                    bundle.putInt(key, valInteger);
                }
            } else if (StringTypeMatcher.isLong(valStr)) {
                Long valLong = BaseParser.parseLong(valStr);
                if (valLong != null) {
                    bundle.putLong(key, valLong);
                }
            } else if (StringTypeMatcher.isFloat(valStr)) {
                Float valFloat = BaseParser.parseFloat(valStr);
                if (valFloat != null) {
                    bundle.putFloat(key, valFloat);
                }
            } else if (StringTypeMatcher.isDouble(valStr)) {
                Double valDouble = BaseParser.parseDouble(valStr);
                if (valDouble != null) {
                    bundle.putDouble(key, valDouble);
                }
            } else if (StringTypeMatcher.isBoolean(valStr)) {
                Boolean valBoolean = BaseParser.parseBoolean(valStr);
                if (valBoolean != null) {
                    bundle.putBoolean(key, valBoolean);
                }
            } else if (JSONTypeMatcher.isJsonObject(valStr)) {
                Bundle valBundle = JSONParser.parse(valStr);
                if (valBundle != null) {
                    bundle.putBundle(key, bundle);
                }
            } else if (JSONTypeMatcher.isJsonArray(valStr)) {
                JSONArray jsonArray;
                try {
                    jsonArray = new JSONArray(valStr);
                } catch (JSONException e) {
                    jsonArray = null;
                }
                if (jsonArray != null) {
                    // 首先判断数组的类型

                    // 根据类型进行解析

                    if (JSONTypeMatcher.isArray(Byte.class, jsonArray)) {
                        List<Byte> list = ListParser.parse(Byte.class, jsonArray);
                        if (list != null) {
                            Byte[] bytes = new Byte[list.size()];
                            bytes = list.toArray(bytes);
                            bundle.putByteArray(key, UnWrapUtil.unWrape(bytes));
                        }
                    } else if (JSONTypeMatcher.isArray(Short.class, jsonArray)) {
                        List<Short> list = ListParser.parse(Short.class, jsonArray);
                        if (list != null) {
                            Short[] array = new Short[list.size()];
                            array = list.toArray(array);
                            bundle.putShortArray(key, UnWrapUtil.unWrape(array));
                        }
                    } else if (JSONTypeMatcher.isArray(Integer.class, jsonArray)) {
                        List<Integer> list = ListParser.parse(Integer.class, jsonArray);
                        if (list != null) {
                            Integer[] array = new Integer[list.size()];
                            array = list.toArray(array);
                            bundle.putIntArray(key, UnWrapUtil.unWrape(array));
                        }
                    } else if (JSONTypeMatcher.isArray(Long.class, jsonArray)) {
                        List<Long> list = ListParser.parse(Long.class, jsonArray);
                        if (list != null) {
                            Long[] array = new Long[list.size()];
                            array = list.toArray(array);
                            bundle.putLongArray(key, UnWrapUtil.unWrape(array));
                        }
                    } else if (JSONTypeMatcher.isArray(Float.class, jsonArray)) {
                        List<Float> list = ListParser.parse(Float.class, jsonArray);
                        if (list != null) {
                            Float[] array = new Float[list.size()];
                            array = list.toArray(array);
                            bundle.putFloatArray(key, UnWrapUtil.unWrape(array));
                        }
                    } else if (JSONTypeMatcher.isArray(Double.class, jsonArray)) {
                        List<Double> list = ListParser.parse(Double.class, jsonArray);
                        if (list != null) {
                            Double[] array = new Double[list.size()];
                            array = list.toArray(array);
                            bundle.putDoubleArray(key, UnWrapUtil.unWrape(array));
                        }
                    } else if (JSONTypeMatcher.isArray(Boolean.class, jsonArray)) {
                        List<Boolean> list = ListParser.parse(Boolean.class, jsonArray);
                        if (list != null) {
                            Boolean[] array = new Boolean[list.size()];
                            array = list.toArray(array);
                            bundle.putBooleanArray(key, UnWrapUtil.unWrape(array));
                        }
                    } else if (JSONTypeMatcher.isArray(String.class, jsonArray)) {
                        List<String> list = ListParser.parse(String.class, jsonArray);
                        if (list != null) {
                            String[] array = new String[list.size()];
                            array = list.toArray(array);
                            bundle.putStringArray(key, array);
                        }
                    } else if (JSONTypeMatcher.isArray(JSONObject.class, jsonArray)) {
                        // 如果array中类型不是基本类型，解析为字符串
                        ArrayList<String> list = ListParser.parse(String.class, jsonArray);
                        if (list != null) {
                            bundle.putSerializable(key, list);
                        }
                    } else if (JSONTypeMatcher.isArray(JSONArray.class, jsonArray)) {
                        // 如果array中类型不是基本类型，解析为字符串
                        ArrayList<String> list = ListParser.parse(String.class, jsonArray);
                        if (list != null) {
                            bundle.putSerializable(key, list);
                        }
                    }
                }
            }
        }

        return bundle;
    }

}
