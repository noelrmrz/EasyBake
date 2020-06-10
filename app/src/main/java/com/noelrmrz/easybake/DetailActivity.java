package com.noelrmrz.easybake;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.noelrmrz.easybake.POJO.Recipe;
import com.noelrmrz.easybake.POJO.Step;
import com.noelrmrz.easybake.fragments.FragmentInstructions;
import com.noelrmrz.easybake.fragments.FragmentStep;
import com.noelrmrz.easybake.fragments.FragmentVideo;
import com.noelrmrz.easybake.utilities.GsonClient;

public class DetailActivity extends FragmentActivity implements StepAdapter.StepAdapterOnClickHandler {
    private final String RECIPE_STEPS_TAG = "Recipe Steps";
    private final String FRAGMENT_INSTRUCTION_TAG = "Instruction Fragment";
    private final String FRAGMENT_VIDEO_TAG = "Video Fragment";
    private String mJsonRecipe;
    private boolean mTwoPane;

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

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            mJsonRecipe = savedInstanceState.getString("jsonRecipe");
        }

        // Determine if you are creating a two pane or single pane layout
        if (findViewById(R.id.easy_bake_linear_layout) != null) {
            // This layout will only exist in the two pane tablet case
            mTwoPane = true;
            Recipe recipe = GsonClient.getGsonClient().fromJson(mJsonRecipe, Recipe.class);

            // Create a new step fragment
            FragmentStep steps = FragmentStep.newInstance(mJsonRecipe);

            // Create a new media fragment
            FragmentVideo fragmentVideo = FragmentVideo.newInstance(recipe.getmSteps()
                            .get(getResources().getInteger(R.integer.zero)).getmVideoUrl(),
                    recipe.getmSteps().get(getResources().getInteger(R.integer.zero))
                            .getmThumbnailUrl());

            // Create a new instructions fragment
            FragmentInstructions fragmentInstructions = FragmentInstructions
                    .newInstance(recipe.getmSteps().get(getResources()
                            .getInteger(R.integer.zero)).getmDescription());

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_steps, steps, RECIPE_STEPS_TAG)
                        .replace(R.id.fragment_container_media, fragmentVideo, FRAGMENT_VIDEO_TAG)
                        .replace(R.id.fragment_container_instructions, fragmentInstructions, FRAGMENT_INSTRUCTION_TAG)
                        .commit();
        } else {
            // We're in single pane mode and display fragments on the phone in seperate activities
            mTwoPane = false;

            // Create a new step fragment
            FragmentStep steps = FragmentStep.newInstance(mJsonRecipe);

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_steps, steps, RECIPE_STEPS_TAG)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);

        outstate.putString("jsonRecipe", mJsonRecipe);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(Step step) {
        // Start the new activity for the selected step
        if (!mTwoPane) {
            Intent intent = new Intent(this, TertiaryActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, mJsonRecipe);
            intent.putExtra(Intent.EXTRA_INDEX, step.getmId());
            startActivity(intent);
        }
        else {
            FragmentVideo fragmentVideo = (FragmentVideo) getSupportFragmentManager()
                    .findFragmentByTag(FRAGMENT_VIDEO_TAG);
            FragmentInstructions fragmentInstructions = (FragmentInstructions)
                    getSupportFragmentManager().findFragmentByTag(FRAGMENT_INSTRUCTION_TAG);

            fragmentInstructions.updateView(step.getmDescription());
            fragmentVideo.setUrl(step.getmVideoUrl());
            fragmentVideo.initializePlayer();
        }
    }
}
