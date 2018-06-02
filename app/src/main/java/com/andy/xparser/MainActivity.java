package com.andy.xparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.andy.parser.XParser;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = "{\"idList\":[1,43,45,23,11], \"id\":12}";
                String path = "/module_a/test";
                Bundle bundle = XParser.fromJson(path, json);
                if (bundle != null) {
                    ARouter.getInstance().build(path)
                            .with(bundle)
                            .navigation();
                }
            }
        });
    }

}
