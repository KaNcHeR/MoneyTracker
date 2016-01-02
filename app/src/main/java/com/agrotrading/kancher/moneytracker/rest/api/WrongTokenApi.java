package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.rest.model.WrongTokenModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface WrongTokenApi {

    @GET("/balance")
    WrongTokenModel getStatus(@Query("auth_token") String token);
}
