package com.andy.parser.to.array;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.andy.parser.from.json.JSONParser;
import com.andy.parser.to.base.BaseParser;
import com.andy.parser.to.list.ListParser;
import com.andy.parser.type.JSONTypeMatcher;
import com.andy.parser.type.StringTypeMatcher;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andy on 2018/5/27.
 */

public class ArrayParser {

    public static byte[] parseByteArray(@NonNull JSONArray jsonArray) {
        List<Byte> list = ListParser.parse(Byte.class, jsonArray);
        if (list == null) {
            return null;
        }

        byte[] result = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    public static short[] parseShortArray(@NonNull JSONArray jsonArray) {
        List<Short> list = ListParser.parse(Short.class, jsonArray);
        if (list == null) {
            return null;
        }

        short[] result = new short[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * 将JSONArray解析为int[]
     */
    public static int[] parseIntArray(@NonNull JSONArray jsonArray) {
        List<Integer> list = ListParser.parse(Integer.class, jsonArray);
        if (list == null) {
            return null;
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * 将JSONArray解析为long[]
     */
    public static long[] parseLongArray(@NonNull JSONArray jsonArray) {
        List<Long> list = ListParser.parse(Long.class, jsonArray);
        if (list == null) {
            return null;
        }

        long[] result = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    public static float[] parseFloatArray(@NonNull JSONArray jsonArray) {
        List<Float> list = ListParser.parse(Float.class, jsonArray);
        if (list == null) {
            return null;
        }

        float[] result = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * 将JSONArray解析为double[]
     */
    public static double[] parseDoubleArray(@NonNull JSONArray jsonArray) {
        List<Double> list = ListParser.parse(Double.class, jsonArray);
        if (list == null) {
            return null;
        }

        double[] result = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * 将JSONArray解析为boolean[]
     */
    public static boolean[] parseBooleanArray(@NonNull JSONArray jsonArray) {
        List<Boolean> list = ListParser.parse(Boolean.class, jsonArray);
        if (list == null) {
            return null;
        }

        boolean[] result = new boolean[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * 将JSONArray解析为String[]
     */
    public static String[] parseStringArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return null;
        }

        List<String> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add(jsonArray.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    /**
     * 将JSONArray解析为Bundle[]
     */
    public static Bundle[] pareBundleArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return null;
        }

        List<Bundle> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                String item = jsonArray.getString(i);
                if (item == null || item.isEmpty()) {
                    continue;
                }
                if (JSONTypeMatcher.isJsonObject(item)) {
                    Bundle bundle = JSONParser.parse(item);
                    if (bundle != null) {
                        list.add(bundle);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Bundle[] array = new Bundle[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }
}
