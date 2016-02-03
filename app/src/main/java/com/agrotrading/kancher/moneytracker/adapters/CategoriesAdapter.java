package com.agrotrading.kancher.moneytracker.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.views.CategoryItemView;
import com.agrotrading.kancher.moneytracker.views.CategoryItemView_;
import com.agrotrading.kancher.moneytracker.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Collections;
import java.util.Comparator;

@EBean
public class CategoriesAdapter extends RecyclerViewAdapterBase<Categories, CategoryItemView> {

    @RootContext
    Context context;

    @Override
    public void onBindViewHolder(ViewWrapper<CategoryItemView> holder, int position) {
        CategoryItemView view = holder.getView();
        Categories categories = items.get(position);
        view.bind(categories, isSelected(position));
    }

    @Override
    protected CategoryItemView onCreateItemView(ViewGroup parent, int viewType) {
        return CategoryItemView_.build(parent.getContext());
    }

    public void insertItemNameAsc(Categories category) {

        items.add(0, category);

        Collections.sort(items, new Comparator<Categories>() {
            @Override
            public int compare(Categories lhs, Categories rhs) {
                return lhs.toString().compareTo(rhs.toString());
            }
        });

        notifyItemInserted(items.indexOf(category));
    }
}
