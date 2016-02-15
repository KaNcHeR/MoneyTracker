package com.agrotrading.kancher.moneytracker.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.utils.exceptions.UnauthorizedException;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.agrotrading.kancher.moneytracker.utils.GoogleAuthHelper;
import com.agrotrading.kancher.moneytracker.utils.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    @Bean
    GoogleAuthHelper googleAuthHelper;

    @ViewById(R.id.error_network)
    TextView errorNetwork;

    @ViewById(R.id.retry_button)
    TextView retryButton;

    @AfterViews
    void ready() {
        doInBackground();
    }

    @OnActivityResult(ConstantManager.GET_GOOGLE_TOKEN_REQUEST_CODE)
    void onResult(int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            googleAuthHelper.getToken(data);
        }
    }

    @Click(R.id.retry_button)
    void retryStart() {
        if(NetworkStatusChecker.isNetworkAvailable(this)) {
            goneError();
            start();
        }
    }

    @Background(delay = 3000)
    void doInBackground() {
        if(NetworkStatusChecker.isNetworkAvailable(this)) {
            start();
            return;
        }

        visibleError();
    }

    @Background(id = "start")
    void start() {
        if(!MoneyTrackerApplication.getGoogleToken(this).equalsIgnoreCase(ConstantManager.DEFAULT_GOOGLE_TOKEN)) {
            googleAuthHelper.checkTokenValid();
            return;
        }

        if(MoneyTrackerApplication.getAuthToken() == null &&
                MoneyTrackerApplication.getGoogleToken(this).equalsIgnoreCase(ConstantManager.DEFAULT_GOOGLE_TOKEN)) {
            UserLoginActivity_.intent(this).start();
            finish();
            return;
        }

        try {
            RestService restService = new RestService();
            String gToken = MoneyTrackerApplication.getGoogleToken(this);
            restService.getBalance(gToken);
            MainActivity_.intent(this).start();
        } catch (UnauthorizedException e) {
            UserLoginActivity_.intent(this).start();
        } finally {
            finish();
        }

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