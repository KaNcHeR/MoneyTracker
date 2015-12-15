package com.agrotrading.kancher.moneytracker.ui.fragments;

import android.support.v4.app.Fragment;

import com.agrotrading.kancher.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.settings_fragment)
public class SettingsFragment extends Fragment {

    @AfterViews
    void ready() {
        getActivity().setTitle(getString(R.string.nav_drawer_settings));
    }

}
