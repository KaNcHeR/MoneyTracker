package com.agrotrading.kancher.moneytracker.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.views.CategoryItemView;
import com.agrotrading.kancher.moneytracker.views.CategoryItemView_;
import com.agrotrading.kancher.moneytracker.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class CategoriesAdapter extends RecyclerViewAdapterBase<Categories, CategoryItemView> {

    @RootContext
    Context context;

    @Override
    public void onBindViewHolder(ViewWrapper<CategoryItemView> holder, int position) {
        CategoryItemView view = holder.getView();
        Categories categories = items.get(position);
        view.bind(categories);
    }

    @Override
    protected CategoryItemView onCreateItemView(ViewGroup parent, int viewType) {
        return CategoryItemView_.build(parent.getContext());
    }

}
