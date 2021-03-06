package com.agrotrading.kancher.moneytracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;

public class MoneyTrackerApplication extends Application {

    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public static void setAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ConstantManager.TOKEN_KEY, token);
        editor.apply();
    }

    public static String getAuthToken() {
        return sharedPreferences.getString(ConstantManager.TOKEN_KEY, null);
    }

    public static void setGoogleToken(Context context, String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ConstantManager.GOOGLE_TOKEN_KEY, token);
        editor.apply();
    }

    public static String getGoogleToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(ConstantManager.GOOGLE_TOKEN_KEY, ConstantManager.DEFAULT_GOOGLE_TOKEN);
    }
}