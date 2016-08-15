package com.asus.mysimpletestactivity01;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by wscheng on 2016/8/15.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest0 {
    public static String TAG = "MainActivityTest";

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void beforeClass1() {
        Log.d(TAG, "beforeClass1()");
    }

    @BeforeClass
    public static void beforeClass2() {
        Log.d(TAG, "beforeClass2()");
    }

    @Before
    public void before() {
        Log.d(TAG, "before()");
    }

    @Test
    public void test1() {
        Log.d(TAG, "test1()");
    }

    @Test
    public void test2() {
        Log.d(TAG, "test2()");
    }

    @After
    public void after() {
        Log.d(TAG, "after()");
    }

    @AfterClass
    public static void afterClass() {
        Log.d(TAG, "afterClass()");
    }
}
