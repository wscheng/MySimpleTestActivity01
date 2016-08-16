package com.asus.mysimpletestactivity01;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;


/**
 * Created by wscheng on 2016/7/11.
 */

@RunWith(AndroidJUnit4.class)
public class ListViewActivityTest {
    @Rule
    public ActivityTestRule<ListViewActivity> mainActivityActivityTestRule = new ActivityTestRule<>(ListViewActivity.class);

    private ArrayList<String> mNameList;
    private Context mTargetContext;
    // UI Automator
    private UiDevice mDevice;

    @Before
    public void init() {
        mNameList = Utilities.getNameList();
        mTargetContext = InstrumentationRegistry.getTargetContext();
        // UI Automator
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void checkDialog() {
        for (int i = 0; i < 5 && i < mNameList.size(); i++) {
            String personName = mNameList.get(i);
            onView(allOf(withId(R.id.my_name), withText(personName))).perform(click());
            checkPersonNameDialogAndClickOK(personName);
        }
    }

    @Test
    public void checkFinalNameIsNotDisplay() {
        onView(allOf(withId(R.id.my_name), withText(mNameList.get(mNameList.size()-1)))).check(doesNotExist());
    }

    @Test
    public void checkScrollToFinalAndThenFirst() throws InterruptedException {
        String tmpPersonName = mNameList.get(mNameList.size() - 1);
        onData(allOf(instanceOf(String.class), is(tmpPersonName))).perform(click());
        checkPersonNameDialogAndClickOK(tmpPersonName);

        tmpPersonName = mNameList.get(0);
        onData(anything()).inAdapterView(withId(R.id.my_list)).atPosition(0).perform(click());
        checkPersonNameDialogAndClickOK(tmpPersonName);
    }

    @Test
    public void checkScrollToFinalAndThenFirstByUIAutomator() throws InterruptedException, UiObjectNotFoundException {
        String tmpPersonName = mNameList.get(mNameList.size() - 1);
        UiScrollable listView = new UiScrollable(new UiSelector().className("android.widget.ListView"));
        UiObject uiObject = listView.getChildByText(new UiSelector().className("android.widget.LinearLayout"), tmpPersonName);
        uiObject.click();
        checkPersonNameDialogAndClickOKByUIA(tmpPersonName);

        tmpPersonName = mNameList.get(0);
        uiObject = listView.getChildByText(new UiSelector().className("android.widget.LinearLayout"), tmpPersonName);
        uiObject.click();
        checkPersonNameDialogAndClickOKByUIA(tmpPersonName);
    }

    @Test
    public void useOnDataToScrollTo() {
        // this will get failed! this is bad, espresso cannot support this
        onData(allOf(instanceOf(String.class), is(mNameList.size()-1))).perform(scrollTo());
    }

    @Test
    public void useOnDataToCheckFinal() {
        // this will get failed!
        onData(allOf(instanceOf(String.class), is(mNameList.get(mNameList.size() - 1)))).check(matches(not(isDisplayed())));
    }

    private void checkPersonNameDialogAndClickOK(String personName) {
        onView(withId(android.R.id.message)).inRoot(isDialog()).check(matches(withText(ListAdapter.getClickPersonMessage(mTargetContext, personName))));
        onView(withText(android.R.string.ok)).perform(click());
    }

    private void checkPersonNameDialogAndClickOKByUIA(String personName) {
        UiObject2 dialogMessage = mDevice.findObject(By.res("android:id/message"));
        assertEquals(ListAdapter.getClickPersonMessage(mTargetContext, personName), dialogMessage.getText());
        UiObject2 okBtn = mDevice.findObject(By.res("android:id/button1"));
        okBtn.click();
    }
}
