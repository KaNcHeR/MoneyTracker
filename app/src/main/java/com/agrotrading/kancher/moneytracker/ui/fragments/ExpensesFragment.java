package com.agrotrading.kancher.moneytracker.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agrotrading.kancher.moneytracker.ui.activities.AddExpenseActivity_;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.adapters.ExpensesAdapter;
import com.agrotrading.kancher.moneytracker.models.Expense;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@EFragment
public class ExpensesFragment extends Fragment {

    @ViewById(R.id.context_recyclerview)
    RecyclerView expensesRecyclerView;

    @ViewById(R.id.fab)
    FloatingActionButton floatingActionButton;

    @Click(R.id.fab)
    void startAddExpenseActivity(){
        AddExpenseActivity_.intent(this).start();
    }

    @Bean
    ExpensesAdapter expensesAdapter;

    @AfterViews
    void ready() {
        expensesAdapter.setItems(getDataList());
        expensesRecyclerView.setAdapter(expensesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        expensesRecyclerView.setLayoutManager(linearLayoutManager);
        getActivity().setTitle(getString(R.string.nav_drawer_expenses));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = inflater.inflate(R.layout.expenses_fragment, container, false);
        Snackbar.make(mainView, getString(R.string.nav_drawer_expenses), Snackbar.LENGTH_SHORT).show();
        return mainView;
    }

    private List<Expense> getDataList() {
        List<Expense> data = new ArrayList<>();
        long millis = Calendar.getInstance().getTimeInMillis();

        Expense expenseTelephone = new Expense();
        expenseTelephone.setTitle("Telephone");
        expenseTelephone.setSum(1000);
        expenseTelephone.setDate(new Date(millis));

        Expense expenseFlat = new Expense();
        expenseFlat.setTitle("Clothes");
        expenseFlat.setSum(2000);
        expenseFlat.setDate(new Date(millis));

        Expense expensePc = new Expense();
        expensePc.setTitle("Pc");
        expensePc.setSum(3000);
        expensePc.setDate(new Date(millis));

        data.add(expenseTelephone);
        data.add(expenseFlat);
        data.add(expensePc);
        data.add(expenseTelephone);
        data.add(expenseFlat);
        data.add(expensePc);
        data.add(expenseTelephone);
        data.add(expenseFlat);
        data.add(expensePc);
        data.add(expenseTelephone);
        data.add(expenseFlat);
        data.add(expensePc);
        data.add(expenseTelephone);
        data.add(expenseFlat);
        data.add(expensePc);
        data.add(expenseTelephone);
        data.add(expenseFlat);
        data.add(expensePc);
        data.add(expenseTelephone);
        data.add(expenseFlat);
        data.add(expensePc);

        return data;
    }
}
