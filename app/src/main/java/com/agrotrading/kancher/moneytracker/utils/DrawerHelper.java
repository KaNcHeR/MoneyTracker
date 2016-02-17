package com.agrotrading.kancher.moneytracker.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.R;
import com.bumptech.glide.Glide;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayDeque;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

@EBean
public class DrawerHelper {

    ImageView pictureImageView;
    ArrayDeque<Integer> drawerItemIdStack;

    @RootContext
    Context context;

    @Pref
    ApplicationPreferences_ prefs;

    public void fillDrawerHeader() {

        if(!prefs.googleAccountEmail().get().equalsIgnoreCase("")) {
            NavigationView navigationView = (NavigationView) ((Activity) context).findViewById(R.id.navigation_view);
            View headerView = navigationView.getHeaderView(ConstantManager.INDEX_DRAWER_HEADER_ACCOUNT_DATA);
            LinearLayout subtitleLayout = (LinearLayout) headerView.findViewById(R.id.subtitle);
            TextView nameTextView = (TextView) headerView.findViewById(R.id.name);
            TextView emailTextView = (TextView) headerView.findViewById(R.id.email);

            nameTextView.setText(prefs.googleAccountName().get());
            emailTextView.setText(prefs.googleAccountEmail().get());
            ImageView pictureImageView = (ImageView) headerView.findViewById(R.id.avatar);

            subtitleLayout.setVisibility(View.VISIBLE);
            pictureImageView.setVisibility(View.VISIBLE);

            Glide.with(context).load(prefs.googleAccountPictureSrc().get())
                    .bitmapTransform(new CropCircleTransformation(context))
                    .into(pictureImageView);
        }
    }

    public void initDrawerItemIdStack(int startId) {
        drawerItemIdStack = new ArrayDeque<>();
        drawerItemIdStack.add(startId);
    }

    public void addToDrawerItemIdStack(int id) {
        drawerItemIdStack.add(id);
    }

    public int getCheckedId() {
        drawerItemIdStack.removeLast();
        return drawerItemIdStack.getLast();
    }
}