package com.agrotrading.kancher.moneytracker.rest;

import com.agrotrading.kancher.moneytracker.rest.api.UserAccountApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserBalanceApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserCategoryApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserExpenseApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserGoogleAccountApi;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.google.gson.Gson;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

public class RestClient {

    private UserAccountApi userAccountApi;
    private UserCategoryApi categoryApi;
    private UserBalanceApi userBalanceApi;
    private UserExpenseApi userExpenseApi;
    private UserGoogleAccountApi checkGoogleTokenApi;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient())
                .setEndpoint(ConstantManager.BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(new Gson()))
                .build();

        userAccountApi = restAdapter.create(UserAccountApi.class);
        categoryApi = restAdapter.create(UserCategoryApi.class);
        userBalanceApi = restAdapter.create(UserBalanceApi.class);
        userExpenseApi = restAdapter.create(UserExpenseApi.class);
        checkGoogleTokenApi = restAdapter.create(UserGoogleAccountApi.class);

    }

    public UserAccountApi getUserAccountApi() { return userAccountApi; }

    public UserCategoryApi getCategoryApi() { return categoryApi; }

    public UserBalanceApi getUserBalanceApi() { return userBalanceApi; }

    public UserExpenseApi getUserExpenseApi() { return userExpenseApi; }

    public UserGoogleAccountApi getCheckGoogleTokenApi() {return checkGoogleTokenApi; }
}