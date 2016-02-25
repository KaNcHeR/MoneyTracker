package com.agrotrading.kancher.moneytracker.ui.activities;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.rest.model.UserRegistrationModel;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.agrotrading.kancher.moneytracker.utils.DialogHelper;
import com.agrotrading.kancher.moneytracker.utils.RetrofitEventBusBridge;
import com.agrotrading.kancher.moneytracker.utils.event.MessageEvent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit.RetrofitError;

@EActivity(R.layout.activity_user_registration)
public class UserRegistrationActivity extends AppCompatActivity {

    @Bean
    DialogHelper dialogHelper;

    @ViewById(R.id.root_view)
    View rootView;

    @ViewById(R.id.et_login)
    EditText etLogin;

    @ViewById(R.id.et_password)
    EditText etPassword;

    @ViewById(R.id.registration_button)
    Button bRegistration;

    @AfterViews
    void init() {
        setupWindowAnimations();
    }

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
        dialogHelper.showProgressDialog(getString(R.string.progress_dialog_sync));

        try {
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
        } catch (RetrofitError error) {
            RetrofitEventBusBridge.showEvent(error);
        }

        enabledRegistrationButton();
    }

    @UiThread
    void enabledRegistrationButton(){
        bRegistration.setEnabled(true);
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

    private void setupWindowAnimations() {
        Fade enterTransition = new Fade();
        enterTransition.setDuration(500);
        getWindow().setEnterTransition(enterTransition);
        getWindow().setExitTransition(enterTransition);
    }
}