package com.andy.parser;

/**
 * @author wuhaibo
 * create date: 2018/5/31.
 */
public class UnWrapUtil {
    public static byte[] unWrape(Byte[] input) {
        if (input == null) {
            return null;
        }

        byte[] output = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            output[i] = (input[i] == null)? 0 : input[i];
        }

        return output;
    }

    public static short[] unWrape(Short[] input) {
        if (input == null) {
            return null;
        }

        short[] result = new short[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = (input[i] == null)? 0 : input[i];
        }

        return result;
    }

    public static int[] unWrape(Integer[] input) {
        if (input == null) {
            return null;
        }

        int[] result = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = (input[i] == null)? 0 : input[i];
        }

        return result;
    }

    public static long[] unWrape(Long[] input) {
        if (input == null) {
            return null;
        }

        long[] result = new long[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = (input[i] == null)? 0 : input[i];
        }

        return result;
    }

    public static float[] unWrape(Float[] input) {
        if (input == null) {
            return null;
        }

        float[] result = new float[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = (input[i] == null)? 0 : input[i];
        }

        return result;
    }

    public static double[] unWrape(Double[] input) {
        if (input == null) {
            return null;
        }

        double[] result = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = (input[i] == null)? 0 : input[i];
        }

        return result;
    }

    public static boolean[] unWrape(Boolean[] input) {
        if (input == null) {
            return null;
        }

        boolean[] result = new boolean[input.length];
        for (int i=0; i<input.length; i++) {
            result[i] = (input[i] == null)? false : input[i];
        }

        return result;
    }
}
