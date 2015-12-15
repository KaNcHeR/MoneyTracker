package com.agrotrading.kancher.moneytracker.views;

import android.content.Context;
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

    public ExpenseItemView(Context context) {
        super(context);
    }

    public void bind(Expenses expenses) {
        nameText.setText(expenses.name);
        sumText.setText(expenses.price);
        dateText.setText(expenses.date);
    }
}
