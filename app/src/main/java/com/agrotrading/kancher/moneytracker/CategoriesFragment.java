package com.agrotrading.kancher.moneytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.categories_fragment, container, false);
        RecyclerView categoriesRecyclerView = (RecyclerView) mainView.findViewById(R.id.context_recyclerview);
        List<Category> adapterList = getDataList();
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(adapterList);
        categoriesRecyclerView.setAdapter(categoriesAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);

        getActivity().setTitle(getString(R.string.nav_drawer_categories));
        return mainView;
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
