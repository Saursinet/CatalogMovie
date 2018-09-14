package com.saursinet.catalogmovie.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.saursinet.catalogmovie.R;
import com.saursinet.catalogmovie.models.Movie;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.lang.ref.WeakReference;

@EViewGroup(R.layout.list_cell)
public class MovieItemView extends RelativeLayout implements SpringListener {

    String url = "https://image.tmdb.org/t/p/original";

    @ViewById
    TextView movie_textview;
    @ViewById
    TextView vote;
    @ViewById
    ImageView cover;
    @ViewById
    ImageButton like_button;
    @ViewById
    RelativeLayout relative;

    private static double TENSION = 800;
    private static double DAMPER = 20; //friction

    private SpringSystem mSpringSystem;
    private Spring mSpring;

    @Click(R.id.relative)
    void onClickMovie(){
        wr_listener.get().onClickMovie(movie);
    }

    @Click(R.id.like_button)
    void onClickLike(){
        mSpring.setEndValue(-1f);
    }

    private Movie movie;

    public interface IMovieItemViewListener {
        void onClickMovie(Movie movie);
    }
    private WeakReference<IMovieItemViewListener> wr_listener;

    public void setListener (IMovieItemViewListener listener) {
        this.wr_listener = new WeakReference<>(listener);
    }

    public MovieItemView(Context context) {
        super(context);
    }

    public MovieItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void bind(Movie movie) {
        this.movie = movie;
        movie_textview.setText(movie.getTitle());
        vote.setText(movie.getVoteAverage() + " / 10");
        Picasso.get().load(url + movie.getPosterPath()).into(cover);

        mSpringSystem = SpringSystem.create();

        mSpring = mSpringSystem.createSpring();
        mSpring.addListener(this);

        SpringConfig config = new SpringConfig(TENSION, DAMPER);
        mSpring.setSpringConfig(config);
    }

    @Override
    public void onSpringUpdate(Spring spring) {
        float value = (float) spring.getCurrentValue();
        float scale = 1f - (value * 0.5f);

        like_button.setScaleX(scale);
        like_button.setScaleY(scale);
    }

    @Override
    public void onSpringAtRest(Spring spring) {
    }

    @Override
    public void onSpringActivate(Spring spring) {
//        final int newColor = getContext().getResources().getColor(R.color.material_drawer_accent);
//        relative.setBackgroundColor(newColor);

    }

    @Override
    public void onSpringEndStateChange(Spring spring) {
//        final int newColor = getContext().getResources().getColor(R.color.colorPrimaryLight);
//        like_button.setColorFilter(newColor, PorterDuff.Mode.SRC_ATOP);
    }
}
