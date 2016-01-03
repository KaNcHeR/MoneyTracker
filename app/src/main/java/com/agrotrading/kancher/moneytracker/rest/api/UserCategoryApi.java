package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoriesModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoryModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.CategoryData;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoryTransactionModel;

import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface UserCategoryApi {

    @POST("/categories/add")
    UserCategoryModel createCategory(@Query("title") String title, @Query("auth_token") String token);

    @GET("/categories")
    UserCategoriesModel getAllCategories(@Query("auth_token") String token);

    @POST("/categories/synch")
    UserCategoriesModel synchCategories(@Query("data") ArrayList<CategoryData> data, @Query("auth_token") String token);

    @POST("/categories/edit")
    UserCategoryModel editCategory(@Query("title") String title, @Query("id") Integer id, @Query("auth_token") String token);

    @POST("/categories/del")
    Response deleteCategory(@Query("id") Integer id, @Query("auth_token") String token);

    @GET("/categories/{id}")
    UserCategoryTransactionModel getCategory(@Path("id") Integer id, @Query("auth_token") String token);

    @GET("/transcat")
    UserCategoryTransactionModel getTransCat(@Query("auth_token") String token);
}
