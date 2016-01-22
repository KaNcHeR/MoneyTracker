package com.agrotrading.kancher.moneytracker.utils;

import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.database.Expenses;
import com.agrotrading.kancher.moneytracker.rest.model.category.CategoryData;
import com.agrotrading.kancher.moneytracker.rest.model.expense.ExpenseData;

import java.util.ArrayList;
import java.util.List;

public class DbToRestBridge {

    public static ArrayList<CategoryData> getCategoriesData() {
        ArrayList<CategoryData> data = new ArrayList<>();
        List<Categories> categories = Categories.getAllCategories();
        CategoryData categoryData = new CategoryData();

        for(Categories category : categories) {
            categoryData.setId(category.getId());
            categoryData.setTitle(category.toString());
            data.add(categoryData);
        }

        return data;
    }

    public static ArrayList<ExpenseData> getTransactionsData() {
        ArrayList<ExpenseData> data = new ArrayList<>();
        List<Expenses> expenses = Expenses.getAllExpenses();
        ExpenseData transactionData = new ExpenseData();

        for(Expenses expense : expenses) {
            transactionData.setId(expense.getId());
            transactionData.setCategoryId(expense.getCategoryId());
            transactionData.setComment(expense.getName());
            transactionData.setSum(expense.getPrice());
            transactionData.setTrDate(expense.getDate());
        }

        return data;
    }
}
