package com.agrotrading.kancher.moneytracker.ui.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.exceptions.UnauthorizedException;
import com.agrotrading.kancher.moneytracker.rest.RestClient;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenStatusModel;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.agrotrading.kancher.moneytracker.utils.NetworkStatusChecker;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    private static final String LOG_TAG = SplashActivity.class.getSimpleName();

    private String gToken;
    private RestClient restClient;

    @ViewById(R.id.error_network)
    TextView errorNetwork;

    @ViewById(R.id.retry_button)
    TextView retryButton;

    @AfterViews
    void ready() {
        restClient = new RestClient();
        gToken = MoneyTrackerApplication.getGoogleToken(this);
        doInBackground();
    }

    @Background
    void checkTokenValid() {
        restClient.getCheckGoogleTokenApi().tokenStatus(gToken, new Callback<GoogleTokenStatusModel>() {
            @Override
            public void success(GoogleTokenStatusModel googleTokenStatusModel, Response response) {
                Log.e(LOG_TAG, googleTokenStatusModel.getStatus());
                if (googleTokenStatusModel.getStatus().equalsIgnoreCase(ConstantManager.STATUS_ERROR)) {
                    doubleTokenEcx();
                } else {
                    MainActivity_.intent(SplashActivity.this).start();
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                doubleTokenEcx();
            }
        });
    }

    private void doubleTokenEcx() {
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(intent, ConstantManager.GET_GOOGLE_TOKEN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ConstantManager.GET_GOOGLE_TOKEN_REQUEST_CODE && resultCode == RESULT_OK) {
            getToken(data);
        }
    }

    @Background
    void getToken(Intent data) {

        final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        final String accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
        Account account = new Account(accountName, accountType);

        try {
            String token = GoogleAuthUtil.getToken(SplashActivity.this, account, ConstantManager.SCOPES);
            MoneyTrackerApplication.setGoogleToken(this, token);
            Log.e(LOG_TAG, "GOOGLE_TOKEN " + MoneyTrackerApplication.getGoogleToken(this));
            MainActivity_.intent(this).start();
            finish();
        } catch (UserRecoverableAuthException userAuthEx) {
            startActivityForResult(userAuthEx.getIntent(), ConstantManager.GET_GOOGLE_TOKEN_REQUEST_CODE);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } catch (GoogleAuthException fatalAuthEx) {
            fatalAuthEx.printStackTrace();
            Log.e(LOG_TAG, "Fatal Exception: " + fatalAuthEx.getLocalizedMessage());
        }

    }

    @Click(R.id.retry_button)
    void retryStart() {

        if(!NetworkStatusChecker.isNetworkAvailable(this)) {
            return;
        }

        goneError();
        start();

    }

    @Background(delay = 3000)
    void doInBackground() {

        if(!NetworkStatusChecker.isNetworkAvailable(this)) {
            visibleError();
            return;
        }

        start();
    }

    @Background(id="start")
    void start() {

        if(!gToken.equalsIgnoreCase(ConstantManager.DEFAULT_GOOGLE_TOKEN)) {
            checkTokenValid();
            return;
        }

        if(MoneyTrackerApplication.getAuthToken() != null || MoneyTrackerApplication.getGoogleToken(this) != null) {

            try {
                RestService restService = new RestService();
                String gToken = MoneyTrackerApplication.getGoogleToken(this);
                Log.e(LOG_TAG, "beforeGetBalance");
                restService.getBalance(gToken);
                Log.e(LOG_TAG, "afterGetBalance");
                MainActivity_.intent(this).start();
                finish();
                return;

            } catch (UnauthorizedException e) {
                e.printStackTrace();
            }

        }

        UserLoginActivity_.intent(this).start();
        finish();

    }

    @UiThread
    void visibleError() {
        errorNetwork.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.VISIBLE);
    }

    @UiThread
    void goneError() {
        errorNetwork.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BackgroundExecutor.cancelAll("start", true);
    }

}
