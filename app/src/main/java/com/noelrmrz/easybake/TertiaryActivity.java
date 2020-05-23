package com.noelrmrz.easybake;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.noelrmrz.easybake.POJO.Recipe;
import com.noelrmrz.easybake.POJO.Step;
import com.noelrmrz.easybake.fragments.FragmentButton;
import com.noelrmrz.easybake.fragments.FragmentInstructions;
import com.noelrmrz.easybake.fragments.FragmentVideo;
import com.noelrmrz.easybake.utilities.GsonClient;

public class TertiaryActivity extends FragmentActivity
        implements FragmentButton.ViewOnClickHandler {

    private final String FRAGMENT_INSTRUCTION_TAG = "Instruction Fragment";
    private final String FRAGMENT_VIDEO_TAG = "Video Fragment";
    private final String FRAGMENT_NBUTTON_TAG = "Next Button Fragment";
    private final String FRAGMENT_PBUTTON_TAG = "Previous Button Fragment";

    private int mPosition;
    private Step mCurrentStep;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Retrieve the intent that started this activity
        Intent intentThatStartedThisActivity = getIntent();

        // Check if its null and if it has an Extra
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                String json = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                mPosition = intentThatStartedThisActivity.getIntExtra(Intent.EXTRA_INDEX, 0);
                setRecipe(json);
            }
        }

        if (savedInstanceState != null) {
            String json = savedInstanceState.getString("json");
            mPosition = savedInstanceState.getInt(Intent.EXTRA_INDEX, 0);
            setRecipe(json);
        }
            // Create a new media fragment
            FragmentVideo fragmentVideo = FragmentVideo.newInstance(mCurrentStep.getmVideoUrl());

            // Create a new instructions fragment
            FragmentInstructions fragmentInstructions = FragmentInstructions.newInstance(mCurrentStep.getmDescription());

            // Create a new next button fragment
            FragmentButton nextButton = FragmentButton.newInstance(this, getString(R.string.next));

            // Create a new previous button fragment
            FragmentButton previousButton = FragmentButton.newInstance(this, getString(R.string.previous));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_next_button, nextButton, FRAGMENT_NBUTTON_TAG)
                .replace(R.id.fragment_container_previous_button, previousButton, FRAGMENT_PBUTTON_TAG)
                .replace(R.id.fragment_container_instructions, fragmentInstructions, FRAGMENT_INSTRUCTION_TAG)
                .replace(R.id.fragment_container_media, fragmentVideo, FRAGMENT_VIDEO_TAG)
                .commit();
    }

    /*
    Updates the instruction TextView when the user clicks on the TextView Buttons
    Updates the Playerview
     */
    @Override
    public void onClick(String direction) {

        FragmentVideo fragmentVideo = (FragmentVideo) getSupportFragmentManager().findFragmentByTag(FRAGMENT_VIDEO_TAG);
        FragmentInstructions fragmentInstructions = (FragmentInstructions) getSupportFragmentManager().findFragmentByTag(FRAGMENT_INSTRUCTION_TAG);

        if (direction.equalsIgnoreCase(getString(R.string.next)) && mRecipe.getmSteps().get(mPosition).getmId()
                < mRecipe.getmSteps().size() - getResources().getInteger(R.integer.one)) {
            mPosition++;
            fragmentInstructions.updateView(mRecipe.getmSteps().get(mPosition).getmDescription());
            fragmentVideo.setUrl(mRecipe.getmSteps().get(mPosition).getmVideoUrl());
            fragmentVideo.initializePlayer();
        }
        else if (direction.equalsIgnoreCase((getString(R.string.previous))) && mPosition
                > getResources().getInteger(R.integer.zero)) {
            mPosition--;
            fragmentInstructions.updateView(mRecipe.getmSteps().get(mPosition).getmDescription());
            fragmentVideo.setUrl(mRecipe.getmSteps().get(mPosition).getmVideoUrl());
            fragmentVideo.initializePlayer();
        }

        mCurrentStep = mRecipe.getmSteps().get(mPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putInt(Intent.EXTRA_INDEX, mPosition);
        outstate.putString("json", GsonClient.getGsonClient().toJson(mRecipe, Recipe.class));
    }

    private void setRecipe(String jsonRecipe) {
        mRecipe = GsonClient.getGsonClient().fromJson(jsonRecipe, Recipe.class);
        mCurrentStep = mRecipe.getmSteps().get(mPosition);
    }
}
