package com.noelrmrz.easybake.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noelrmrz.easybake.POJO.Recipe;
import com.noelrmrz.easybake.POJO.Step;
import com.noelrmrz.easybake.R;
import com.noelrmrz.easybake.StepAdapter;
import com.noelrmrz.easybake.utilities.GsonClient;

import java.util.ArrayList;

public class FragmentStep extends Fragment {

    private Recipe mRecipe;

    private RecyclerView mRecyclerView;
    private StepAdapter mStepAdapter;

    public FragmentStep() {

    }

    /*
    Returns an instance of FragmentStep
     */
    public static FragmentStep newInstance(String jsonRecipe) {
        Bundle args = new Bundle();
        FragmentStep fragmentStep = new FragmentStep();
        args.putString(Intent.EXTRA_TEXT, jsonRecipe);
        fragmentStep.setArguments(args);
        return fragmentStep;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecipe = GsonClient.getGsonClient().fromJson(getArguments().getString(Intent.EXTRA_TEXT), Recipe.class);
        mStepAdapter = new StepAdapter((StepAdapter.StepAdapterOnClickHandler) getActivity());
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

    public Step[] convertToArray(ArrayList<Step> arrayList) {
        Step[] newlist = new Step[arrayList.size()];
        for (int x = 0; x < arrayList.size(); x++) {
            newlist[x] = arrayList.get(x);
        }
        return newlist;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
