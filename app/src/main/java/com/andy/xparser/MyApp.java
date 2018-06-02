package com.andy.xparser;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.andy.parser.XParser;

/**
 * @author wuhaibo
 * create date: 2018/6/1.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        XParser.init(this);
        ARouter.init(this);
    }
}
