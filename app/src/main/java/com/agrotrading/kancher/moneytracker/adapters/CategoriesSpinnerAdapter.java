package com.agrotrading.kancher.moneytracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.database.Categories;

import java.util.List;

public class CategoriesSpinnerAdapter extends ArrayAdapter implements SpinnerAdapter {

    List<Categories> categories;

    public CategoriesSpinnerAdapter(Context context, List<Categories> categories) {
        super(context, 0, categories);
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Categories category = (Categories) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_categories, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_text);
        name.setText(category.name);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        Categories category = (Categories) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_categories, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_text);
        name.setText(category.name);

        return convertView;
    }
}
