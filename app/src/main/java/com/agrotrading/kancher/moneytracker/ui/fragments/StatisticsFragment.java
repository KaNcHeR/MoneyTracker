package com.agrotrading.kancher.moneytracker.ui.fragments;

import android.support.v4.app.Fragment;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.ui.PieChartView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.statistics_fragment)
public class StatisticsFragment extends Fragment {

    private float[] dataPoints = {450, 1230, 200, 400, 500};

    @ViewById(R.id.pie_chart)
    PieChartView pieChartView;

    @AfterViews
    void ready() {
        getActivity().setTitle(getString(R.string.nav_drawer_statistics));
        pieChartView.setDataPoints(dataPoints);
    }

}
