package com.andy.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuhaibo
 * create date: 2018/6/1.
 */
public class WareHouse {
    static Map<String, Class> dataTypeMap = new HashMap<>();

    public static void init() {
        dataTypeMap = new HashMap<>();
    }

    public static void clear() {
        if (null != dataTypeMap) {
            dataTypeMap.clear();
            dataTypeMap = null;
        }
    }

    public static Class<?> getType (String dataName) {
        return dataTypeMap.isEmpty()? null : dataTypeMap.get(dataName);
    }

}
