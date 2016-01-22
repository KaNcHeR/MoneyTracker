package com.agrotrading.kancher.moneytracker.rest.api;

import com.agrotrading.kancher.moneytracker.rest.model.expense.ExpenseData;
import com.agrotrading.kancher.moneytracker.rest.model.expense.UserExpenseModel;
import com.agrotrading.kancher.moneytracker.rest.model.expense.UserExpensesModel;

import java.util.ArrayList;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface UserExpenseApi {

    @GET("/transactions")
    UserExpensesModel getAllExpenses(@Query("auth_token") String token);

    @POST("/transactions/synch")
    UserExpensesModel syncExpenses(@Query("data") ArrayList<ExpenseData> data, @Query("auth_token") String token);

    @POST("/transactions/add")
    UserExpenseModel addExpense(@Query("sum") Integer sum, @Query("comment") String comment,
                                @Query("category_id") Integer categoryId, @Query("tr_date") String trDate,
                                @Query("auth_token") String token);
}