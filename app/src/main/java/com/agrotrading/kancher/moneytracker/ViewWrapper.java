package com.agrotrading.kancher.moneytracker;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ViewWrapper<V extends View> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private V view;
    private ClickListener clickListener;

    public ViewWrapper(V itemView, ClickListener clickListener) {
        super(itemView);
        view = itemView;
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public V getView() {
        return view;
    }

    public interface ClickListener {
        void onItemClicked(int position);
        boolean onItemLongClicked(int position);
    }

    @Override
    public void onClick(View v) {
        if(clickListener != null) clickListener.onItemClicked(getAdapterPosition());
    }

    @Override
    public boolean onLongClick(View v) {
        return clickListener != null && clickListener.onItemLongClicked(getAdapterPosition());
    }
}
