package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoriesModel;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface UserCategoryApi {

    @GET("/categories")
    UserCategoriesModel getAllCategories(@Query("google_token") String gToken, @Query("auth_token") String token);

    @POST("/categories/synch")
    UserCategoriesModel syncCategories(@Query("data") String data, @Query("google_token") String gToken, @Query("auth_token") String token);

}
