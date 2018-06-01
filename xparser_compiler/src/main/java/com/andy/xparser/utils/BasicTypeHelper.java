package com.andy.xparser.utils;

/**
 * @author wuhaibo
 * create date: 2018/6/1.
 */
public class BasicTypeHelper {
    public static boolean isBaiscType(String type) {
        if (type == null || type.isEmpty()) {
            return false;
        }

        if (type.equals("int") ||
                type.equals("byte") ||
                type.equals("short") ||
                type.equals("long") ||
                type.equals("float") ||
                type.equals("double") ||
                type.equals("char")) {
            return true;
        }

        return false;
    }

    public static String wraper(String basicType) {
        if (basicType == null || basicType.isEmpty()) {
            return null;
        }

        switch (basicType) {
            case "byte":
                return Byte.class.getCanonicalName();
            case "short":
                return Short.class.getCanonicalName();
            case "int":
                return Integer.class.getCanonicalName();
            case "long":
                return Long.class.getCanonicalName();
            case "float":
                return Float.class.getCanonicalName();
            case "double":
                return Double.class.getCanonicalName();
            case "char":
                return Character.class.getCanonicalName();
            default:
                return null;
        }
    }
}
