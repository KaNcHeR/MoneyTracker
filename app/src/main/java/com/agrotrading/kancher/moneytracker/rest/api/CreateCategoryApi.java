package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.rest.model.category.CreateCategoryModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface CreateCategoryApi {

    @GET("/categories/add")
    CreateCategoryModel createCategory(@Query("title") String title, @Query("auth_token") String token);
}
