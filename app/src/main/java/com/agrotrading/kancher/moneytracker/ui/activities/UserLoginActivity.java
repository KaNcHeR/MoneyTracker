package com.agrotrading.kancher.moneytracker.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.rest.model.UserLoginModel;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.agrotrading.kancher.moneytracker.utils.DialogHelper;
import com.agrotrading.kancher.moneytracker.utils.GoogleAuthHelper;
import com.agrotrading.kancher.moneytracker.utils.RetrofitEventBusBridge;
import com.agrotrading.kancher.moneytracker.utils.event.MessageEvent;
import com.google.android.gms.common.AccountPicker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit.RetrofitError;

@EActivity(R.layout.activity_login)
public class UserLoginActivity extends AppCompatActivity {

    boolean timeToFinish = false;

    @Bean
    GoogleAuthHelper googleAuthHelper;

    @Bean
    DialogHelper dialogHelper;

    @ViewById(R.id.root_view)
    View rootView;

    @ViewById(R.id.et_login)
    EditText etLogin;

    @ViewById(R.id.et_password)
    EditText etPassword;

    @ViewById(R.id.login_button)
    Button bLogin;

    @OnActivityResult(ConstantManager.GET_GOOGLE_TOKEN_REQUEST_CODE)
    void onResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            dialogHelper.showProgressDialog(getString(R.string.progress_dialog_sync));
            try {
                googleAuthHelper.getToken(data);
            } catch (RetrofitError error) {
                RetrofitEventBusBridge.showEvent(error);
                return;
            }
            timeToFinish = true;
        }
    }

    @Click(R.id.tv_registration_button)
    void registration() {
        UserRegistrationActivity_.intent(this).start();
        finish();
    }

    @Click(R.id.login_button)
    void login() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if (etLogin.length() < 5 || etPassword.length() < 5) {
            Snackbar.make(rootView, getString(R.string.user_registration_characters_long), Snackbar.LENGTH_LONG).show();
        } else {
            bLogin.setEnabled(false);
            loginUser();
        }
    }

    @Click(R.id.sign_in_button)
    void btnGPlusLogin() {
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{ConstantManager.GOOGLE_ACCOUNT_TYPE}, false, null, null, null, null);
        startActivityForResult(intent, ConstantManager.GET_GOOGLE_TOKEN_REQUEST_CODE);
    }

    @Background
    void loginUser() {
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        RestService restService = new RestService();
        dialogHelper.showProgressDialog(getString(R.string.progress_dialog_sync));
        try {
            UserLoginModel userLoginModel = restService.login(login, password);

            MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());

            switch (userLoginModel.getStatus()) {

                case ConstantManager.STATUS_WRONG_LOGIN:
                    Snackbar.make(rootView, getString(R.string.wrong_login), Snackbar.LENGTH_LONG).show();
                    break;
                case ConstantManager.STATUS_WRONG_PASSWORD:
                    Snackbar.make(rootView, getString(R.string.wrong_password), Snackbar.LENGTH_LONG).show();
                    break;
                case ConstantManager.STATUS_SUCCESS:
                    googleAuthHelper.startMainActivityWithoutGToken();
                    return;
                default:
                    Snackbar.make(rootView, getString(R.string.user_registration_other_error), Snackbar.LENGTH_LONG).show();
                    break;
            }
        } catch (RetrofitError error) {
            RetrofitEventBusBridge.showEvent(error);
        }

        enabledRegistrationButton();
    }

    @UiThread
    void enabledRegistrationButton() {
        bLogin.setEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(timeToFinish) {
            finish();
        }
        EventBus.getDefault().unregister(this);
    }

    private void showSnackBar(int resId) {
        Snackbar.make(rootView, resId, Snackbar.LENGTH_LONG).show();
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        dialogHelper.hideProgressDialog();
        switch (event.code) {
            case MessageEvent.ALERT_NO_INTERNET:
                showSnackBar(R.string.network_not_available);
                break;
            case MessageEvent.CONNECTION_TIMEOUT:
                showSnackBar(R.string.connection_timeout);
                break;
            case MessageEvent.SERVER_NOT_RESPOND:
                showSnackBar(R.string.server_error);
                break;
        }
    }

}