package com.agrotrading.kancher.moneytracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;


public class ExpensesAdapter extends ArrayAdapter {

    List<Expense> expenses;

    public ExpensesAdapter(Context context, List<Expense> expenses) {
        super(context, 0, expenses);
        this.expenses = expenses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Expense expense = (Expense) getItem(position);
        Random rnd = new Random();
        int bgColor;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        bgColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        RelativeLayout field = (RelativeLayout) convertView.findViewById(R.id.field_item);
        field.setBackgroundColor(bgColor);

        TextView name = (TextView) convertView.findViewById(R.id.name_text);
        TextView sum = (TextView) convertView.findViewById(R.id.sum_text);
        TextView date = (TextView) convertView.findViewById(R.id.date_text);

        name.setText(expense.getTitle());
        sum.setText(expense.getSumStr());
        date.setText(expense.getDateStr());

        return convertView;
    }
}
