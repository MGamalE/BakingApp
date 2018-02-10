package com.bakingapp.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bakingapp.bakingapp.model.Step;
import com.bakingapp.bakingapp.R;

import java.util.ArrayList;


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    Context context;
    private ArrayList<Step> step;

    public StepAdapter(ArrayList<Step> step, Context context) {
        this.step = step;
        this.context = context;
    }


    @Override
    public StepAdapter.StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.step_list_item, parent, false);
        return new StepHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepHolder holder, int position) {
        holder.short_description.setText(step.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return step.size();
    }

    class StepHolder extends RecyclerView.ViewHolder {
        TextView short_description;

        StepHolder(View itemView) {
            super(itemView);
            short_description = (TextView) itemView.findViewById(R.id.short_description);
        }

    }
}
