package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenStatusModel;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenUserDataModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface CheckGoogleTokenApi {

    @GET("/gcheck")
    void tokenStatus(@Query("google_token") String gToken, Callback<GoogleTokenStatusModel> modelCallback);

    @GET("/gjson")
    GoogleTokenUserDataModel googleJson(@Query("google_token") String gToken);
}
