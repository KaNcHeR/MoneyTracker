package com.agrotrading.kancher.moneytracker.rest;

import com.agrotrading.kancher.moneytracker.handlers.RetrofitErrorHandler;
import com.agrotrading.kancher.moneytracker.rest.api.UserCategoryApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserAccountApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserBalanceApi;
import com.agrotrading.kancher.moneytracker.rest.api.UserExpenseApi;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;

import retrofit.RestAdapter;

public class RestClient {

    private UserAccountApi userAccountApi;
    private UserCategoryApi categoryApi;
    private UserBalanceApi userBalanceApi;
    private UserExpenseApi userExpenseApi;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ConstantManager.BASE_URL)
                .setErrorHandler(new RetrofitErrorHandler())
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        userAccountApi = restAdapter.create(UserAccountApi.class);
        categoryApi = restAdapter.create(UserCategoryApi.class);
        userBalanceApi = restAdapter.create(UserBalanceApi.class);
        userExpenseApi = restAdapter.create(UserExpenseApi.class);

    }

    public UserAccountApi getUserAccountApi() { return userAccountApi; }

    public UserCategoryApi getCategoryApi() { return categoryApi; }

    public UserBalanceApi getUserBalanceApi() { return userBalanceApi; }

    public UserExpenseApi getUserExpenseApi() { return userExpenseApi; }
}
