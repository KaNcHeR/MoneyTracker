package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenStatusModel;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenUserDataModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface UserGoogleAccountApi {

    @GET("/gcheck")
    GoogleTokenStatusModel tokenStatus(@Query("google_token") String gToken);

    @GET("/gjson")
    GoogleTokenUserDataModel googleJson(@Query("google_token") String gToken);
}
