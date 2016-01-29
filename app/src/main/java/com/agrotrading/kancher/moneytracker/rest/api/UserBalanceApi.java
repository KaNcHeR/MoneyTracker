package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.exceptions.UnauthorizedException;
import com.agrotrading.kancher.moneytracker.rest.model.UserBalanceModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface UserBalanceApi {

    @GET("/balance")
    UserBalanceModel getBalance(@Query("google_token") String gToken, @Query("auth_token") String token) throws UnauthorizedException;

    @GET("/balance")
    UserBalanceModel setBalance(@Query("set") float balance, @Query("google_token") String gToken, @Query("auth_token") String token) throws UnauthorizedException;
}
