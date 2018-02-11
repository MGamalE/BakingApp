package com.bakingapp.bakingapp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bakingapp.bakingapp.R;
import com.bakingapp.bakingapp.model.Recipe;
import com.bakingapp.bakingapp.model.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class StepDetailFragment extends android.app.Fragment {

    ArrayList<Step> step;
    int position;
    ArrayList<Recipe> recipe;
    long pos;
    String video;
    Boolean getPlayerWhenReady = true;
    private SimpleExoPlayer player;
    private SimpleExoPlayerView exoPlayerView;
    private TextView stepDescription;
    private ImageView recipeStepImage;
    private Button previousStep, nextStep;

    public StepDetailFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        setHasOptionsMenu(true);

        exoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.step_video);
        stepDescription = (TextView) rootView.findViewById(R.id.step_description);
        previousStep = (Button) rootView.findViewById(R.id.previous_step);
        nextStep = (Button) rootView.findViewById(R.id.next_step);
        recipeStepImage = (ImageView) rootView.findViewById(R.id.recipe_step_image);

        if (savedInstanceState != null) {

            step = savedInstanceState.getParcelableArrayList("steps");
            position = savedInstanceState.getInt("position");
            pos = savedInstanceState.getLong("position_player");
            getPlayerWhenReady = savedInstanceState.getBoolean("state");
        } else {
            step = getArguments().getParcelableArrayList("steps");
            position = getArguments().getInt("position");

        }

        handleUi();


        previousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                handleUi();
            }
        });

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                handleUi();
            }
        });


        return rootView;


    }

    public void handleUi() {

        if (position == 0) {
            previousStep.setVisibility(View.GONE);
        } else {
            previousStep.setVisibility(View.VISIBLE);

        }

        if (position >= step.size() - 1) {
            nextStep.setVisibility(View.GONE);
        } else {
            nextStep.setVisibility(View.VISIBLE);
        }

        video = step.get(position).getVideoURL();
        String image = step.get(position).getThumbnailURL();

        if (!video.isEmpty()) {
            initializePlayer(Uri.parse(video));
            exoPlayerView.setVisibility(View.VISIBLE);
            recipeStepImage.setVisibility(View.GONE);
        } else if (!image.isEmpty()) {
            Picasso.with(getActivity()).load(image).placeholder(R.drawable.recipe_image).into(recipeStepImage);
            exoPlayerView.setVisibility(View.GONE);
            recipeStepImage.setVisibility(View.VISIBLE);

        } else {
            exoPlayerView.setVisibility(View.GONE);
            recipeStepImage.setVisibility(View.VISIBLE);
            recipeStepImage.setImageResource(R.drawable.recipe_image);
        }

        stepDescription.setText(step.get(position).getShortDescription());


    }


    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }


    public void initializePlayer(Uri uri) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            if (pos != C.TIME_UNSET) player.seekTo(pos);
            exoPlayerView.setPlayer(player);
            player.setPlayWhenReady(getPlayerWhenReady);
            MediaSource mediaSource = buildMediaSource(uri);
            player.prepare(mediaSource, true, false);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelableArrayList("steps");
            position = savedInstanceState.getInt("position");
            pos = savedInstanceState.getLong("position_player");
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        initializePlayer(Uri.parse(video));
    }

    @Override
    public void onPause() {
        getPlayerWhenReady = player.getPlayWhenReady();
        super.onPause();
        if (player != null) {
            pos = player.getCurrentPosition();
            player.stop();
            player.release();
            player = null;
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();

    }


    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList("steps", step);
        currentState.putInt("position", position);
        currentState.putLong("position_player", pos);
        currentState.putBoolean("state", getPlayerWhenReady);
    }

}
