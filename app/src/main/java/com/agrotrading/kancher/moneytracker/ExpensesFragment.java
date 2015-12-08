package com.agrotrading.kancher.moneytracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
                Snackbar.make(mainView, "Nice", Snackbar.LENGTH_SHORT).show();
            }
        });

        getActivity().setTitle(getString(R.string.nav_drawer_expenses));
        return mainView;
    }

    private List<Expense> getDataList() {
        List<Expense> data = new ArrayList<>();
        long millis = Calendar.getInstance().getTimeInMillis();

        Expense expense_telephone = new Expense();
        expense_telephone.setTitle("Telephone");
        expense_telephone.setSum(1000);
        expense_telephone.setDate(new Date(millis));

        Expense expense_flat = new Expense();
        expense_flat.setTitle("Clothes");
        expense_flat.setSum(2000);
        expense_flat.setDate(new Date(millis));

        Expense expense_pc = new Expense();
        expense_pc.setTitle("Pc");
        expense_pc.setSum(3000);
        expense_pc.setDate(new Date(millis));

        data.add(expense_telephone);
        data.add(expense_flat);
        data.add(expense_pc);
        data.add(expense_telephone);
        data.add(expense_flat);
        data.add(expense_pc);
        data.add(expense_telephone);
        data.add(expense_flat);
        data.add(expense_pc);
        data.add(expense_telephone);
        data.add(expense_flat);
        data.add(expense_pc);
        data.add(expense_telephone);
        data.add(expense_flat);
        data.add(expense_pc);
        data.add(expense_telephone);
        data.add(expense_flat);
        data.add(expense_pc);
        data.add(expense_telephone);
        data.add(expense_flat);
        data.add(expense_pc);

        return data;
    }
}
