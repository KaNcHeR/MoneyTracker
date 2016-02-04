package com.agrotrading.kancher.moneytracker.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.Model;
import com.agrotrading.kancher.moneytracker.ViewWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class RecyclerViewAdapterBase<T extends Model, V extends View> extends SelectableAdapter<ViewWrapper<V>> {

    protected List<T> items = new ArrayList<>();
    protected ViewWrapper.ClickListener clickListener;

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType), clickListener);
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    public void init(List<T> items, ViewWrapper.ClickListener clickListener) {
        this.items = items;
        this.clickListener = clickListener;
    }

    public void removeItem(int position) {
        if(items.get(position) != null) {
            items.get(position).delete();
            items.remove(position);
        }
    }

    public void removeItemWithNotify(int position) {
        removeItem(position);
        notifyItemRemoved(position);
    }

    public void removeItems(List<Integer> positions) {

        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });

        while (!positions.isEmpty()) {
            removeItemWithNotify(positions.get(0));
            positions.remove(0);
        }

    }

}
