package com.agrotrading.kancher.moneytracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.RLayoutHolder> {

    List<Category> categories;

    public CategoriesAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public RLayoutHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list_item, parent, false);
        return new RLayoutHolder(convertView);
    }

    @Override
    public void onBindViewHolder(RLayoutHolder holder, int position) {
        Category category = categories.get(position);
        holder.nameText.setText(category.getTitle());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class RLayoutHolder extends RecyclerView.ViewHolder {

        protected TextView nameText;

        public RLayoutHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.name_text);
        }
    }
}
