package com.andy.parser;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 2018/5/27.
 */

public class ArrayParser {
    public static int[] parseIntArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return null;
        }

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add(jsonArray.getInt(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        int[] array = new int[list.size()];
        for (int i= 0 ; i< list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static long[] parseLongArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return null;
        }

        List<Long> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add(jsonArray.getLong(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        long[] array = new long[list.size()];
        for (int i= 0 ; i< list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static double[] parseDoubleArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return null;
        }

        List<Double> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add(jsonArray.getDouble(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        double[] array = new double[list.size()];
        for (int i= 0 ; i< list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    public static boolean[] parseBooleanArray(@NonNull JSONArray jsonArray) {
        if (jsonArray.length() <= 0) {
            return null;
        }

        List<Boolean> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                list.add(jsonArray.getBoolean(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        boolean[] array = new boolean[list.size()];
        for (int i= 0 ; i< list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

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
        for (int i= 0 ; i< list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }
}
