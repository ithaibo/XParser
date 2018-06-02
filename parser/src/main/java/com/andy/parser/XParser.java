package com.andy.parser;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.andy.parser.base.BaseParser;
import com.andy.parser.type.JSONTypeMatcher;
import com.andy.parser.type.StringTypeMatcher;
import com.andy.parser.utils.ClassUtils;
import com.andy.xparser.model.Constants;
import com.andy.xparser.model.IDataTypeProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;

/**
 * @author wuhaibo
 * create date: 2018/6/1.
 */
public class XParser {
    public synchronized static void init(Context context) {
        // collect all data annotations
        try {
            Set<String> dataTypeClassSet = ClassUtils.getFileNameByPackageName(context,
                    Constants.NAME_PACKAGE_AUTOWIRED_ROUTE);

            if (dataTypeClassSet == null || dataTypeClassSet.isEmpty()) {
                Log.i("XParser", "dataTypeClassSet is isEmpty");
                return;
            }

            for (String className : dataTypeClassSet) {
                Log.i("XParser", "find a dataTypeClass: " + className);
                Class clazz = Class.forName(className);
                if (clazz == null) {
                    continue;
                }
                Constructor constructor = clazz.getConstructor();
                if (constructor != null) {
                    ((IDataTypeProvider) constructor.newInstance()).loadInto(WareHouse.dataTypeMap);
                    Log.i("XParser", "add a record into warehouse, all data: " + WareHouse.dataTypeMap);
                } else {
                    Log.e("XParser", "Constructor for " + className + " is null");
                }

            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String FORMAT_PATH_DATA = "%s$$%s";

    /**
     * 将uri解析为Bundle
     *
     * @param uri 经过decode后的url
     */
    public static Bundle fromUri(@NonNull String path, @NonNull Uri uri) {
        Bundle bundle = new Bundle();
        Set<String> paramKeySet = uri.getQueryParameterNames();
        if (paramKeySet != null && !paramKeySet.isEmpty()) {
            for (String key : paramKeySet) {
                String keyConvert = String.format(FORMAT_PATH_DATA, path, key);
                Type valueType = WareHouse.getType(keyConvert);
                if (valueType == null) {
                    continue;
                }

                String valueRaw = uri.getQueryParameter(key);
                // 解析普通字符串
                if (StringTypeMatcher.isString(valueRaw) && valueType.equals(String.class)) {
                    bundle.putString(key, valueRaw);
                    continue;
                }

                // 解析基本类型
                if (parseBasicType(bundle, key, valueType, valueRaw)) {
                    continue;
                }

                // 其他的全部以JSON形式进行返回
                if (JSONTypeMatcher.isJson(valueRaw)) {
                    bundle.putString(key, valueRaw);
                }
            }
        }

        return bundle;
    }


    public static Bundle fromJson(@NonNull String path, @NonNull String json) {
        if (JSONTypeMatcher.isJsonArray(json)) {
            // 不支持仅为JSON数组的数据
            return null;
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            // ignored
        }
        if (jsonObject == null) {
            return null;
        }

        Bundle bundle = new Bundle();

        Iterator<String> keyIterator = KeysParser.parse(json);
        if (keyIterator != null) {
            try {
                while (keyIterator.hasNext()) {
                    String key = keyIterator.next();
                    String valueRaw = jsonObject.getString(key);
                    String keyConvert = String.format(FORMAT_PATH_DATA, path, key);
                    Type valueType = WareHouse.getType(keyConvert);

                    if (valueType == null) {
                        continue;
                    }

                    if (StringTypeMatcher.isString(valueRaw) && valueType.equals(String.class)) {
                        bundle.putString(key, valueRaw);
                        continue;
                    }

                    if (parseBasicType(bundle, key, valueType, valueRaw)) {
                        continue;
                    }

                    if (JSONTypeMatcher.isJson(valueRaw)) {
                        bundle.putString(key, valueRaw);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return bundle;
    }

    private static boolean parseBasicType(Bundle bundle, String key, Type valueType,
            String valueRaw) {
        if (StringTypeMatcher.isByte(valueRaw) &&
                (byte.class.equals(valueType) ||
                        valueType.equals(Byte.class))) {
            Byte parsed = BaseParser.parseByte(valueRaw);
            if (parsed == null) {
                parsed = 0;
            }
            bundle.putByte(key, parsed);
            return true;
        }

        if (StringTypeMatcher.isShort(valueRaw) &&
                (short.class.equals(valueType) ||
                        Short.class.equals(valueType))) {
            Short parsed = BaseParser.parseShort(valueRaw);
            bundle.putShort(key, parsed == null ? 0 : parsed);
            return true;
        }

        if (StringTypeMatcher.isInt(valueRaw) &&
                (int.class.equals(valueType) ||
                        Integer.class.equals(valueType))) {
            Integer parsed = BaseParser.parseInt(valueRaw);
            bundle.putInt(key, parsed == null ? 0 : parsed);
            return true;
        }

        if (StringTypeMatcher.isLong(valueRaw) &&
                (long.class.equals(valueType) ||
                        Long.class.equals(valueType))) {
            Long parsed = BaseParser.parseLong(valueRaw);
            bundle.putLong(key, parsed == null ? 0 : parsed);
            return true;
        }

        if (StringTypeMatcher.isFloat(valueRaw) &&
                (float.class.equals(valueType) ||
                        Float.class.equals(valueType))) {
            Float parsed = BaseParser.parseFloat(valueRaw);
            bundle.putFloat(key, parsed == null ? 0 : parsed);
            return true;
        }

        if (StringTypeMatcher.isDouble(valueRaw) &&
                (double.class.equals(valueType) ||
                        Double.class.equals(valueType))) {
            Double parsed = BaseParser.parseDouble(valueRaw);
            bundle.putDouble(key, parsed == null ? 0 : parsed);
            return true;
        }

        if (StringTypeMatcher.isBoolean(valueRaw) &&
                (boolean.class.equals(valueType) ||
                        Boolean.class.equals(valueType))) {
            Boolean parsed = BaseParser.parseBoolean(valueRaw);
            bundle.putBoolean(key, parsed == null ? false : parsed);
            return true;
        }

        return false;
    }
}