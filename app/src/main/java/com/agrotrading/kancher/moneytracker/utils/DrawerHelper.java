package com.agrotrading.kancher.moneytracker.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.ui.activities.MainActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.net.URL;

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
            pictureImageView = (ImageView) headerView.findViewById(R.id.avatar);

            getGooglePlusAccountPicture();
        }
    }

    @Background
    void getGooglePlusAccountPicture() {

        try {
            URL pictureURL = new URL(prefs.googleAccountPictureSrc().get());
            Bitmap bitmap = BitmapFactory.decodeStream(pictureURL.openConnection().getInputStream());
            setGooglePlusAccountPicture(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @UiThread
    void setGooglePlusAccountPicture(Bitmap bitmap) {
        pictureImageView.setImageBitmap(bitmap);
    }
}
