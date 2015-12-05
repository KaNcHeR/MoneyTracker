package com.agrotrading.kancher.moneytracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ExpensesAdapter extends ArrayAdapter {

    List<Expense> expenses;

    public ExpensesAdapter(Context context, List<Expense> expenses) {
        super(context, 0, expenses);
        this.expenses = expenses;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Expense expense = (Expense) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_text);
        TextView sum = (TextView) convertView.findViewById(R.id.sum_text);

        name.setText(expense.getTitle());
        sum.setText(expense.getSum());

        return convertView;
    }
}
