package com.bakingapp.bakingapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bakingapp.bakingapp.R;
import com.bakingapp.bakingapp.fragment.RecipeDetailFragment;


public class RecipeDetail extends AppCompatActivity {

    RecipeDetailFragment recipeDetailFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_layout);

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        recipeDetailFragment = new RecipeDetailFragment();
        Bundle extras = getIntent().getBundleExtra("bundle");

        if (savedInstanceState != null) {
            recipeDetailFragment = (RecipeDetailFragment) getFragmentManager().getFragment(savedInstanceState, "recipeDetailFragment");
            if (!recipeDetailFragment.isAdded()) {
                if (tabletSize) {
                    getFragmentManager().beginTransaction().replace(R.id.recipe_master, recipeDetailFragment).commit();

                } else {
                    recipeDetailFragment.setArguments(extras);
                    getFragmentManager().beginTransaction().replace(R.id.recipe_detail, recipeDetailFragment).commit();

                }
            }

        } else {
            if (tabletSize) {
                recipeDetailFragment.setArguments(extras);
                getFragmentManager().beginTransaction().replace(R.id.recipe_master, recipeDetailFragment).commit();

            } else {
                recipeDetailFragment.setArguments(extras);
                getFragmentManager().beginTransaction().replace(R.id.recipe_detail, recipeDetailFragment).commit();

            }
        }



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "recipeDetailFragment", recipeDetailFragment);

    }

}
