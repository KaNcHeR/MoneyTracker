package com.agrotrading.kancher.moneytracker.ui.activities;

import android.content.Context;
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
import com.agrotrading.kancher.moneytracker.utils.NetworkStatusChecker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_login)
public class UserLoginActivity extends AppCompatActivity {

    @ViewById(R.id.et_login)
    EditText etLogin;

    @ViewById(R.id.et_password)
    EditText etPassword;

    @ViewById(R.id.login_button)
    Button bLogin;

    @Click(R.id.tv_registration_button)
    void registration() {
        UserRegistrationActivity_.intent(this).start();
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
