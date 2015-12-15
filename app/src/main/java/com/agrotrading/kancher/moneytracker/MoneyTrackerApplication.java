package com.agrotrading.kancher.moneytracker;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class MoneyTrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

}
