package com.andy.xparser;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.andy.parser.XParser;
import com.andy.xparser.data.XParser__app__Index;
import com.andy.xparser.data.XParser__module_a__Index;

/**
 * @author wuhaibo
 * create date: 2018/6/1.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
            XParser.Debugable = true;
        }

        XParser.addIndex(new XParser__app__Index());
        XParser.addIndex(new XParser__module_a__Index());

        ARouter.init(this);
    }
}
