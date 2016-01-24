package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoriesModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoryExpenseModel;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface UserCategoryApi {

    @GET("/categories")
    UserCategoriesModel getAllCategories(@Query("auth_token") String token);

    @POST("/categories/synch")

    UserCategoriesModel syncCategories(@Query("data") String data, @Query("auth_token") String token);

    @GET("/categories/{id}")
    UserCategoryExpenseModel getCategory(@Path("id") Integer id, @Query("auth_token") String token);

    @GET("/transcat")
    UserCategoryExpenseModel getTransCat(@Query("auth_token") String token);
}
