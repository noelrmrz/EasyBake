package com.noelrmrz.easybake.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.noelrmrz.easybake.R;

public class FragmentVideo extends Fragment {

    private String mURL;
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;

    private final String PLAY_WHEN_READY = "playWhenReady";
    private final String CURRENT_WINDOW = "currentWindow";
    private final String PLAYBACK_POSITION = "playbackPosition";

    private boolean playWhenReady = true;
    private int currentWindow;
    private long playbackPosition;

    public FragmentVideo() {
    }

    public static FragmentVideo newInstance(String url) {
        Bundle args = new Bundle();
        FragmentVideo fragmentVideo = new FragmentVideo();
        args.putString(Intent.EXTRA_TEXT, url);
        fragmentVideo.setArguments(args);
        return fragmentVideo;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mURL = savedInstanceState.getString(Intent.EXTRA_TEXT);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
        }
        else {
            mURL = getArguments().getString(Intent.EXTRA_TEXT);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the player view
        mPlayerView = view.findViewById(R.id.player_view);
        // initialize the player
        initializePlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exo_player, container, false);
        return view;
    }

    public void initializePlayer() {
        if (mExoPlayer == null) {
            // Create and instance of the ExoPlayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the media source
            mExoPlayer.prepare(buildMediaSource(getUri()));
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }
        else {
            //loading new uri
            mExoPlayer.prepare(buildMediaSource(getUri()), false, false);
        }
    }


    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), getString(R.string.app_name));
        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT < 24 || mExoPlayer == null)) {
            initializePlayer();
        }
        if (mExoPlayer != null) {
            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(playbackPosition);
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUI() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            updateStartPosition();
        }
        if(Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer != null) {
            updateStartPosition();
        }
        if(Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            updateStartPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    private void updateStartPosition() {
        if (mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
        }
    }

    private Uri getUri() {
        return Uri.parse(mURL);
    }

    public void setUrl(String url) {
        mURL = url;
    }

    @Override
    public void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        updateStartPosition();
        outstate.putString(Intent.EXTRA_TEXT, mURL);
        outstate.putBoolean(PLAY_WHEN_READY, playWhenReady);
        outstate.putInt(CURRENT_WINDOW, currentWindow);
        outstate.putLong(PLAYBACK_POSITION, playbackPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // TODO change to fullscreen
            mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            hideSystemUI();

        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // TODO change to portrait
            mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

}
