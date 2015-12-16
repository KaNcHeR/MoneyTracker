package com.agrotrading.kancher.moneytracker.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.agrotrading.kancher.moneytracker.views.ExpenseItemView;
import com.agrotrading.kancher.moneytracker.views.ExpenseItemView_;
import com.agrotrading.kancher.moneytracker.ViewWrapper;
import com.agrotrading.kancher.moneytracker.models.Expense;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class ExpensesAdapter extends RecyclerViewAdapterBase<Expense, ExpenseItemView> {

    @RootContext
    Context context;

    @Override
    public void onBindViewHolder(ViewWrapper<ExpenseItemView> holder, int position) {
        ExpenseItemView view = holder.getView();
        Expense expense = items.get(position);
        view.bind(expense);
    }

    @Override
    protected ExpenseItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ExpenseItemView_.build(context);
    }

    @Override
    public void setItems(List<Expense> items) {
        super.setItems(items);
    }
}
