package com.agrotrading.kancher.moneytracker.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.InMemoryExpenseFinder;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.interfaces.ExpenseFinder;
import com.agrotrading.kancher.moneytracker.models.Expense;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.CardViewHolder> {

    List<Expense> expenses;

    @Bean(InMemoryExpenseFinder.class)
    ExpenseFinder expenseFinder;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        expenses = expenseFinder.findAll();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.nameText.setText(expense.getTitle());
        holder.sumText.setText(expense.getSumStr());
        holder.dateText.setText(expense.getDateStr());
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        protected TextView nameText;
        protected TextView sumText;
        protected TextView dateText;

        public CardViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.name_text);
            sumText = (TextView) itemView.findViewById(R.id.sum_text);
            dateText = (TextView) itemView.findViewById(R.id.date_text);
        }
    }
}
