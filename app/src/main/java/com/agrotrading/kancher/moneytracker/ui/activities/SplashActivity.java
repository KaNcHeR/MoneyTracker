package com.agrotrading.kancher.moneytracker.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.exceptions.UnauthorizedException;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.utils.NetworkStatusChecker;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.api.BackgroundExecutor;


@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    @ViewById(R.id.error_network)
    TextView errorNetwork;

    @ViewById(R.id.retry_button)
    TextView retryButton;

    @AfterViews
    void ready() {
        doInBackground();
    }

    @Click(R.id.retry_button)
    void retryStart() {
        goneError();
        start();
    }

    @Background(delay = 2000)
    void doInBackground() {
        start();
    }

    @Background(id="start",delay = 1000)
    void start() {

        if(!NetworkStatusChecker.isNetworkAvailable(getApplicationContext())) {
            visibleError();
            return;
        }

        if(MoneyTrackerApplication.getAuthToken() != null) {

            try {
                RestService restService = new RestService();
                restService.getBalance();
                MainActivity_.intent(this).start();

            } catch (UnauthorizedException e) {
                e.printStackTrace();
            }

        } else {
            UserLoginActivity_.intent(this).start();
        }

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
