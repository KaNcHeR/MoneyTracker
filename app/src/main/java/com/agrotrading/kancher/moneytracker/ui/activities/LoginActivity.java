package com.agrotrading.kancher.moneytracker.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.rest.model.CreateCategoryModel;
import com.agrotrading.kancher.moneytracker.rest.model.Data;
import com.agrotrading.kancher.moneytracker.rest.model.UserLoginModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @AfterViews
    void ready(){
        //проверка интернета и поле и т.д. ...
        login();
    }

    @Background
    void login() {
        RestService restService = new RestService();
        UserLoginModel userLoginModel = restService.login("kancher", "16111988");
        MoneyTrackerApplication.setAuthToken(userLoginModel.getAuthToken());
        Log.d(LOG_TAG, "Status: " + userLoginModel.getStatus() + ", token: " + MoneyTrackerApplication.getAuthToken());

        CreateCategoryModel createCategory = restService.createCategory("Test1");
        Data data = createCategory.getData();
        Log.d(LOG_TAG, "Status: " + createCategory.getStatus() + ", title: " + data.getTitle() + ", id: " + data.getId());
    }
}
