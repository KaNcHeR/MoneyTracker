package com.agrotrading.kancher.moneytracker.rest;

import com.agrotrading.kancher.moneytracker.handlers.RetrofitErrorHandler;
import com.agrotrading.kancher.moneytracker.rest.api.CreateCategoryApi;
import com.agrotrading.kancher.moneytracker.rest.api.LoginUserApi;
import com.agrotrading.kancher.moneytracker.rest.api.RegisterUserApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserBalanceApi;
import com.agrotrading.kancher.moneytracker.rest.api.WrongTokenApi;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;

import retrofit.RestAdapter;

public class RestClient {

    private RegisterUserApi registerUserApi;
    private LoginUserApi loginUserApi;
    private CreateCategoryApi createCategoryApi;
    private WrongTokenApi wrongTokenApi;
    private UserBalanceApi userBalanceApi;
    //...

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConstantManager.BASE_URL)
                .setErrorHandler(new RetrofitErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        registerUserApi = restAdapter.create(RegisterUserApi.class);
        loginUserApi = restAdapter.create(LoginUserApi.class);
        createCategoryApi = restAdapter.create(CreateCategoryApi.class);
        wrongTokenApi = restAdapter.create(WrongTokenApi.class);
        userBalanceApi = restAdapter.create(UserBalanceApi.class);
        //...
    }

    public RegisterUserApi getRegisterUserApi() { return registerUserApi; }

    public LoginUserApi getLoginUserApi() {
        return loginUserApi;
    }

    public CreateCategoryApi getCreateCategoryApi() {
        return createCategoryApi;
    }

    public WrongTokenApi getWrongTokenApi() { return wrongTokenApi; }

    public UserBalanceApi getUserBalanceApi() {return userBalanceApi; }
}
