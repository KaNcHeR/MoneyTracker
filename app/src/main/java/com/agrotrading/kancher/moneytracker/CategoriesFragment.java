package com.agrotrading.kancher.moneytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.categories_fragment, container, false);
        ListView categoriesListView = (ListView) mainView.findViewById(R.id.list_view);
        List<Category> adapterList = getDataList();
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(getActivity(), adapterList);
        categoriesListView.setAdapter(categoriesAdapter);
        getActivity().setTitle(getString(R.string.nav_drawer_categories));
        return mainView;
    }

    private List<Category> getDataList() {
        List<Category> data = new ArrayList<>();

        Category category_electronics = new Category();
        category_electronics.setTitle("Electronics");

        Category category_foodstuffs = new Category();
        category_foodstuffs.setTitle("Foodstuffs");

        Category category_chemicals = new Category();
        category_chemicals.setTitle("Household chemicals");

        Category category_clothes = new Category();
        category_clothes.setTitle("Clothes");

        data.add(category_electronics);
        data.add(category_foodstuffs);
        data.add(category_chemicals);
        data.add(category_clothes);
        return data;
    }
}
