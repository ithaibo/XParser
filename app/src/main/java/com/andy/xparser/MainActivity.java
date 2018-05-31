package com.andy.xparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String input = "[{\"id\":1},{\"id\":2}]";
        try {
            JSONArray jsonArray = new JSONArray(input);
            for (int i = 0; i < jsonArray.length(); i++) {
                String item = jsonArray.getString(0);
                Log.i("Andy", "item: " + item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
