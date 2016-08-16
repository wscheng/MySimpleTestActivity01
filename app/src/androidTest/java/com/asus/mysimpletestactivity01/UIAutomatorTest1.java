package com.asus.mysimpletestactivity01;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import static java.lang.Thread.sleep;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UIAutomatorTest1 {

    private static final String TAG = "UIAutomatorTest1";
    private static final String BASIC_SAMPLE_PACKAGE
            = "com.asus.mysimpletestactivity01";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;
    private Context mContext;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        mContext = InstrumentationRegistry.getContext();
        final Intent intent = mContext.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mContext.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT);
    }


    @Test
    public void testClick() {
        UiObject2 btn = mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "btn"));
        btn.click();
        UiObject2 resultText2 = mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "result"));
        assertEquals(MainActivity.RESULT, resultText2.getText());
    }

    @Test
    public void testClickWithUiObjectItemChanged() throws UiObjectNotFoundException {
        String initialString = "Hello world!";
        Log.d(TAG, "this context is test package context, don't have target context=>" +
                mContext.getPackageName() + " || Should use @Rule so that u can get the target context");
        UiObject resultText = mDevice.findObject(new UiSelector().resourceId("com.asus.mysimpletestactivity01:id/result").text(initialString));
        UiObject2 resultText2 = mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "result").text(initialString));
        UiObject2 btn = mDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "btn"));
        btn.click();
        try {
            assertEquals(MainActivity.RESULT, resultText.getText());
        } catch (UiObjectNotFoundException e) {
            Log.d(TAG, "UiObject cannot found the old item");
        }
        assertEquals(MainActivity.RESULT, resultText2.getText());
    }
}