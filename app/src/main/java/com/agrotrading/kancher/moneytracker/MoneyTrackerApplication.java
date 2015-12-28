package com.agrotrading.kancher.moneytracker;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

public class MoneyTrackerApplication extends Application {

    private static SharedPreferences sharedPreferences;
    private static final String TOKEN_KEY = "token_key";
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public static void setAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public static String getAuthToken() {
        return sharedPreferences.getString(TOKEN_KEY, "");
    }
}
