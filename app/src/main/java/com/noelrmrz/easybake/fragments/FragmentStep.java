package com.noelrmrz.easybake.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noelrmrz.easybake.POJO.Recipe;
import com.noelrmrz.easybake.POJO.Step;
import com.noelrmrz.easybake.R;
import com.noelrmrz.easybake.StepAdapter;
import com.noelrmrz.easybake.TertiaryActivity;
import com.noelrmrz.easybake.utilities.GsonClient;

import java.util.ArrayList;

public class FragmentStep extends Fragment implements StepAdapter.StepAdapterOnClickHandler {

    private static Recipe mRecipe;

    private RecyclerView mRecyclerView;
    private StepAdapter mStepAdapter;

    public FragmentStep() {

    }

    /*
    Returns an instance of FragmentStep
     */
    public static FragmentStep newInstance(String jsonRecipe) {
        Bundle args = new Bundle();
        mRecipe = GsonClient.getGsonClient().fromJson(jsonRecipe, Recipe.class);
        FragmentStep fragmentStep = new FragmentStep();
        args.putString(Intent.EXTRA_TEXT, jsonRecipe);
        fragmentStep.setArguments(args);
        return fragmentStep;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mRecipe = GsonClient.getGsonClient().fromJson(getArguments().getString(Intent.EXTRA_TEXT), Recipe.class);
        }

        mStepAdapter = new StepAdapter(this);
        mStepAdapter.setStepList(convertToArray(mRecipe.getmSteps()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.rv_steps);
        mRecyclerView.setAdapter(mStepAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_layout, container,
                false);
        return view;
    }

    @Override
    public void onClick(Step step) {
        // Start the new activity for the selected step
        Intent intent = new Intent(getActivity(), TertiaryActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, GsonClient.getGsonClient().toJson(mRecipe,
                mRecipe.getClass()));
        intent.putExtra(Intent.EXTRA_INDEX, step.getmId());
        startActivity(intent);
    }

    public Step[] convertToArray(ArrayList<Step> arrayList) {
        Step[] newlist = new Step[arrayList.size()];
        for (int x = 0; x < arrayList.size(); x++) {
            newlist[x] = arrayList.get(x);
        }
        return newlist;
    }
}
