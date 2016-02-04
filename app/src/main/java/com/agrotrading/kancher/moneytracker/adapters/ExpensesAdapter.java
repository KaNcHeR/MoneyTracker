package com.agrotrading.kancher.moneytracker.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.database.Expenses;
import com.agrotrading.kancher.moneytracker.views.ExpenseItemView;
import com.agrotrading.kancher.moneytracker.views.ExpenseItemView_;
import com.agrotrading.kancher.moneytracker.ViewWrapper;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class ExpensesAdapter extends RecyclerViewAdapterBase<Expenses, ExpenseItemView> {

    @RootContext
    Context context;

    private int lastPosition = -1;

    @Override
    public void onBindViewHolder(ViewWrapper<ExpenseItemView> holder, int position) {
        ExpenseItemView view = holder.getView();
        Expenses expense = items.get(position);
        view.bind(expense, isSelected(position));
        setAnimation(view.getCardView(), position);
    }

    @Override
    protected ExpenseItemView onCreateItemView(ViewGroup parent, int viewType) {
        return ExpenseItemView_.build(parent.getContext());
    }

    private void setAnimation(View viewToAnimate, int position) {
        if(position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
