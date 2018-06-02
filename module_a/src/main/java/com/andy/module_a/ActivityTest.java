package com.andy.module_a;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

/**
 * @author wuhaibo
 * create date: 2018/6/2.
 */

@Route(path = "/module_a/test")
public class ActivityTest extends AppCompatActivity {
    @Autowired
    List<Integer> idList;

    @Autowired
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);

        Log.i("Andy", "idList: " + idList);
        Log.i("Andy", "id: " + id);
    }
}
