package com.agrotrading.kancher.moneytracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.InMemoryCategoryFinder;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.interfaces.CategoryFinder;
import com.agrotrading.kancher.moneytracker.models.Category;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.RLayoutHolder> {

    List<Category> categories;

    @Bean(InMemoryCategoryFinder.class)
    CategoryFinder categoryFinder;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        categories = categoryFinder.findAll();
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
