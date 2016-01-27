package com.agrotrading.kancher.moneytracker.ui.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.rest.model.UserLoginModel;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.agrotrading.kancher.moneytracker.utils.NetworkStatusChecker;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

@EActivity(R.layout.activity_login)
public class UserLoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = UserLoginActivity.class.getSimpleName();

    @ViewById(R.id.et_login)
    EditText etLogin;

    @ViewById(R.id.et_password)
    EditText etPassword;

    @ViewById(R.id.login_button)
    Button bLogin;

    @Click(R.id.tv_registration_button)
    void registration() {
        UserRegistrationActivity_.intent(this).start();
        finish();
    }

    @Click(R.id.login_button)
    void login(View clickedView) {

        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        if(!NetworkStatusChecker.isNetworkAvailable(getApplicationContext())) {
            Snackbar.make(clickedView, getString(R.string.network_not_available), Snackbar.LENGTH_SHORT).show();
            return;
        }

        if(etLogin.length() < 5 || etPassword.length() < 5) {
            Snackbar.make(clickedView, getString(R.string.user_registration_characters_long), Snackbar.LENGTH_LONG).show();
            return;
        }

        bLogin.setEnabled(false);

        loginUser(clickedView);
    }

    @Click(R.id.sign_in_button)
    void btnGPlusLogin() {
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
            String token = GoogleAuthUtil.getToken(UserLoginActivity.this, account, ConstantManager.SCOPES);
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

    @Background
    void loginUser(View view) {

        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();

        RestService restService = new RestService();
        UserLoginModel userLoginModel = restService.login(login, password);
        MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());

        switch (userLoginModel.getStatus()) {

            case ConstantManager.STATUS_WRONG_LOGIN:
                Snackbar.make(view, getString(R.string.wrong_login), Snackbar.LENGTH_LONG).show();
                break;

            case ConstantManager.STATUS_WRONG_PASSWORD:
                Snackbar.make(view, getString(R.string.wrong_password), Snackbar.LENGTH_LONG).show();
                break;

            case ConstantManager.STATUS_SUCCESS:
                MainActivity_.intent(this).start();
                finish();
                return;

            default:
                Snackbar.make(view, getString(R.string.user_registration_other_error), Snackbar.LENGTH_LONG).show();
                break;

        }

        enabledRegistrationButton();

    }


    @UiThread
    void enabledRegistrationButton(){
        bLogin.setEnabled(true);
    }

}
