package com.andy.xparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.andy.parser.WareHouse;
import com.andy.parser.to.list.ListParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import annotation.Autowired;
import annotation.Route;

@Route(path = "/app/main")
public class MainActivity extends AppCompatActivity {

    @Autowired(name = "idName")
    private int id;

    @Autowired(name = "usr")
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        String input = "[1, 3, 32, 43]";
//
//        JSONArray  jsonArray;
//        try {
//            jsonArray = new JSONArray(input);
//        } catch (JSONException e) {
//            jsonArray = null;
//        }
//        if (jsonArray != null) {
//            List<Integer> list = ListParser.parse(Integer.class, jsonArray);
//            Integer[] array = new Integer[list.size()];
//
//            Log.i("Andy", "int array: " +list);
//        }

//        String complex = "{\"int\":312,\"double\":2.3,\"boolean\":true,\"string\":\"gooogle\","
//                + "\"intArray\":[1,2,4,5],\"objArray\":[{\"id\":3424},{\"id\":453}],"
//                + "\"arrayArray\":[[12,4,5],[23,4,5,1]]}";

        findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                Class clazz = WareHouse.getType("/app/main$$usr");
                Log.i("Andy", String.format("name = %s, & class: %s", "/app/main$$usr", clazz.getCanonicalName().toString()));

                Class clazz1 = WareHouse.getType("/app/main$$idName");
                Log.i("Andy", String.format("name = %s, & class: %s", "/app/main$$idName", clazz1.getCanonicalName().toString()));
            }
        });

    }

}
