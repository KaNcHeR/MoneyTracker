package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.rest.model.expense.UserExpensesModel;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface UserExpenseApi {

    @GET("/transactions")
    UserExpensesModel getAllExpenses(@Query("google_token") String gToken, @Query("auth_token") String token);

    @POST("/transactions/synch")
    UserExpensesModel syncExpenses(@Query("data") String data, @Query("google_token") String gToken, @Query("auth_token") String token);

}
