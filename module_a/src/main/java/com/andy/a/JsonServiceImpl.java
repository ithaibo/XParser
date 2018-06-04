package com.andy.a;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Used for json converter
 *
 * @author zhilong <a href="mailto:zhilong.lzl@alibaba-inc.com">Contact me.</a>
 * @version 1.0
 * @since 2017/4/10 下午2:10
 */
@Route(path = "/service/json")
public class JsonServiceImpl implements SerializationService {
    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
        Log.i("JsonServiceImpl", "json2Object invoked, input: " + text + ", type: " + clazz);
        return new Gson().fromJson(text, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        Log.i("JsonServiceImpl", "object2Json invoked");
        return new Gson().toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        Log.i("JsonServiceImpl", "parseObject invoked, input: " + input + ", type: " + clazz);
        return new Gson().fromJson(input, clazz);
    }
}
