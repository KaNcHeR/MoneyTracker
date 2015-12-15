package com.agrotrading.kancher.moneytracker.ui.fragments;

import android.support.v4.app.Fragment;

import com.agrotrading.kancher.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.statistics_fragment)
public class StatisticsFragment extends Fragment {

    @AfterViews
    void ready() {
        getActivity().setTitle(getString(R.string.nav_drawer_statistics));
    }

}
