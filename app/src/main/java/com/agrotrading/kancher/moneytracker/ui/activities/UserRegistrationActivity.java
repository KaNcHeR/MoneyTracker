package com.agrotrading.kancher.moneytracker.ui.activities;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.rest.model.UserRegistrationModel;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.agrotrading.kancher.moneytracker.utils.NetworkStatusChecker;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_user_registration)
public class UserRegistrationActivity extends AppCompatActivity {

    @ViewById(R.id.root_view)
    View rootView;

    @ViewById(R.id.et_login)
    EditText etLogin;

    @ViewById(R.id.et_password)
    EditText etPassword;

    @ViewById(R.id.registration_button)
    Button bRegistration;

    @Click(R.id.tv_login_button)
    void registration() {
        UserLoginActivity_.intent(this).start();
        finish();
    }

    @Click(R.id.registration_button)
    void registration(View clickedView) {

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
        } else {
            bRegistration.setEnabled(false);
            registerUser();
        }
    }

    @Background
    public void registerUser() {

        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();

        RestService restService = new RestService();
        UserRegistrationModel userRegistrationModel = restService.register(login, password);

        switch (userRegistrationModel.getStatus()) {
            case ConstantManager.STATUS_LOGIN_BUSY_ALREADY:
                Snackbar.make(rootView, getString(R.string.user_registration_login_busy_already), Snackbar.LENGTH_LONG).show();
                break;
            case ConstantManager.STATUS_SUCCESS:
                MainActivity_.intent(this).start();
                finish();
                return;
            default:
                Snackbar.make(rootView, getString(R.string.user_registration_other_error), Snackbar.LENGTH_LONG).show();
                break;
        }
        enabledRegistrationButton();
    }

    @UiThread
    void enabledRegistrationButton(){
        bRegistration.setEnabled(true);
    }
}