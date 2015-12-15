package com.agrotrading.kancher.moneytracker.adapters;

import android.content.Context;
import android.view.ViewGroup;

import com.agrotrading.kancher.moneytracker.database.Expenses;
import com.agrotrading.kancher.moneytracker.views.ExpenseItemView;
import com.agrotrading.kancher.moneytracker.views.ExpenseItemView_;
import com.agrotrading.kancher.moneytracker.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class ExpensesAdapter extends RecyclerViewAdapterBase<Expenses, ExpenseItemView> {

    @RootContext
    Context context;

    @Override
    public void onBindViewHolder(ViewWrapper<ExpenseItemView> holder, int position) {
        ExpenseItemView view = holder.getView();
        Expenses expenses = items.get(position);
        view.bind(expenses);
    }

    @Override
    protected ExpenseItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ExpenseItemView_.build(parent.getContext());
    }

    @Override
    public ExpensesAdapter setItems(List<Expenses> items) {
        super.setItems(items);
        return this;
    }
}
