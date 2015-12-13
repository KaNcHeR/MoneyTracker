package com.agrotrading.kancher.moneytracker.interfaces;

import com.agrotrading.kancher.moneytracker.models.Expense;

import java.util.List;

public interface ExpenseFinder {
    List<Expense> findAll();
}
