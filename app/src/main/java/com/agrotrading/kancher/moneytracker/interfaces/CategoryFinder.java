package com.agrotrading.kancher.moneytracker.interfaces;

import com.agrotrading.kancher.moneytracker.models.Category;

import java.util.List;

public interface CategoryFinder {
    List<Category> findAll();
}
