package com.asus.mysimpletestactivity01;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiCollection;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.allOf;

//import android.support.


/**
 * Created by wscheng on 2016/7/11.
 */

@RunWith(AndroidJUnit4.class)
public class RecyclerViewActivityTest {
    @Rule
    public ActivityTestRule<RecyclerViewActivity> mainActivityActivityTestRule = new ActivityTestRule<>(RecyclerViewActivity.class);

    private ArrayList<String> mNameList;
    UiDevice mDevice;

    @Before
    public void init() {
        mNameList = Utilities.getNameList();
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void checkInitViewResult() {
        onView(allOf(withId(R.id.my_name), withText(mNameList.get(0)))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.my_name), withText(mNameList.get(mNameList.size()-1)))).check(doesNotExist());
    }

    @Test
    public void checkScrollToFinalResult() throws InterruptedException {

        // 1 find the ImageView place(yahoo)
        onView(withId(R.id.my_rec)).perform(RecyclerViewActions.actionOnItem(hasDescendant(allOf(withId(R.id.my_img), hasSibling(withText(mNameList.get(mNameList.size()-1))))), scrollTo()));
        onView(allOf(withId(R.id.my_img), hasSibling(withText(mNameList.get(mNameList.size()-1))))).perform(click());
        mDevice.pressBack();
        sleep(3000);

        // 2 find the TextView place(apple)
        onView(withId(R.id.my_rec)).perform(RecyclerViewActions.actionOnItem(hasDescendant(allOf(withId(R.id.my_name), withText(mNameList.get(mNameList.size()-1)))),scrollTo()));
        onView(allOf(withId(R.id.my_name), withText(mNameList.get(mNameList.size()-1)))).perform(click());
        mDevice.pressBack();
        sleep(3000);


        // 3 find the TextView place(apple)
        onView(withId(R.id.my_rec)).perform(RecyclerViewActions.actionOnItemAtPosition(mNameList.size()-1, scrollTo()));
        onView(allOf(withId(R.id.my_name), withText(mNameList.get(mNameList.size()-1)))).perform(click());
        mDevice.pressBack();
        sleep(3000);

        // 4 click position View in rec(google)
        onView(withId(R.id.my_rec)).perform(RecyclerViewActions.actionOnItemAtPosition(mNameList.size() - 1, click()));
        mDevice.pressBack();
        sleep(3000);

    }

    @Test
    public void checkScrollToFinalAndFirstByUIA() throws InterruptedException, UiObjectNotFoundException {

        UiScrollable recView = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        // 1 find the last view (google)
        String tmpPersonName = mNameList.get(mNameList.size() - 1);
        UiObject uiObject = recView.getChildByText(new UiSelector().className("android.widget.LinearLayout"), tmpPersonName);
        uiObject.click();
        mDevice.pressBack();
        sleep(3000);

        // 2 find the last view (apple)
        uiObject = recView.getChildByText(new UiSelector().className("android.widget.TextView"), tmpPersonName);
        uiObject.click();
        mDevice.pressBack();
        sleep(3000);

        // 3 find the 1st view
        tmpPersonName = mNameList.get(0);
        uiObject = recView.getChildByText(new UiSelector().className("android.widget.LinearLayout"), tmpPersonName);
        uiObject.click();
        mDevice.pressBack();
        sleep(3000);
    }
}
