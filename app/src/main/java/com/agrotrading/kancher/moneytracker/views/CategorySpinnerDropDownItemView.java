package com.agrotrading.kancher.moneytracker.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.database.Categories;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.spinner_categories_drop_down)
public class CategorySpinnerDropDownItemView extends LinearLayout {

    @ViewById(R.id.name_text)
    TextView name;

    public CategorySpinnerDropDownItemView(Context context) {
        super(context);
    }

    public void bind(Categories category) {
        name.setText(category.toString());
    }

}
