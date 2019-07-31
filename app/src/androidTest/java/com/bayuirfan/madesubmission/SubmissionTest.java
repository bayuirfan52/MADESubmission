package com.bayuirfan.madesubmission;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.bayuirfan.madesubmission.features.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SubmissionTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
            .perform(RecyclerViewActions.scrollToPosition(10))
            .perform(RecyclerViewActions.actionOnItemAtPosition(10, click()));

        onView(withId(R.id.tv_title_detail)).check(matches(isDisplayed()));

        pressBack();

        onView(withId(R.id.vp_main)).perform(swipeLeft());

        onView(withId(R.id.rv_tv_show)).check(matches(isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(5))
                .perform(RecyclerViewActions.actionOnItemAtPosition(5, click()));

        try {
            Thread.sleep(2000);
            onView(withId(R.id.tv_genre_detail)).check(matches(isDisplayed()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();
    }
}