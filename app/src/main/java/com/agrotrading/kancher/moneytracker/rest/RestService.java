package com.agrotrading.kancher.moneytracker.rest;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.exceptions.UnauthorizedException;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenUserDataModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserBalanceModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserLogoutModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoriesModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserLoginModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserRegistrationModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoryExpenseModel;
import com.agrotrading.kancher.moneytracker.rest.model.expense.UserExpenseModel;
import com.agrotrading.kancher.moneytracker.rest.model.expense.UserExpensesModel;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;
import com.google.android.gms.auth.GoogleAuthException;

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

    public UserCategoriesModel getAllCategories(String gToken) throws UnauthorizedException {
        return restClient.getCategoryApi().getAllCategories(gToken, MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoriesModel syncCategories(String data, String gToken) throws UnauthorizedException {
        return restClient.getCategoryApi().syncCategories(data, gToken, MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoryExpenseModel getCategory(Integer id, String gToken) throws UnauthorizedException {
        return restClient.getCategoryApi().getCategory(id, gToken, MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoryExpenseModel getTransCat(String gToken) throws UnauthorizedException {
        return restClient.getCategoryApi().getTransCat(gToken, MoneyTrackerApplication.getAuthToken());
    }

    public UserBalanceModel getBalance(String gToken) throws UnauthorizedException, GoogleAuthException {
        return restClient.getUserBalanceApi().getBalance(gToken, MoneyTrackerApplication.getAuthToken());
    }
    
    public UserBalanceModel setBalance(float balance, String gToken) throws UnauthorizedException{
        return restClient.getUserBalanceApi().setBalance(balance, gToken, MoneyTrackerApplication.getAuthToken());
    }

    public UserExpensesModel getAllExpenses(String gToken) throws UnauthorizedException{
        return restClient.getUserExpenseApi().getAllExpenses(gToken, MoneyTrackerApplication.getAuthToken());
    }

    public UserExpensesModel syncExpenses(String data, String gToken) throws UnauthorizedException{
        return restClient.getUserExpenseApi().syncExpenses(data, gToken, MoneyTrackerApplication.getAuthToken());
    }

    public UserExpenseModel addExpense(int sum, String comment, int categoryId, String trDate, String gToken) throws UnauthorizedException {
        return restClient.getUserExpenseApi().addExpense(sum, comment, categoryId, trDate, gToken, MoneyTrackerApplication.getAuthToken());
    }

    public GoogleTokenUserDataModel getGoogleUserData(String gToken) {
        return restClient.getCheckGoogleTokenApi().googleJson(gToken);
    }

}