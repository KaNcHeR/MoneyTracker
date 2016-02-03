package com.agrotrading.kancher.moneytracker.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.database.Expenses;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.list_item)
public class ExpenseItemView extends RelativeLayout {

    @ViewById(R.id.name_text)
    TextView nameText;

    @ViewById(R.id.sum_text)
    TextView sumText;

    @ViewById(R.id.date_text)
    TextView dateText;

    @ViewById(R.id.selected_overlay)
    View selectedOverlay;

    public ExpenseItemView(Context context) {
        super(context);
    }

    public void bind(Expenses expense, boolean selected) {
        nameText.setText(expense.getName());
        sumText.setText(String.valueOf(expense.getPrice()));
        dateText.setText(expense.getDate());
        selectedOverlay.setVisibility(selected ? VISIBLE : INVISIBLE);
    }
}
