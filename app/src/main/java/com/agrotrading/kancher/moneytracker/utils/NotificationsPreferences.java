package com.agrotrading.kancher.moneytracker.utils;

import com.agrotrading.kancher.moneytracker.R;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface NotificationsPreferences {

    @DefaultBoolean(value = true, keyRes = R.string.pref_enable_notifications_key)
    boolean displayNotifications();

    @DefaultBoolean(value = true, keyRes = R.string.pref_enable_sound_key)
    boolean soundNotifications();

    @DefaultBoolean(value = true, keyRes = R.string.pref_enable_vibrate_key)
    boolean vibrateNotifications();

    @DefaultBoolean(value = true, keyRes = R.string.pref_enable_indicator_key)
    boolean indicatorNotifications();

}
