package com.andy.parser;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gson.Gson;

import java.util.ArrayList;

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
        ArrayList<Integer> intList = new ArrayList<>();
        assertTrue(ArrayList.class.equals(intList.getClass()));
    }

    @Test
    public void testJsonParse() {
        String jsonArray = "[1,2,3,4,5]";
        int[] integerArrayList = new Gson().fromJson(jsonArray, int[].class);

        assertTrue(integerArrayList != null &&
                integerArrayList[2] == 3);
    }
}