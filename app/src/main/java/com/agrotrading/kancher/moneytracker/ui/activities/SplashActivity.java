package com.agrotrading.kancher.moneytracker.ui.activities;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.widget.TextView;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.utils.ApplicationPreferences_;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.agrotrading.kancher.moneytracker.utils.DialogHelper;
import com.agrotrading.kancher.moneytracker.utils.GoogleAuthHelper;
import com.agrotrading.kancher.moneytracker.utils.RetrofitEventBusBridge;
import com.agrotrading.kancher.moneytracker.utils.event.MessageEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.api.BackgroundExecutor;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;

@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity {

    ActivityOptionsCompat transitionActivityOptions;

    boolean timeToFinish = false;

    @Bean
    GoogleAuthHelper googleAuthHelper;

    @Bean
    DialogHelper dialogHelper;

    @Pref
    ApplicationPreferences_ prefs;

    @ViewById(R.id.error_network)
    TextView errorNetwork;

    @ViewById(R.id.retry_button)
    TextView retryButton;

    @AfterViews
    void ready() {
        setupWindowAnimations();
        //noinspection unchecked
        transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
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
        goneError();
        start();
    }

    @Background(delay = 3000)
    void doInBackground() {
        start();
    }

    @Background(id = "start")
    void start() {
        String token = MoneyTrackerApplication.getAuthToken();
        String gToken = MoneyTrackerApplication.getGoogleToken(this);
        String email = prefs.googleAccountEmail().get();
        RestService restService;

        if(token == null && (gToken.equalsIgnoreCase(ConstantManager.DEFAULT_GOOGLE_TOKEN) || email.equalsIgnoreCase(""))) {
            startLoginActivity();
            return;
        }

        dialogHelper.showProgressDialog(getString(R.string.progress_dialog_sync));

        if(!gToken.equalsIgnoreCase(ConstantManager.DEFAULT_GOOGLE_TOKEN) && !email.equalsIgnoreCase("")) {
            googleAuthHelper.checkTokenValid();
            timeToFinish = true;
            return;
        }

        try {
            restService = new RestService();
            restService.getBalance(gToken);
            startMainActivity();
        } catch (RetrofitError error) {
            RetrofitEventBusBridge.showEvent(error);
        }
    }

    @UiThread
    void startLoginActivity() {
        UserLoginActivity_.intent(this).withOptions(transitionActivityOptions.toBundle()).start();
        timeToFinish = true;
    }

    @UiThread
    void startMainActivity() {
        MainActivity_.intent(this).withOptions(transitionActivityOptions.toBundle()).start();
        timeToFinish = true;
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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        if(timeToFinish) {
            finish();
        }
    }

    public void onEventMainThread(MessageEvent event) {
        dialogHelper.hideProgressDialog();
        switch (event.code) {
            case MessageEvent.ALERT_UNAUTHORIZED:
                UserLoginActivity_.intent(this).withOptions(transitionActivityOptions.toBundle()).start();
                finish();
                break;
            case MessageEvent.ALERT_NO_INTERNET:
                errorNetwork.setText(R.string.network_not_available);
                break;
            case MessageEvent.CONNECTION_TIMEOUT:
                errorNetwork.setText(R.string.connection_timeout);
                break;
            case MessageEvent.SERVER_NOT_RESPOND:
                errorNetwork.setText(R.string.server_error);
                break;
        }
        visibleError();
    }

    private void setupWindowAnimations() {
        Fade fadeTransition = new Fade();
        fadeTransition.setDuration(500);
        getWindow().setExitTransition(fadeTransition);
    }

}