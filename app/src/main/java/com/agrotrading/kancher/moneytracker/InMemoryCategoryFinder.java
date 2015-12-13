package com.agrotrading.kancher.moneytracker;

import com.agrotrading.kancher.moneytracker.interfaces.CategoryFinder;
import com.agrotrading.kancher.moneytracker.models.Category;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

@EBean
public class InMemoryCategoryFinder implements CategoryFinder {

    @Override
    public List<Category> findAll() {
        return getDataList();
    }

    private List<Category> getDataList() {
        List<Category> data = new ArrayList<>();

        Category categoryElectronics = new Category();
        categoryElectronics.setTitle("Electronics");

        Category categoryFoodstuffs = new Category();
        categoryFoodstuffs.setTitle("Foodstuffs");

        Category categoryChemicals = new Category();
        categoryChemicals.setTitle("Household chemicals");

        Category categoryClothes = new Category();
        categoryClothes.setTitle("Clothes");

        data.add(categoryElectronics);
        data.add(categoryFoodstuffs);
        data.add(categoryChemicals);
        data.add(categoryClothes);
        data.add(categoryElectronics);
        data.add(categoryFoodstuffs);
        data.add(categoryChemicals);
        data.add(categoryClothes);
        data.add(categoryElectronics);
        data.add(categoryFoodstuffs);
        data.add(categoryChemicals);
        data.add(categoryClothes);
        data.add(categoryElectronics);
        data.add(categoryFoodstuffs);
        data.add(categoryChemicals);
        data.add(categoryClothes);
        return data;
    }
}
