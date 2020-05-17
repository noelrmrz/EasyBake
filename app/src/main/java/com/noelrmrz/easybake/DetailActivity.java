package com.noelrmrz.easybake;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.noelrmrz.easybake.fragments.FragmentStep;

public class DetailActivity extends FragmentActivity {
    //private final String INTENT_EXTRA_TAG = getString(Intent.EXTRA_TEXT);
    private final String RECIPE_STEPS_TAG = "Recipe Steps";
    private String mJsonRecipe;
    private boolean mTwoPane;
    private FragmentStep fragmentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intentThatStartedThisActivity = getIntent();

        // Check for extras in the Intent
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mJsonRecipe = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            }
        }

        // Retrieve existing Fragment instance if it exists
        // else create a new instance
        if (savedInstanceState != null) {
            fragmentStep = (FragmentStep) getSupportFragmentManager().findFragmentByTag(RECIPE_STEPS_TAG);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_steps, fragmentStep, RECIPE_STEPS_TAG)
                    .commit();
        }
        else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            // Create a new step fragment
            FragmentStep steps = FragmentStep.newInstance(mJsonRecipe);

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container_steps, steps, RECIPE_STEPS_TAG)
                    .commit();
        }

        // Determine if you are creating a two pane or single pane layout
        if (findViewById(R.id.easy_bake_linear_layout) != null) {
            // This layout will only exist in the two pane tablet case
            mTwoPane = true;

/*            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                // Create a new step fragment
                FragmentStep steps = FragmentStep.newInstance(mJsonRecipe);

                // Create a new media fragment
                FragmentVideo media = new FragmentVideo("someURL");

                // Create a new instructions fragment
                FragmentInstructions instructions = new FragmentInstructions();

                // Create a new button fragment
                FragmentButton button = new FragmentButton();

                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container_steps, steps, RECIPE_STEPS_TAG)
                        .commit();
            }*/
        } else {
            // We're in single pane mode and display fragments on the phone in seperate activities
            mTwoPane = false;
        }
    }
}
