package com.agrotrading.kancher.moneytracker.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.agrotrading.kancher.moneytracker.views.CategoryItemView;
import com.agrotrading.kancher.moneytracker.views.CategoryItemView_;
import com.agrotrading.kancher.moneytracker.ViewWrapper;
import com.agrotrading.kancher.moneytracker.models.Category;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class CategoriesAdapter extends RecyclerViewAdapterBase<Category, CategoryItemView> {

    @RootContext
    Context context;

    @Override
    public void onBindViewHolder(ViewWrapper<CategoryItemView> holder, int position) {
        CategoryItemView view = holder.getView();
        Category category = items.get(position);
        view.bind(category);
    }

    @Override
    protected CategoryItemView onCreateItemView(ViewGroup parent, int viewType) {
        return CategoryItemView_.build(context);
    }

    @Override
    public void setItems(List<Category> items) {
        super.setItems(items);
    }
}
