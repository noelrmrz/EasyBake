package com.noelrmrz.easybake;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    @Rule
    public IntentsTestRule<MainActivity> mainActivityActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }
    /**
     * Clicks on a RecyclerView item and checks it opens up the DetailActivity with the correct details;
     */

    @Test
    public void clickRecyclerViewItem_OpensDetailActivity() {

        // Let the UI completely load with fetched data

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Perform Click operation on RecyclerView item
        onView(ViewMatchers.withId(R.id.rv_cards)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withId(R.id.rv_cards)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Check intent is received on receiving intent
        intended(hasExtraWithKey(Intent.EXTRA_TEXT));

        // Perform click operation on Step recyclerView item
        onView(withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        // Check the correct component is started
        intended(hasComponent(TertiaryActivity.class.getName()));
    }
}
