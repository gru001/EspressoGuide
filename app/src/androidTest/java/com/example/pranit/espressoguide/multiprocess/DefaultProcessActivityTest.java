package com.example.pranit.espressoguide.multiprocess;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.pranit.espressoguide.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by pranit on 12/25/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DefaultProcessActivityTest {
    private static final String TAG = "ExampleInstrumentedTest";

    private static final String DEFAULT_PROC_NAME =
            "com.example.android.testing.espresso.multiprocesssample";

    @Rule
    public ActivityTestRule<DefaultProcessActivity> rule =
            new ActivityTestRule<>(DefaultProcessActivity.class);

    @Test
    public void verifyAssertingOnViewInRemoteProcessIsSuccessful() {
        Log.d(TAG, "Checking main process name...");
        onView(withId(R.id.textNamedProcess)).check(matches(withText(is(DEFAULT_PROC_NAME))));

        Log.d(TAG, "Starting activity in a secondary process...");
        onView(withId(R.id.startActivityBtn)).perform(click());

        Log.d(TAG, "Checking private process name...");
        onView(withId(R.id.textPrivateProcessName))
                .check(matches(withText(is(DEFAULT_PROC_NAME + ":PID2"))));

        Log.d(TAG, "Clicking list item in private process activity...");
        onData(allOf(instanceOf(String.class), is("Doppio"))).perform(click());

        Log.d(TAG, "Check selected text appears...");
        onView(withId(R.id.selectedListItemText)).check(matches(withText("Selected: Doppio")));
    }
}