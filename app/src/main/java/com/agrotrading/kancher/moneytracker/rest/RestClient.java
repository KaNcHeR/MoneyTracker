package com.agrotrading.kancher.moneytracker.rest;

import com.agrotrading.kancher.moneytracker.rest.api.CreateCategoryApi;
import com.agrotrading.kancher.moneytracker.rest.api.LoginUserApi;
import com.agrotrading.kancher.moneytracker.rest.api.RegisterUserApi;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;

import retrofit.RestAdapter;

public class RestClient {

    private RegisterUserApi registerUserApi;
    private LoginUserApi loginUserApi;
    private CreateCategoryApi createCategoryApi;
    //...

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConstantManager.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        registerUserApi = restAdapter.create(RegisterUserApi.class);
        loginUserApi = restAdapter.create(LoginUserApi.class);
        createCategoryApi = restAdapter.create(CreateCategoryApi.class);
        //...
    }

    public RegisterUserApi getRegisterUserApi() {
        return registerUserApi;
    }

    public LoginUserApi getLoginUserApi() {
        return loginUserApi;
    }

    public CreateCategoryApi getCreateCategoryApi() {
        return createCategoryApi;
    }
}
