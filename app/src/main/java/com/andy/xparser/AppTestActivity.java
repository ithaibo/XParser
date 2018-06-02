package com.andy.xparser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * @author wuhaibo
 * create date: 2018/6/2.
 */
@Route(path = "/app/apptest")
public class AppTestActivity extends AppCompatActivity {

    @Autowired(name = "idName")
    int id;

    @Autowired(name = "usr")
    User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
