package com.noelrmrz.easybake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noelrmrz.easybake.POJO.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    private Step[] mSteps;
    private final StepAdapterOnClickHandler mClickHandler;

    public StepAdapter(StepAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public StepAdapter.StepAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                                int viewType) {
        // Get the Context and ID of our layout for the list items in RecyclerView
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.fragment_step;

        // Get the LayoutInflater
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        // Inflate our layout into the view
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.StepAdapterViewHolder viewHolder,
                                 int position) {
        viewHolder.stepDescription.setText(mSteps[position].getmShortDescription());

        // Accounting for zero indexed instruction steps
        if (position != 0) {
            viewHolder.stepNumber.setText(String.valueOf(position));
        }
    }

    @Override
    public int getItemCount() {
        // Check to verify if we have steps in the list
        if (mSteps == null) {
            return  0;
        }
        else {
            return mSteps.length;
        }
    }

    public interface StepAdapterOnClickHandler {
        void onClick(Step step);
    }

    public class StepAdapterViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public TextView stepDescription;
        public TextView stepNumber;

        public StepAdapterViewHolder(View view) {
            super(view);
            stepDescription = view.findViewById(R.id.tv_step_short_instruction);
            stepNumber = view.findViewById(R.id.tv_step_number);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step step = mSteps[adapterPosition];
            mClickHandler.onClick(step);
        }
    }

    /*
    Updates the steps in the step list
     */
    public void setStepList(Step[] steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }
}
