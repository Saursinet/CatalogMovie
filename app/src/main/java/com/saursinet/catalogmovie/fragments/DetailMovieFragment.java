package com.saursinet.catalogmovie.fragments;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.saursinet.catalogmovie.R;
import com.saursinet.catalogmovie.models.Movie;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.detail_movie)
public class DetailMovieFragment extends Fragment {

    @ViewById(R.id.imageView)
    ImageView mPoster;
    @ViewById(R.id.titleMovie)
    TextView mTitle;
    @ViewById(R.id.overview)
    TextView mOverview;
    @ViewById(R.id.video_player)
    VideoView videoView;
    @ViewById(R.id.play_button)
    ImageButton mPlayButton;

    @FragmentArg("movie")
    Movie movie;

    private MediaController mController;

    public static final String SAMPLE_1 = "https://developers.google.com/training/images/tacoma_narrows.mp4";

    @AfterViews
    void initImage() {
        Uri uri = Uri.parse(SAMPLE_1);

        mController = new MediaController(getContext());
        mController.setAnchorView(videoView);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mPlayButton.setVisibility(View.VISIBLE);
            }
        });

        videoView.setMediaController(mController);
        videoView.setVideoURI(uri);

        mTitle.setText(movie.getTitle());
        mOverview.setText("Overview : " + movie.getOverview());

    }

    @Click(R.id.play_button)
    void onClick() {
        mPlayButton.setVisibility(View.INVISIBLE);
        videoView.start();
    }
}
