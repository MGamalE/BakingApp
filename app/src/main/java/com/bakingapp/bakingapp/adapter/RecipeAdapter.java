package com.bakingapp.bakingapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bakingapp.bakingapp.model.Recipe;
import com.bakingapp.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    Context mContext;
    private ArrayList<Recipe> recipe;


    public RecipeAdapter(ArrayList<Recipe> recipeList, Context mContext) {

        this.mContext = mContext;
        this.recipe = recipeList;

    }


    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recipe_item_card, parent, false);
        return new RecipeHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        holder.recipeName.setText(recipe.get(position).getName());
        String image = recipe.get(position).getImage();


        if (!(image.isEmpty())) {
            Uri builtUri = Uri.parse(image).buildUpon().build();
            Picasso.with(mContext)
                    .load(builtUri)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .into(holder.recipeImage);
        }
    }

    @Override
    public int getItemCount() {
        return recipe.size();
    }

    static class RecipeHolder extends RecyclerView.ViewHolder {
        ImageView recipeImage;
        TextView recipeName;

        public RecipeHolder(View itemView) {
            super(itemView);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipe_image);
            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
        }


    }
}
