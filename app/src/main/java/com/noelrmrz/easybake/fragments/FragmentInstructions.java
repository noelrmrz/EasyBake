package com.noelrmrz.easybake.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.noelrmrz.easybake.R;

public class FragmentInstructions extends Fragment{

    private TextView mTextView;
    private String mInstructions;

    public FragmentInstructions() {

    }

    public static FragmentInstructions newInstance(String instructions) {
        Bundle args = new Bundle();
        FragmentInstructions fragmentInstructions = new FragmentInstructions();
        args.putString(Intent.EXTRA_TEXT, instructions);
        fragmentInstructions.setArguments(args);
        return fragmentInstructions;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        // Retrieve arguments from savedInstanceState or arguments
        if (savedInstanceState != null) {
            mInstructions = savedInstanceState.getString(Intent.EXTRA_TEXT);
        } else {
            mInstructions = getArguments().getString(Intent.EXTRA_TEXT);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextView = view.findViewById(R.id.tv_step_long_description);
        mTextView.setText(mInstructions);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_long_description, container, false);
        return view;
    }

    public void updateView(String instructions) {
        mInstructions = instructions;
        mTextView.setText(mInstructions);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putString(Intent.EXTRA_TEXT, mInstructions);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

}
