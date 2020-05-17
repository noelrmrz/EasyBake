package com.noelrmrz.easybake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noelrmrz.easybake.POJO.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private Recipe[] mRecipes;
    private final RecipeAdapterOnClickHandler mClickHandler;
    private Context mContext;

    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
                                                                    int viewType) {
        // Get the Context and ID of our layout for the list items in RecyclerView
        mContext = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;

        // Get the LayoutInflater
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;

        // Inflate our layout into the view
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeAdapterViewHolder viewHolder,
                                 int position) {
        viewHolder.recipeName.setText(mRecipes[position].getmName());
        viewHolder.recipeServings.setText(mContext.getString(R.string.servings)
                .concat(String.valueOf(mRecipes[position].getmServings())));
        viewHolder.recipeIngredientCount.setText(mContext.getString(R.string.ingredients)
                .concat(String.valueOf(mRecipes[position].getmIngredients().size())));
        // TODO background image if they exist if not use a default image
    }

    @Override
    public int getItemCount() {
        // Check to verify if we have recipes in the list
        if (mRecipes == null) {
            return  0;
        }
        else {
            return mRecipes.length;
        }
    }


    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        public TextView recipeName;
        public TextView recipeServings;
        public TextView recipeIngredientCount;
        public ImageView recipeImage;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            recipeName = view.findViewById(R.id.tv_card_recipe_name);
            recipeServings = view.findViewById(R.id.tv_card_servings);
            recipeIngredientCount = view.findViewById(R.id.tv_card_ingredients);
            recipeImage = view.findViewById(R.id.iv_card_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipes[adapterPosition];
            mClickHandler.onClick(recipe);
        }
    }

    /*
    Updates the Recipes in the list
     */
    public void setRecipeList(Recipe[] recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }
}