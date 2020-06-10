package com.noelrmrz.easybake.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.noelrmrz.easybake.R;
import com.noelrmrz.easybake.utilities.PicassoClient;

public class FragmentVideo extends Fragment {

    private String mVideoUrl;
    private String mThumbnailUrl;
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;
    private ImageView mImageView;

    private final String PLAY_WHEN_READY = "playWhenReady";
    private final String CURRENT_WINDOW = "currentWindow";
    private final String PLAYBACK_POSITION = "playbackPosition";
    private final static String THUMBNAIL_URL = "thumbnailUrl";

    private boolean playWhenReady = true;
    private int currentWindow;
    private long playbackPosition;
    private int orientation = Configuration.ORIENTATION_PORTRAIT;
    private Activity mActivity;

    public FragmentVideo() {
    }

    public static FragmentVideo newInstance(String videoUrl, String thumbnailUrl) {
        Bundle args = new Bundle();
        FragmentVideo fragmentVideo = new FragmentVideo();
        args.putString(Intent.EXTRA_TEXT, videoUrl);
        args.putString(THUMBNAIL_URL, thumbnailUrl);
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
        mActivity = getActivity();

        if (savedInstanceState != null) {
            mThumbnailUrl = savedInstanceState.getString(THUMBNAIL_URL);
            mVideoUrl = savedInstanceState.getString(Intent.EXTRA_TEXT);
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW);
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
        }
        else {
            mVideoUrl = getArguments().getString(Intent.EXTRA_TEXT);
            mThumbnailUrl = getArguments().getString(THUMBNAIL_URL);
        }

        orientation = mActivity.getResources().getConfiguration().orientation;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the player view
        mPlayerView = view.findViewById(R.id.player_view);
        mImageView = view.findViewById(R.id.iv_default_player_image);

        // Check if the video url is empty, if it is make its view gone and show the thumbnail
        if (!(mVideoUrl.isEmpty())) {
            mImageView.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);
        }
        else {
            // Check if the thumnail url is empty, if it is show a default image
            if (!(mThumbnailUrl.isEmpty())) {
                PicassoClient.downloadImage(mThumbnailUrl, mImageView);
            }
            else {
                mImageView.setImageResource(R.drawable.ic_icon);
            }
            mImageView.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
        }

        ViewGroup.LayoutParams params = mPlayerView.getLayoutParams();

        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                mPlayerView.setLayoutParams(params);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                mPlayerView.setLayoutParams(params);
                hideSystemUI();
                break;
        }

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
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the media source
            mExoPlayer.prepare(buildMediaSource(getUri()));
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }
        else {
            //loading new uri
            mExoPlayer.prepare(buildMediaSource(getUri()), false, false);
            //reset the playback position
            mExoPlayer.seekTo(0);
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
        return Uri.parse(mVideoUrl);
    }

    public void setUrl(String url) {
        mVideoUrl = url;
    }

    @Override
    public void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        updateStartPosition();
        outstate.putString(THUMBNAIL_URL, mThumbnailUrl);
        outstate.putString(Intent.EXTRA_TEXT, mVideoUrl);
        outstate.putBoolean(PLAY_WHEN_READY, playWhenReady);
        outstate.putInt(CURRENT_WINDOW, currentWindow);
        outstate.putLong(PLAYBACK_POSITION, playbackPosition);
    }
}
