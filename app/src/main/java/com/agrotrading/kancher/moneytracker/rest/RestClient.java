package com.agrotrading.kancher.moneytracker.rest;

import com.agrotrading.kancher.moneytracker.handlers.RetrofitErrorHandler;
import com.agrotrading.kancher.moneytracker.rest.api.UserGoogleAccountApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserCategoryApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserAccountApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserBalanceApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserExpenseApi;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

public class RestClient {

    private UserAccountApi userAccountApi;
    private UserCategoryApi categoryApi;
    private UserBalanceApi userBalanceApi;
    private UserExpenseApi userExpenseApi;
    private UserGoogleAccountApi checkGoogleTokenApi;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient(getClient()))
                .setEndpoint(ConstantManager.BASE_URL)
                .setErrorHandler(new RetrofitErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        userAccountApi = restAdapter.create(UserAccountApi.class);
        categoryApi = restAdapter.create(UserCategoryApi.class);
        userBalanceApi = restAdapter.create(UserBalanceApi.class);
        userExpenseApi = restAdapter.create(UserExpenseApi.class);
        checkGoogleTokenApi = restAdapter.create(UserGoogleAccountApi.class);

    }

    private OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(60, TimeUnit.SECONDS);
        client.setReadTimeout(60, TimeUnit.SECONDS);
        return client;
    }

    public UserAccountApi getUserAccountApi() { return userAccountApi; }

    public UserCategoryApi getCategoryApi() { return categoryApi; }

    public UserBalanceApi getUserBalanceApi() { return userBalanceApi; }

    public UserExpenseApi getUserExpenseApi() { return userExpenseApi; }

    public UserGoogleAccountApi getCheckGoogleTokenApi() {return checkGoogleTokenApi; }
}
