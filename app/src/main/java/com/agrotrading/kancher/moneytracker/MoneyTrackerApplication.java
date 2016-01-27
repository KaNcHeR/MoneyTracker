package com.agrotrading.kancher.moneytracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;

public class MoneyTrackerApplication extends Application {

    private static SharedPreferences sharedPreferences;
    private static final String TOKEN_KEY = "token_key";
    private static final String GOOGLE_TOKEN_KEY = "google_token_key";

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
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public static void setGoogleToken(Context context, String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(GOOGLE_TOKEN_KEY, token);
        editor.apply();
    }

    public static String getGoogleToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(GOOGLE_TOKEN_KEY, ConstantManager.DEFAULT_GOOGLE_TOKEN);
    }
}
