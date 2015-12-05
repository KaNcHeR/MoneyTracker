package com.agrotrading.kancher.moneytracker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class OtherFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.other_fragment, container, false);
        ListView expensesListView = (ListView) mainView.findViewById(R.id.list_view);
        List<Expense> adapterList = getDataList();
        ExpensesAdapter expensesAdapter = new ExpensesAdapter(getActivity(), adapterList);
        expensesListView.setAdapter(expensesAdapter);
        getActivity().setTitle(getString(R.string.no_expenses));
        return mainView;
    }

    private List<Expense> getDataList() {
        List<Expense> data = new ArrayList<>();
        data.add(new Expense("Telephone", "1000"));
        data.add(new Expense("Clothes", "5000"));
        return data;
    }
}

