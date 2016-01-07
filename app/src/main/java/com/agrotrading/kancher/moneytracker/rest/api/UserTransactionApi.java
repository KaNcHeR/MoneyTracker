package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.rest.model.transaction.TransactionData;
import com.agrotrading.kancher.moneytracker.rest.model.transaction.UserTransactionModel;
import com.agrotrading.kancher.moneytracker.rest.model.transaction.UserTransactionsModel;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface UserTransactionApi {

    @GET("/transactions")
    UserTransactionsModel getAllTransactions(@Query("auth_token") String token);

    @POST("/transactions/synch")
    UserTransactionsModel synchTransactions(@Query("data") ArrayList<TransactionData> data, @Query("auth_token") String token);

    @POST("/transactions/add")
    UserTransactionModel addTransaction(@Query("sum") Integer sum, @Query("comment") String comment,
                                 @Query("category_id") Integer categoryId, @Query("tr_date") String trDate,
                                 @Query("auth_token") String token);
}
