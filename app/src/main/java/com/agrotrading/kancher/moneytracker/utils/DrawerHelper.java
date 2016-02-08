package com.agrotrading.kancher.moneytracker.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.ui.activities.MainActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EBean
public class DrawerHelper {

    Context context;
    MainActivity mainActivity;
    ImageView pictureImageView;

    @Pref
    static ApplicationPreferences_ prefs;

    public DrawerHelper(Context context) {
        this.context = context;
        try {
            mainActivity = (MainActivity) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }

    public void fillDrawerHeader() {

        if(!prefs.googleAccountEmail().get().equalsIgnoreCase("")) {

            View headerView = mainActivity.getNavigationView().getHeaderView(ConstantManager.INDEX_DRAWER_HEADER_ACCOUNT_DATA);
            TextView nameTextView = (TextView) headerView.findViewById(R.id.name);
            TextView emailTextView = (TextView) headerView.findViewById(R.id.email);
            nameTextView.setText(prefs.googleAccountName().get());
            emailTextView.setText(prefs.googleAccountEmail().get());

            ImageView pictureImageView = (ImageView) headerView.findViewById(R.id.avatar);
            ImageLoader.getInstance().displayImage(prefs.googleAccountPictureSrc().get(), pictureImageView);
        }
    }

}