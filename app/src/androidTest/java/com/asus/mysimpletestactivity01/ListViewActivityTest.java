package com.asus.mysimpletestactivity01;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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

    @Before
    public void init() {
        mNameList = Utilities.getNameList();
        mTargetContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void onCheckDialog() {
        for (int i = 0; i < 5 && i < mNameList.size(); i++) {
            String personName = mNameList.get(i);
            onView(allOf(withId(R.id.my_name), withText(personName))).check(matches(isDisplayed()));
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

        //onData(allOf(instanceOf(String.class), is("Sam"))).check(matches(isDisplayed()));
        // this is bad, espresso cannot support this
        // onData(allOf(instanceOf(String.class), is("Sam"))).perform(scrollTo());
        String tmpPersonName = mNameList.get(mNameList.size() - 1);
        onData(allOf(instanceOf(String.class), is(tmpPersonName))).perform(click());
        checkPersonNameDialogAndClickOK(tmpPersonName);

        tmpPersonName = mNameList.get(0);
        onData(anything()).inAdapterView(withId(R.id.my_list)).atPosition(0).perform(click());
        checkPersonNameDialogAndClickOK(tmpPersonName);
    }

    @Test
    public void useOnDataToCheckFinal() {
        // this will get failed!
        onData(allOf(instanceOf(String.class), is(mNameList.get(mNameList.size() - 1)))).check(matches(not(isDisplayed())));
    }

    @Test
    public void useOnDataToCheckFinal2() {
        // this will get failed!
        onData(allOf(instanceOf(String.class), is(mNameList.get(mNameList.size() - 1)))).check(matches(not(isDisplayed())));
    }

    private void checkPersonNameDialogAndClickOK(String personName) {
        onView(withText(ListAdapter.getClickPersonMessage(mTargetContext, personName))).check(matches(isDisplayed()));
        onView(withText(android.R.string.ok)).perform(click());
    }
}
