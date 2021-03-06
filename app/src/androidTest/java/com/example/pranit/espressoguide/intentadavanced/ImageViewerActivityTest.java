package com.example.pranit.espressoguide.intentadavanced;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.pranit.espressoguide.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Instrumentation.ActivityResult;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.pranit.espressoguide.intentadavanced.ImageViewHasDrawableMatcher.hasDrawable;
import static org.hamcrest.Matchers.not;
/**
 * Espresso tests for {@link ImageViewerActivity}.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ImageViewerActivityTest {

    /**
     * A JUnit {@link Rule @Rule} to init and release Espresso Intents before and after each
     * test run.
     * <p>
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link Before @Before} method.
     * <p>
     * This rule is based on {@link ActivityTestRule} and will create and launch of the activity
     * for you and also expose the activity under test.
     */
    @Rule
    public IntentsTestRule<ImageViewerActivity> mIntentsRule = new IntentsTestRule<>(
            ImageViewerActivity.class);

    @Before
    public void stubCameraIntent() {
        ActivityResult result = createImageCaptureActivityResultStub();

        // Stub the Intent.
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);
    }

    @Test
    public void takePhoto_drawableIsApplied() {
        // Check that the ImageView doesn't have a drawable applied.
        onView(withId(R.id.imageView)).check(matches(not(hasDrawable())));

        // Click on the button that will trigger the stubbed intent.
        onView(withId(R.id.button_take_photo)).perform(click());

        // With no user interaction, the ImageView will have a drawable.
        onView(withId(R.id.imageView)).check(matches(hasDrawable()));
    }

    private ActivityResult createImageCaptureActivityResultStub() {
        // Put the drawable in a bundle.
        Bundle bundle = new Bundle();
        bundle.putParcelable(ImageViewerActivity.KEY_IMAGE_DATA, BitmapFactory.decodeResource(
                mIntentsRule.getActivity().getResources(), R.mipmap.ic_launcher));

        // Create the Intent that will include the bundle.
        Intent resultData = new Intent();
        resultData.putExtras(bundle);

        // Create the ActivityResult with the Intent.
        return new ActivityResult(Activity.RESULT_OK, resultData);
    }
}