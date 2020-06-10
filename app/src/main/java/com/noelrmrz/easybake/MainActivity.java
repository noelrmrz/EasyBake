package com.noelrmrz.easybake;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.noelrmrz.easybake.POJO.Recipe;
import com.noelrmrz.easybake.utilities.GsonClient;
import com.noelrmrz.easybake.utilities.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    // String to be used for offline testing
    // private static final String JSON = "[{\"id\": 1,\"name\": \"Nutella Pie\",\"ingredients\": [{\"quantity\": 2,\"measure\": \"CUP\",\"ingredient\": \"Graham Cracker crumbs\"},{\"quantity\": 6,\"measure\": \"TBLSP\",\"ingredient\": \"unsalted butter, melted\"},{\"quantity\": 0.5,\"measure\": \"CUP\",\"ingredient\": \"granulated sugar\"},{\"quantity\": 1.5,\"measure\": \"TSP\",\"ingredient\": \"salt\"},{\"quantity\": 5,\"measure\": \"TBLSP\",\"ingredient\": \"vanilla\"},{\"quantity\": 1,\"measure\": \"K\",\"ingredient\": \"Nutella or other chocolate-hazelnut spread\"},{\"quantity\": 500,\"measure\": \"G\",\"ingredient\": \"Mascapone Cheese(room temperature)\"},{\"quantity\": 1,\"measure\": \"CUP\",\"ingredient\": \"heavy cream(cold)\"},{\"quantity\": 4,\"measure\": \"OZ\",\"ingredient\": \"cream cheese(softened)\"}],\"steps\": [{\"id\": 0,\"shortDescription\": \"Recipe Introduction\",\"description\": \"Recipe Introduction\",\"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\",\"thumbnailURL\": \"\"},{\"id\": 1,\"shortDescription\": \"Starting prep\",\"description\": \"1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.\",\"videoURL\": \"\",\"thumbnailURL\": \"\"},{\"id\": 2,\"shortDescription\": \"Prep the cookie crust.\",\"description\": \"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.\",\"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\",\"thumbnailURL\": \"\"},{\"id\": 3,\"shortDescription\": \"Press the crust into baking form.\",\"description\": \"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.\",\"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4\",\"thumbnailURL\": \"\"},{\"id\": 4,\"shortDescription\": \"Start filling prep\",\"description\": \"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.\",\"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4\",\"thumbnailURL\": \"\"},{\"id\": 5,\"shortDescription\": \"Finish filling prep\",\"description\": \"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.\",\"videoURL\": \"\",\"thumbnailURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4\"},{\"id\": 6,\"shortDescription\": \"Finishing Steps\",\"description\": \"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!\",\"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4\",\"thumbnailURL\": \"\"}],\"servings\": 8,\"image\": \"\"},{\"id\": 2,\"name\": \"Nutella Pie\",\"ingredients\": [{\"quantity\": 2,\"measure\": \"CUP\",\"ingredient\": \"Graham Cracker crumbs\"},{\"quantity\": 6,\"measure\": \"TBLSP\",\"ingredient\": \"unsalted butter, melted\"},{\"quantity\": 0.5,\"measure\": \"CUP\",\"ingredient\": \"granulated sugar\"},{\"quantity\": 1.5,\"measure\": \"TSP\",\"ingredient\": \"salt\"},{\"quantity\": 5,\"measure\": \"TBLSP\",\"ingredient\": \"vanilla\"},{\"quantity\": 1,\"measure\": \"K\",\"ingredient\": \"Nutella or other chocolate-hazelnut spread\"},{\"quantity\": 500,\"measure\": \"G\",\"ingredient\": \"Mascapone Cheese(room temperature)\"},{\"quantity\": 1,\"measure\": \"CUP\",\"ingredient\": \"heavy cream(cold)\"},{\"quantity\": 4,\"measure\": \"OZ\",\"ingredient\": \"cream cheese(softened)\"}],\"steps\": [{\"id\": 0,\"shortDescription\": \"Recipe Introduction\",\"description\": \"Recipe Introduction\",\"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4\",\"thumbnailURL\": \"\"},{\"id\": 1,\"shortDescription\": \"Starting prep\",\"description\": \"1. Preheat the oven to 350\\u00b0F. Butter a 9\\\" deep dish pie pan.\",\"videoURL\": \"\",\"thumbnailURL\": \"\"},{\"id\": 2,\"shortDescription\": \"Prep the cookie crust.\",\"description\": \"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.\",\"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4\",\"thumbnailURL\": \"\"},{\"id\": 3,\"shortDescription\": \"Press the crust into baking form.\",\"description\": \"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.\",\"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4\",\"thumbnailURL\": \"\"},{\"id\": 4,\"shortDescription\": \"Start filling prep\",\"description\": \"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.\",\"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4\",\"thumbnailURL\": \"\"},{\"id\": 5,\"shortDescription\": \"Finish filling prep\",\"description\": \"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.\",\"videoURL\": \"\",\"thumbnailURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4\"},{\"id\": 6,\"shortDescription\": \"Finishing Steps\",\"description\": \"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!\",\"videoURL\": \"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4\",\"thumbnailURL\": \"\"}],\"servings\": 8,\"image\": \"\"}]";

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_cards);
        mRecipeAdapter = new RecipeAdapter(this);
        mRecyclerView.setAdapter(mRecipeAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        ConnectivityManager cm = (ConnectivityManager) getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        // Check for network connectivity before fetching the data
        if (isConnected) {
            loadRecipes();
        }
        else {
            //no network connectivity
        }
    }

    public void loadRecipes() {
        RetrofitClient.getRecipeListObjects(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.isSuccessful()) {
                    // Convert the JsonObjects in the JsonArray to a POJO
                    Recipe[] recipes = GsonClient.getGsonClient().fromJson(response.body(),
                            Recipe[].class);
                    mRecipeAdapter.setRecipeList(recipes);
                }
                else {
                    Log.v(TAG,response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, GsonClient.getGsonClient().toJson(recipe,
                recipe.getClass()));
        startActivity(intent);
    }
}
