package com.example.pranit.espressoguide.recyclerview;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.example.pranit.espressoguide.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by pranit on 27/12/17.
 */
public class RecyclerSampleActivityTest {
    private static final int ITEM_BELOW_THE_FOLD = 40;

    @Rule
    public ActivityTestRule<RecyclerSampleActivity> mActivityRule = new ActivityTestRule<>(
            RecyclerSampleActivity.class);

    @Test
    public void scrollToItemBelowFold_checkItsText() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEM_BELOW_THE_FOLD, click()));

        // Match the text in an item below the fold and check that it's displayed.
        String itemElementText = mActivityRule.getActivity().getResources().getString(
                R.string.item_element_text) + String.valueOf(ITEM_BELOW_THE_FOLD);
        onView(withText(itemElementText)).check(matches(isDisplayed()));
    }

    @Test
    public void itemInMiddleOfList_hasSpecialText() {
        // First, scroll to the view holder using the isInTheMiddle matcher.
        onView(ViewMatchers.withId(R.id.recyclerView))
                .perform(RecyclerViewActions.scrollToHolder(isInTheMiddle()));

        // Check that the item has the special text.
        String middleElementText =
                mActivityRule.getActivity().getResources().getString(R.string.middle);
        onView(withText(middleElementText)).check(matches(isDisplayed()));
    }

    /**
     * Matches the {@link CustomAdapter.ViewHolder}s in the middle of the list.
     */
    private static Matcher<CustomAdapter.ViewHolder> isInTheMiddle() {
        return new TypeSafeMatcher<CustomAdapter.ViewHolder>() {
            @Override
            protected boolean matchesSafely(CustomAdapter.ViewHolder customHolder) {
                return customHolder.getIsInTheMiddle();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item in the middle");
            }
        };
    }
}