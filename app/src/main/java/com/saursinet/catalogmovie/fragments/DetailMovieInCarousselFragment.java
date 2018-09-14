package com.saursinet.catalogmovie.fragments;

import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.saursinet.catalogmovie.R;
import com.saursinet.catalogmovie.models.Movie;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.caroussel_view)
public class DetailMovieInCarousselFragment extends Fragment {

    @FragmentArg("movie")
    Movie movie;

    @ViewById(R.id.title_caroussel)
    TextView title;

    @ViewById(R.id.image_in_caroussel)
    ImageView image;

    String url = "https://image.tmdb.org/t/p/original";

    @AfterViews
    public void initView() {
        title.setText(movie.getTitle());
        Picasso.get().load(url + movie.getPosterPath()).into(image);
    }
}
