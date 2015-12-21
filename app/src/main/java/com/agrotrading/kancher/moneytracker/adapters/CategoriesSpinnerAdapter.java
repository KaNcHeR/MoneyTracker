package com.agrotrading.kancher.moneytracker.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.views.CategorySpinnerDropDownItemView;
import com.agrotrading.kancher.moneytracker.views.CategorySpinnerDropDownItemView_;
import com.agrotrading.kancher.moneytracker.views.CategorySpinnerItemView;
import com.agrotrading.kancher.moneytracker.views.CategorySpinnerItemView_;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class CategoriesSpinnerAdapter extends BaseAdapter {

    List<Categories> categories;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        categories = Categories.getAllCategories();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CategorySpinnerItemView categorySpinnerItemView;

        if (convertView == null) {
            categorySpinnerItemView = CategorySpinnerItemView_.build(context);
        } else {
            categorySpinnerItemView = (CategorySpinnerItemView) convertView;
        }

        categorySpinnerItemView.bind(getItem(position));

        return categorySpinnerItemView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        CategorySpinnerDropDownItemView categorySpinnerDropDownItemView;

        if (convertView == null) {
            categorySpinnerDropDownItemView = CategorySpinnerDropDownItemView_.build(context);
        } else {
            categorySpinnerDropDownItemView = (CategorySpinnerDropDownItemView) convertView;
        }

        categorySpinnerDropDownItemView.bind(getItem(position));

        return categorySpinnerDropDownItemView;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Categories getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
