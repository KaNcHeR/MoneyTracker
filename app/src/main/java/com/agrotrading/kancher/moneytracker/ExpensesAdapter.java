package com.agrotrading.kancher.moneytracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.CardViewHolder> {

    List<Expense> expenses;

    public ExpensesAdapter(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.name_text.setText(expense.getTitle());
        holder.sum_text.setText(expense.getSumStr());
        holder.date_text.setText(expense.getDateStr());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        protected TextView name_text;
        protected TextView sum_text;
        protected TextView date_text;

        public CardViewHolder(View itemView) {
            super(itemView);
            name_text = (TextView) itemView.findViewById(R.id.name_text);
            sum_text = (TextView) itemView.findViewById(R.id.sum_text);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
        }
    }
}
