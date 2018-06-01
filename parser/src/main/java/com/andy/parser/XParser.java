package com.andy.parser;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.andy.parser.utils.ClassUtils;
import com.andy.xparser.model.Constants;
import com.andy.xparser.model.IDataTypeProvider;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * @author wuhaibo
 * create date: 2018/6/1.
 */
public class XParser {
    //    ParseResult fromUri(String path, Uri uri);
//    ParseResult fromJson(String jsonStr);
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
}