package com.agrotrading.kancher.moneytracker.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenStatusModel;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenUserDataModel;
import com.agrotrading.kancher.moneytracker.ui.activities.MainActivity_;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EBean
public class GoogleAuthHelper {

    private static final String LOG_TAG = GoogleAuthHelper.class.getSimpleName();

    Context context;
    Activity activity;
    RestService restService;
    private String gToken;

    @Pref
    static ApplicationPreferences_ prefs;

    public GoogleAuthHelper(Context context) {
        this.context = context;
        activity = (Activity) context;
        gToken = MoneyTrackerApplication.getGoogleToken(context);
        restService = new RestService();
    }

    @Background
    public void checkTokenValid(final boolean saveAccountData) {

        restService.getGoogleTokenStatus(gToken, new Callback<GoogleTokenStatusModel>() {
            @Override
            public void success(GoogleTokenStatusModel googleTokenStatusModel, Response response) {

                if (googleTokenStatusModel.getStatus().equalsIgnoreCase(ConstantManager.STATUS_ERROR)) {
                    doubleTokenEcx();
                } else {
                    editPrefs();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                doubleTokenEcx();
            }
        });
    }

    @Background
    void editPrefs() {
        GoogleTokenUserDataModel accountData = restService.getGoogleUserData(gToken);
        prefs.edit()
                .googleAccountName().put(accountData.getName())
                .googleAccountEmail().put(accountData.getEmail())
                .googleAccountPictureSrc().put(accountData.getPicture())
                .apply();
        MainActivity_.intent(context).start();
        activity.finish();
    }

    private void doubleTokenEcx() {
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                new String[]{ConstantManager.GOOGLE_ACCOUNT_TYPE}, false, null, null, null, null);
        activity.startActivityForResult(intent, ConstantManager.GET_GOOGLE_TOKEN_REQUEST_CODE);
    }

    @Background
    public void getToken(Intent data) {

        final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        final String accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
        Account account = new Account(accountName, accountType);

        try {
            gToken = GoogleAuthUtil.getToken(context, account, ConstantManager.SCOPES);
            MoneyTrackerApplication.setGoogleToken(context, gToken);
            Log.e(LOG_TAG, "GOOGLE_TOKEN " + MoneyTrackerApplication.getGoogleToken(context));
            editPrefs();

        } catch (UserRecoverableAuthException userAuthEx) {
            activity.startActivityForResult(userAuthEx.getIntent(), ConstantManager.GET_GOOGLE_TOKEN_REQUEST_CODE);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } catch (GoogleAuthException fatalAuthEx) {
            fatalAuthEx.printStackTrace();
            Log.e(LOG_TAG, "Fatal Exception: " + fatalAuthEx.getLocalizedMessage());
        }

    }

    public String getGToken() {
        return gToken;
    }
}
