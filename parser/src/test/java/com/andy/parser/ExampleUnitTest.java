package com.andy.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        String input = "[{\"id\":1},{\"id\":2}]";
        try {
            JSONArray jsonArray = new JSONArray(input);
            String item = jsonArray.getString(0);
            assertTrue(!item.isEmpty());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}