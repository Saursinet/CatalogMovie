package com.saursinet.catalogmovie.adaptors;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.mikepenz.iconics.utils.Utils;
import com.saursinet.catalogmovie.models.Movie;
import com.saursinet.catalogmovie.views.MovieItemView;
import com.saursinet.catalogmovie.views.MovieItemView_;
import com.saursinet.catalogmovie.views.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.lang.ref.WeakReference;

@EBean
public class MovieAdaptor extends RecyclerViewAdapterBase<Movie, MovieItemView> implements MovieItemView.IMovieItemViewListener {

    @RootContext
    Context context;

    public interface IMovieAdapterListener {
        void onClickMovie(Movie movie);
    }

    private WeakReference<IMovieAdapterListener> wr_listener;

    public void setListener(IMovieAdapterListener listener) {
        this.wr_listener = new WeakReference<>(listener);
    }

    MovieAdaptor(Context ctx){
        context = ctx;
    }

    @Override
    protected MovieItemView onCreateItemView(ViewGroup parent, int viewType) {
        return MovieItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<MovieItemView> viewHolder, int position) {
        MovieItemView view = viewHolder.getView();
        Movie movie = mItems.get(position);
        view.setListener(this);
        view.bind(movie);
    }

    @Override
    public void onClickMovie(Movie city) {
        wr_listener.get().onClickMovie(city);
    }

}
