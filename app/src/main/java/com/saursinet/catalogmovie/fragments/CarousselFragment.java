package com.saursinet.catalogmovie.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.saursinet.catalogmovie.R;
import com.saursinet.catalogmovie.models.Movie;
import com.saursinet.catalogmovie.models.MovieResponse;
import com.saursinet.catalogmovie.services.MovieInterface;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@EFragment(R.layout.view_pager)
public class CarousselFragment extends Fragment {

    @ViewById(R.id.pager)
    ViewPager mPager;

    private PagerAdapter mPagerAdapter;
    MovieInterface movieService;

    int page = 1;
    int numberResult = -1;

    List<Movie> movieList;

    Retrofit retrofit;

    @AfterViews
    void initAdapter() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieService = retrofit.create(MovieInterface.class);

        addMovies(1);
        mPagerAdapter = new CarousselFragment.ScreenSlidePagerAdapter(getChildFragmentManager());

    }

    private static final int NUM_PAGES = 20;

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DetailMovieInCarousselFragment_.builder().movie(movieList.get(position)).build();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void addMovies(int page) {
        Call<MovieResponse> call = movieService.getMovies(page);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movies = response.body();

                if (numberResult == -1) {
                    numberResult = movies.getTotalResults();
                }
                movieList = movies.getResults();
                numberResult -= 20;
                mPager.setAdapter(mPagerAdapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                System.out.println("didn't work");
//                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
