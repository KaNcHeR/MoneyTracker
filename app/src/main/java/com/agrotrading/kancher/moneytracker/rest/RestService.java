package com.agrotrading.kancher.moneytracker.rest;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.rest.model.CreateCategoryModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserLoginModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserRegistrationModel;

public class RestService {

    private static final String REGISTER_FLAG = "1";

    private RestClient restClient;

    public RestService() {
        restClient = new RestClient();
    }

    public UserRegistrationModel register(String login, String password) {
        return restClient.getRegisterUserApi().registerUser(login, password, REGISTER_FLAG);
    }

    public UserLoginModel login (String login, String password) {
        return restClient.getLoginUserApi().loginUser(login, password);
    }

    public CreateCategoryModel createCategory (String title) {
        return restClient.getCreateCategoryApi().createCategory(title, MoneyTrackerApplication.getAuthToken());
    }
    //...logout etc.
}
