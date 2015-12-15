package com.agrotrading.kancher.moneytracker.views;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.models.Category;
import com.agrotrading.kancher.moneytracker.models.Expense;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.categories_list_item)
public class CategoryItemView extends RelativeLayout {

    @ViewById(R.id.name_text)
    TextView nameText;

    public CategoryItemView(Context context) {
        super(context);
    }

    public void bind(Category expense) {
        nameText.setText(expense.getTitle());
    }
}
