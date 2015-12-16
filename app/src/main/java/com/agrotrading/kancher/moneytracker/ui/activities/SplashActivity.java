package com.agrotrading.kancher.moneytracker.ui.activities;

import android.support.v7.app.AppCompatActivity;

import com.agrotrading.kancher.moneytracker.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    @AfterViews
    void ready() {
        doInBackground();
    }

    @Background(delay=3000)
    void doInBackground() {
        MainActivity_.intent(this).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
