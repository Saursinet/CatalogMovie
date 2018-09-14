package com.saursinet.catalogmovie.fragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.saursinet.catalogmovie.R;
import com.saursinet.catalogmovie.adaptors.MovieAdaptor;
import com.saursinet.catalogmovie.models.Movie;
import com.saursinet.catalogmovie.models.MovieResponse;
import com.saursinet.catalogmovie.services.MovieInterface;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@EFragment(R.layout.recycler_view)
public class RecyclerViewFragment extends Fragment implements MovieAdaptor.IMovieAdapterListener {

    @Bean
    MovieAdaptor myAdaptor;
    @ViewById(R.id.recyclerViewMovie)
    RecyclerView mRecyclerView;
    @ViewById(R.id.card_view)
    CardView mCardView;
    @ViewById(R.id.indeterminateBar)
    ProgressBar progressBar;
    @ViewById(android.support.v7.appcompat.R.id.search_button)
    ImageView searchImage;
    @ViewById(R.id.like_button)
    ImageButton likeButton;

    Retrofit retrofit;
    MovieInterface movieService;

    int page = 1;
    int numberResult = -1;

    @AfterViews
    void initFragment() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && numberResult > 0) {
                    ++page;
                    addMovies(page);
                }
            }
        });

        myAdaptor.setListener(this);

        mRecyclerView.setAdapter(myAdaptor);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieService = retrofit.create(MovieInterface.class);

        addMovies(page);

    }

    public void addMovies(int page) {
        Call<MovieResponse> call = movieService.getMovies(page);

        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movies = response.body();

                if (numberResult == -1) {
                    numberResult = movies.getTotalResults();
                }
                myAdaptor.addItems(movies.getResults(), myAdaptor.size());
                myAdaptor.notifyDataSetChanged();
                numberResult -= 20;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                System.out.println("didn't work");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClickMovie(Movie movie) {
        DetailMovieFragment detailFragment = DetailMovieFragment_.builder().movie(movie).build();
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, detailFragment).addToBackStack("detailMovieFragment").commit();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}