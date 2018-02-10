package com.bakingapp.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.bakingapp.model.Ingredient;
import com.bakingapp.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by Mohammad on 31/01/2018.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {

    Context mContext;
    private ArrayList<Ingredient> ingredient;

    public IngredientAdapter(Context mContext, ArrayList<Ingredient> ingredient) {
        this.ingredient = ingredient;
        this.mContext = mContext;
    }

    @Override
    public IngredientAdapter.IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.ingredient_list_item, parent, false);
        return new IngredientHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapter.IngredientHolder holder, int position) {

        holder.quantity.setText(String.valueOf(ingredient.get(position).getQuantity()));
        holder.measure.setText(ingredient.get(position).getMeasure());
        holder.ingredient_name.setText(ingredient.get(position).getIngredient());

    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }

    class IngredientHolder extends RecyclerView.ViewHolder {
        TextView quantity, measure, ingredient_name;

        IngredientHolder(View itemView) {
            super(itemView);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            measure = (TextView) itemView.findViewById(R.id.measure);
            ingredient_name = (TextView) itemView.findViewById(R.id.ingredient_name);

        }
    }


}
