package com.agrotrading.kancher.moneytracker.rest;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenStatusModel;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenUserDataModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserBalanceModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserLoginModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserLogoutModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserRegistrationModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoriesModel;
import com.agrotrading.kancher.moneytracker.rest.model.expense.UserExpensesModel;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;

import retrofit.RetrofitError;

public class RestService {

    private RestClient restClient;

    public RestService() {
        restClient = new RestClient();
    }

    public UserRegistrationModel register(String login, String password) {
        return restClient.getUserAccountApi().registerUser(login, password, ConstantManager.REGISTER_FLAG);
    }

    public UserLoginModel login (String login, String password) {
        return restClient.getUserAccountApi().loginUser(login, password);
    }

    public UserLogoutModel logout() {
        return restClient.getUserAccountApi().logoutUser();
    }

    public UserCategoriesModel getAllCategories(String gToken) {
        return restClient.getCategoryApi().getAllCategories(gToken, MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoriesModel syncCategories(String data, String gToken) {
        return restClient.getCategoryApi().syncCategories(data, gToken, MoneyTrackerApplication.getAuthToken());
    }

    public UserBalanceModel getBalance(String gToken) {
        return restClient.getUserBalanceApi().getBalance(gToken, MoneyTrackerApplication.getAuthToken());
    }
    
    public UserBalanceModel setBalance(float balance, String gToken) {
        return restClient.getUserBalanceApi().setBalance(balance, gToken, MoneyTrackerApplication.getAuthToken());
    }

    public UserExpensesModel getAllExpenses(String gToken){
        return restClient.getUserExpenseApi().getAllExpenses(gToken, MoneyTrackerApplication.getAuthToken());
    }

    public UserExpensesModel syncExpenses(String data, String gToken) {
        return restClient.getUserExpenseApi().syncExpenses(data, gToken, MoneyTrackerApplication.getAuthToken());
    }

    public GoogleTokenStatusModel getGoogleTokenStatus(String gToken) throws RetrofitError {
        return restClient.getCheckGoogleTokenApi().tokenStatus(gToken);
    }

    public GoogleTokenUserDataModel getGoogleUserData(String gToken) {
        return restClient.getCheckGoogleTokenApi().googleJson(gToken);
    }

}