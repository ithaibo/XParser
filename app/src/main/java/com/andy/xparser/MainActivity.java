package com.andy.xparser;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.andy.parser.XParser;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView etSourceJson = findViewById(R.id.et_source_json);
        etSourceJson.setText("{\"id\":12,\"idList\":[1,43,45,23,11],\"user\":{\"name\":\"Andy\","
                + "\"id\":123}}");

        final TextView etSourceUri = findViewById(R.id.et_source_uri);
        etSourceUri.setText("quanmin://mobile.app/module_a/test?id=12&idList=[1,43,45,23,11]&user={\"name\":\"Andy\",\"id\":123}");

        final TextView etPath = findViewById(R.id.etPath);
        etPath.setText("/module_a/test");

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String json = etSourceJson.getText().toString();
                String path = etPath.getText().toString();
                Bundle bundle = XParser.fromJson(path, json);
                if (bundle != null) {
                    Log.i("MainActivity", "bundle: " + bundle);

                    ARouter.getInstance().build("/module_a/test")
                            .with(bundle)
                            .navigation();
                }
            }
        });

        findViewById(R.id.btnUri).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etSourceUri.getText().toString();
                Uri uri = Uri.parse(url);
                Bundle bundle = XParser.fromUri("/module_a/test", uri);
                if (bundle != null) {
                    Log.i("MainActivity", "uri, bundle: " + bundle);
//                    ARouter.getInstance().build(etPath.getText().toString())
//                            .with(bundle)
//                            .navigation();

                    ARouter.getInstance().build(uri)
                            .navigation();
                }
            }
        });
    }

}
