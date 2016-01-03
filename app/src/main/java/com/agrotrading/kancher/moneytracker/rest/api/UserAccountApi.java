package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.rest.model.UserLoginModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserLogoutModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserRegistrationModel;

import retrofit.http.GET;
import retrofit.http.Query;

public interface UserAccountApi {

    @GET("/auth")
    UserRegistrationModel registerUser(@Query("login") String login, @Query("password") String password,
                                       @Query("register") String register);

    @GET("/auth")
    UserLoginModel loginUser(@Query("login") String login, @Query("password") String password);

    @GET("/logout")
    UserLogoutModel logoutUser();
}
