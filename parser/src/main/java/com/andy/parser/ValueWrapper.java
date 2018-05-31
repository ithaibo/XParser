package com.andy.parser;

import java.io.Serializable;

/**
 * Created by Andy on 2018/5/27.
 */

public class ValueWrapper implements Serializable{
    private Object value;
    private Class<?> valueClass;
    private String key;

    public ValueWrapper(Object value, Class<?> valueClass) {
        this.value = value;
        this.valueClass = valueClass;
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getValueClass() {
        return valueClass;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "{value: " + value.toString() +
                ", type: " + valueClass.toString() +
                ", key: " + (key == null? "" : key)
                + "}";
    }
}
