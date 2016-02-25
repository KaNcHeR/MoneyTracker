package com.agrotrading.kancher.moneytracker.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenStatusModel;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenUserDataModel;
import com.agrotrading.kancher.moneytracker.ui.activities.MainActivity_;
import com.agrotrading.kancher.moneytracker.utils.event.MessageEvent;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import retrofit.RetrofitError;

@EBean
public class GoogleAuthHelper {

    private static final String LOG_TAG = GoogleAuthHelper.class.getSimpleName();
    RestService restService = new RestService();

    @RootContext
    Context context;

    @Bean
    DBRestBridge dbRestBridge;

    @Pref
    static ApplicationPreferences_ prefs;

    @Background
    public void checkTokenValid() {

        boolean startMainActivity = true;
        String accountEmail = prefs.googleAccountEmail().get();

        try {
            String gToken = MoneyTrackerApplication.getGoogleToken(context);
            GoogleTokenStatusModel statusModel = restService.getGoogleTokenStatus(gToken);
            if (statusModel.getStatus().equalsIgnoreCase(ConstantManager.STATUS_ERROR)) {
                startMainActivity = false;
            }
        } catch (RetrofitError error) {
            RetrofitEventBusBridge.showEvent(error);
            return;
        }

        if(!startMainActivity) {
            Intent intentDummy = new Intent();
            intentDummy.putExtra(AccountManager.KEY_ACCOUNT_NAME, accountEmail);
            intentDummy.putExtra(AccountManager.KEY_ACCOUNT_TYPE, ConstantManager.GOOGLE_ACCOUNT_TYPE);
            getToken(intentDummy);
        } else {
            startMainActivityWithGToken();
        }
    }

    private boolean editPrefs() {
        String gToken = MoneyTrackerApplication.getGoogleToken(context);
        GoogleTokenUserDataModel accountData;

        try {
            accountData = restService.getGoogleUserData(gToken);
        } catch (RetrofitError error) {
            RetrofitEventBusBridge.showEvent(error);
            return false;
        }

        prefs.edit()
                .googleAccountName().put(accountData.getName())
                .googleAccountEmail().put(accountData.getEmail())
                .googleAccountPictureSrc().put(accountData.getPicture())
                .apply();

        return true;
    }

    @Background
    public void startMainActivityWithoutGToken() {
        startMainActivity();
    }

    private void startMainActivityWithGToken() {
        if(editPrefs()) {
            startMainActivity();
        }
    }

    void startMainActivity() {

        if(prefs.needFirstSync().get()) {
            dbRestBridge.firstSync();
            prefs.needFirstSync().put(false);
        }
        startTransition();
    }

    @UiThread
    void startTransition() {
        //noinspection unchecked
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context);
        MainActivity_.intent(context).withOptions(transitionActivityOptions.toBundle()).start();
    }

    private void doubleTokenEcx() {
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                new String[]{ConstantManager.GOOGLE_ACCOUNT_TYPE}, false, null, null, null, null);
        ((Activity) context).startActivityForResult(intent, ConstantManager.GET_GOOGLE_TOKEN_REQUEST_CODE);
    }

    @Background
    public void getToken(Intent data) {
        final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        final String accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
        Account account = new Account(accountName, accountType);

        try {
            String gToken = GoogleAuthUtil.getToken(context, account, ConstantManager.SCOPES);
            MoneyTrackerApplication.setGoogleToken(context, gToken);
            Log.e(LOG_TAG, "GOOGLE_TOKEN " + MoneyTrackerApplication.getGoogleToken(context));
            startMainActivityWithGToken();

        } catch (UserRecoverableAuthException userAuthEx) {
            ((Activity) context).startActivityForResult(userAuthEx.getIntent(), ConstantManager.GET_GOOGLE_TOKEN_REQUEST_CODE);
        } catch (IOException ioEx) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.ALERT_NO_INTERNET));
            ioEx.printStackTrace();
        } catch (GoogleAuthException fatalAuthEx) {
            fatalAuthEx.printStackTrace();
            Log.e(LOG_TAG, "Fatal Exception: " + fatalAuthEx.getLocalizedMessage());
        }
    }
}