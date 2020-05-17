package com.noelrmrz.easybake.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.noelrmrz.easybake.R;

public class FragmentButton extends Fragment implements View.OnClickListener {

    private TextView mButtonTextView;
    private String mDirection;
    private static ViewOnClickHandler mClickHandler;

    public FragmentButton() {

    }

    public static FragmentButton newInstance(ViewOnClickHandler clickHandler, String direction) {
        Bundle args = new Bundle();
        FragmentButton fragmentButton = new FragmentButton();
        mClickHandler = clickHandler;
        args.putString(Intent.EXTRA_TEXT, direction);
        fragmentButton.setArguments(args);
        return fragmentButton;
    }

    /*
    Called when the fragment is being created or recreated
    Use onCreate for any standard setup that does not require the activity to be fully created
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDirection = getArguments().getString(Intent.EXTRA_TEXT);
    }


    /*
    Triggered after onCreateView
    only called if the view from onCreateView is non-null
    View setup should occur here
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButtonTextView = view.findViewById(R.id.tv_button);
        mButtonTextView.setText(mDirection);
    }

    /*
Called when the fragment should create its view object heirarchy
 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        mClickHandler.onClick(mDirection);
    }

    public interface ViewOnClickHandler {
        void onClick(String direction);
    }
}
