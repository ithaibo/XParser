package com.andy.parser.type;

/**
 * @author wuhaibo
 * create date: 2018/6/2.
 */
public class BasicTypeUtil {

    public static boolean isBaiscType(Class clazz) {
        if (clazz.equals(int.class) ||
                clazz.equals(short.class) ||
                clazz.equals(byte.class) ||
                clazz.equals(long.class) ||
                clazz.equals(float.class) ||
                clazz.equals(double.class) ||
                clazz.equals(char.class) ||
                clazz.equals(boolean.class)) {
            return true;
        }

        return false;
    }

    /**
     * 将基本类型转换为包装类型
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Class<T> convertType(Class clazz) {
        if (clazz.equals(int.class))
            return (Class<T>) Integer.class;
        if (clazz.equals(short.class))
            return (Class<T>) Short.class;
        if (clazz.equals(byte.class))
            return (Class<T>) Byte.class;

        if (clazz.equals(long.class))
            return (Class<T>) Long.class;
        if (clazz.equals(float.class))
            return (Class<T>) Float.class;
        if (clazz.equals(double.class))
            return (Class<T>) Double.class;
        if (clazz.equals(char.class))
            return (Class<T>) Character.class;
        if (clazz.equals(boolean.class))
            return (Class<T>) Boolean.class;

        return null;
    }

}
