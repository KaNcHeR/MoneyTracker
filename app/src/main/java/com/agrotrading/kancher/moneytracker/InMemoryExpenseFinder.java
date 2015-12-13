package com.agrotrading.kancher.moneytracker;

import com.agrotrading.kancher.moneytracker.interfaces.ExpenseFinder;
import com.agrotrading.kancher.moneytracker.models.Expense;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@EBean
public class InMemoryExpenseFinder implements ExpenseFinder {

    @Override
    public List<Expense> findAll() {
        return getDataList();
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
