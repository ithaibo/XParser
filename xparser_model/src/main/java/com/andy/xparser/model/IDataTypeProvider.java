package com.andy.xparser.model;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author wuhaibo
 * create date: 2018/6/1.
 */
public interface IDataTypeProvider {
    void loadInto(Map<String, Type> wareHouse);
}
