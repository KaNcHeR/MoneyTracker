package com.agrotrading.kancher.moneytracker.utils;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.APPLICATION_DEFAULT)
public interface ApplicationPreferences {

    @DefaultBoolean(false)
    boolean needSyncCategories();

    @DefaultBoolean(false)
    boolean needSyncExpenses();

    String googleAccountName();

    String googleAccountEmail();

    String googleAccountPictureSrc();

}
