package com.bakingapp.bakingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bakingapp.bakingapp.activity.RecyclerItemClickListener;
import com.bakingapp.bakingapp.activity.StepDetail;
import com.bakingapp.bakingapp.adapter.IngredientAdapter;
import com.bakingapp.bakingapp.adapter.StepAdapter;
import com.bakingapp.bakingapp.model.Ingredient;
import com.bakingapp.bakingapp.model.Recipe;
import com.bakingapp.bakingapp.model.Step;
import com.bakingapp.bakingapp.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class RecipeDetailFragment extends android.app.Fragment {
    ArrayList<Step> step;
    ArrayList<Ingredient> ingredient;
    ArrayList<Recipe> recipe;
    RecyclerView recyclerViewIngredient;
    RecyclerView recyclerViewStep;
    RecyclerView.LayoutManager IngredientlayoutManager;
    RecyclerView.LayoutManager SteplayoutManager;
    StepDetailFragment StepDetailFragment;
    int stepPosition = 0;
    int ingredientPosition = 0;

    boolean tabletSize;
    View rootView;

    public RecipeDetailFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        recyclerViewIngredient = (RecyclerView) rootView.findViewById(R.id.rv_ingredient);
        recyclerViewStep = (RecyclerView) rootView.findViewById(R.id.rv_step);

        tabletSize = getResources().getBoolean(R.bool.isTablet);


        if (savedInstanceState != null) {

            step = savedInstanceState.getParcelableArrayList("steps");
            ingredient = savedInstanceState.getParcelableArrayList("ingredients");
            stepPosition = savedInstanceState.getInt("step");
            ingredientPosition = savedInstanceState.getInt("ingredient");


        } else {
            Bundle extra = getArguments();
            ingredient = extra.getParcelableArrayList("ingredients");
            step = extra.getParcelableArrayList("steps");
        }


        IngredientlayoutManager = new LinearLayoutManager(getActivity());
        SteplayoutManager = new LinearLayoutManager(getActivity());

        recyclerViewIngredient.setLayoutManager(IngredientlayoutManager);
        recyclerViewStep.setLayoutManager(SteplayoutManager);

        recyclerViewIngredient.setAdapter(new IngredientAdapter(getActivity(), ingredient));

        StepAdapter adapter = new StepAdapter(step, getActivity());
        recyclerViewStep.setAdapter(adapter);

        recyclerViewStep.scrollToPosition(stepPosition);
        recyclerViewIngredient.scrollToPosition(ingredientPosition);


        recyclerViewStep.addOnItemTouchListener(
                new RecyclerItemClickListener(RecipeDetailFragment.this.getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {


                        if (tabletSize) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("steps", step);
                            bundle.putParcelableArrayList("recipes", recipe);
                            bundle.putInt("position", position);
                            StepDetailFragment = new StepDetailFragment();
                            StepDetailFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.recipe_master_detail, StepDetailFragment).commit();


                        } else {
                            Intent intentStep = new Intent(getActivity(), StepDetail.class);
                            intentStep.putParcelableArrayListExtra("steps", step);
                            intentStep.putExtra("position", position);
                            startActivity(intentStep);
                        }

                        Toast.makeText(RecipeDetailFragment.this.getActivity(), "This is Step Number :" + position, Toast.LENGTH_SHORT).show();

                    }
                })
        );

        (recyclerViewStep.getAdapter()).notifyDataSetChanged();


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pref_menu, menu);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("steps", step);
        outState.putParcelableArrayList("ingredients", ingredient);
        outState.putInt("step",
                ((LinearLayoutManager) recyclerViewStep.getLayoutManager()).findFirstVisibleItemPosition());
        outState.putInt("ingredient",
                ((LinearLayoutManager) recyclerViewIngredient.getLayoutManager()).findFirstVisibleItemPosition());

        if (tabletSize && StepDetailFragment != null)
            getFragmentManager().putFragment(outState, "stepDetailFragment", StepDetailFragment);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_Ingredients_widget:
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ingredientPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String ingre = gson.toJson(ingredient);
                editor.putString("ingredients", ingre);
                editor.commit();
                Toast.makeText(getActivity(), "Recipe Ingredient Added To Widget", Toast.LENGTH_SHORT).show();

                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


}
