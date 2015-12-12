package com.agrotrading.kancher.moneytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpensesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View mainView = inflater.inflate(R.layout.expenses_fragment, container, false);
        RecyclerView expensesRecyclerView = (RecyclerView) mainView.findViewById(R.id.context_recyclerview);
        List<Expense> adapterData = getDataList();
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(adapterData);
        expensesRecyclerView.setAdapter(expensesAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        expensesRecyclerView.setLayoutManager(linearLayoutManager);

        FloatingActionButton floatingActionButton = (FloatingActionButton) mainView.findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddExpenseActivity_.class);
                getActivity().startActivity(intent);
            }
        });

        getActivity().setTitle(getString(R.string.nav_drawer_expenses));

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
