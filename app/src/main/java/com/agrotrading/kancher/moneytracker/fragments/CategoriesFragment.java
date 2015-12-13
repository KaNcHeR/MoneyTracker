package com.agrotrading.kancher.moneytracker.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.adapters.CategoriesAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.categories_fragment)
public class CategoriesFragment extends Fragment {

    @ViewById(R.id.context_recyclerview)
    RecyclerView categoriesRecyclerView;

    @Bean
    CategoriesAdapter categoriesAdapter;

    @AfterViews
    void ready() {
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoriesRecyclerView.setLayoutManager(linearLayoutManager);
        getActivity().setTitle(getString(R.string.nav_drawer_categories));
    }

}
