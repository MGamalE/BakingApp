package com.bakingapp.bakingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bakingapp.bakingapp.adapter.RecipeAdapter;
import com.bakingapp.bakingapp.api.Client;
import com.bakingapp.bakingapp.api.Service;
import com.bakingapp.bakingapp.model.Recipe;
import com.bakingapp.bakingapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Service service;
    RecipeAdapter adapter;

    ArrayList<Recipe> recipeList;
    Call<ArrayList<Recipe>> call;
    int position;
    private CountingIdlingResource mIdlingResource = new CountingIdlingResource("data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_recipe);

        if (savedInstanceState != null) {
            recipeList = savedInstanceState.getParcelableArrayList("recipeList");
            position = savedInstanceState.getInt("position");
        } else {
            recipeList = new ArrayList<>();
            position = 0;
            mIdlingResource.increment();
        }

        adapter = new RecipeAdapter(recipeList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if (recipeList != null && recipeList.size() != 0) {
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(position);
        } else {

            service = Client.getClient().create(Service.class);
            call = service.getRecipe();
            call.enqueue(new Callback<ArrayList<Recipe>>() {

                @Override
                public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                    recipeList = response.body();


                    adapter = new RecipeAdapter(recipeList, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    mIdlingResource.decrement();
                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    Intent intentRecipe = new Intent(MainActivity.this, RecipeDetail.class);

                                    Bundle bundle = new Bundle();
                                    bundle.putParcelableArrayList("steps", recipeList.get(position).getSteps());
                                    bundle.putParcelableArrayList("ingredients", recipeList.get(position).getIngredients());
                                    intentRecipe.putExtra("bundle", bundle);

                                    startActivity(intentRecipe);
                                    Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                                }
                            })
                    );
                    adapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "Recipes", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    Log.d("Error", "" + t.getMessage());
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            });

        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("recipeList", recipeList);
        outState.putInt("position", ((LinearLayoutManager) recyclerView.getLayoutManager())
                .findFirstCompletelyVisibleItemPosition());
    }


    @VisibleForTesting
    @NonNull
    public CountingIdlingResource getIdlingResource() {
        return mIdlingResource;
    }

}




