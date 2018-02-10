package com.bakingapp.bakingapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bakingapp.bakingapp.R;
import com.bakingapp.bakingapp.fragment.StepDetailFragment;


public class StepDetail extends AppCompatActivity {

    StepDetailFragment stepDetailFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail_layout);

        if (savedInstanceState != null) {
            stepDetailFragment = (StepDetailFragment) getFragmentManager().getFragment(savedInstanceState, "stepDetailFragment");
        } else {
            stepDetailFragment = new StepDetailFragment();
            Bundle bundle = getIntent().getExtras();
            stepDetailFragment.setArguments(bundle);
        }


        if (!stepDetailFragment.isAdded()) {
            getFragmentManager().beginTransaction().replace(R.id.step_detail, stepDetailFragment).commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "stepDetailFragment", stepDetailFragment);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);


    }
}
