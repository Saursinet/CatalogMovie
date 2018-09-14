package com.saursinet.catalogmovie.adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.saursinet.catalogmovie.models.Movie;
import com.saursinet.catalogmovie.views.ViewWrapper;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {

    protected List<T> mItems = new ArrayList<>();

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public void addItems(List<T> items, int position) {
        if (mItems != null && items != null) {
            mItems.addAll(position, items);
        }
    }

    public void addItem(T item, int position) {
        if(mItems != null && item != null) {
            mItems.add(position,item);
        }
    }

    public void clearItems() {
        if (mItems != null) {
            mItems.clear();
        }
    }

    public void removeItem(int position) {
        if (mItems != null) {
            mItems.remove(position);
        }
    }

    public int size() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }
}
