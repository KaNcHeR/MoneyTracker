package com.agrotrading.kancher.moneytracker.views;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.models.Expense;

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

    public void bind(Expense expense) {
        nameText.setText(expense.getTitle());
        sumText.setText(expense.getSumStr());
        dateText.setText(expense.getDateStr());
    }
}
