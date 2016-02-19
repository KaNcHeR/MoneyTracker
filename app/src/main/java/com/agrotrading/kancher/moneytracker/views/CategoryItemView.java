package com.agrotrading.kancher.moneytracker.views;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.database.Categories;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.categories_list_item)
public class CategoryItemView extends RelativeLayout {

    @ViewById(R.id.name_text)
    TextView nameText;

    @ViewById(R.id.selected_overlay)
    View selectedOverlay;

    public CategoryItemView(Context context) {
        super(context);
    }

    public void bind(Categories categories, boolean selected) {
        nameText.setText(categories.toString());
        selectedOverlay.setVisibility(selected ? VISIBLE : INVISIBLE);
    }

    public TextView getNameText() {
        return nameText;
    }
}
