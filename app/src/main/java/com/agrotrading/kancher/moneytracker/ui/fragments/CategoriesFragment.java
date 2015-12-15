package com.agrotrading.kancher.moneytracker.ui.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.adapters.CategoriesAdapter;
import com.agrotrading.kancher.moneytracker.models.Category;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.categories_fragment)
public class CategoriesFragment extends Fragment {

    @ViewById(R.id.context_recyclerview)
    RecyclerView categoriesRecyclerView;

    @Bean
    CategoriesAdapter categoriesAdapter;

    @AfterViews
    void ready() {
        categoriesAdapter.setItems(getDataList());
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);
        getActivity().setTitle(getString(R.string.nav_drawer_categories));
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
