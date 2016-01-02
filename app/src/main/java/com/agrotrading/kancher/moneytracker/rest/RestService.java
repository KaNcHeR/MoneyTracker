package com.agrotrading.kancher.moneytracker.rest;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.exceptions.UnauthorizedException;
import com.agrotrading.kancher.moneytracker.rest.model.UserBalanceModel;
import com.agrotrading.kancher.moneytracker.rest.model.WrongTokenModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.CreateCategoryModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserLoginModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserRegistrationModel;

import retrofit.RetrofitError;

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

    public CreateCategoryModel createCategory (String title) throws UnauthorizedException {
        return restClient.getCreateCategoryApi().createCategory(title, MoneyTrackerApplication.getAuthToken());
    }

    public WrongTokenModel getWrongToken() {
        return restClient.getWrongTokenApi().getStatus(MoneyTrackerApplication.getAuthToken());
    }

    public UserBalanceModel getBalance() throws UnauthorizedException {
        return restClient.getUserBalanceApi().getBalance(MoneyTrackerApplication.getAuthToken());
    }
    
    public UserBalanceModel setBalance(float balance) throws UnauthorizedException{
        return restClient.getUserBalanceApi().setBalance(balance, MoneyTrackerApplication.getAuthToken());
    }

    //...logout etc.
}
