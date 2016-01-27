package com.agrotrading.kancher.moneytracker.rest;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.exceptions.UnauthorizedException;
import com.agrotrading.kancher.moneytracker.rest.model.UserBalanceModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserLogoutModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoriesModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserLoginModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserRegistrationModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoryExpenseModel;
import com.agrotrading.kancher.moneytracker.rest.model.expense.ExpenseData;
import com.agrotrading.kancher.moneytracker.rest.model.expense.UserExpenseModel;
import com.agrotrading.kancher.moneytracker.rest.model.expense.UserExpensesModel;
import com.agrotrading.kancher.moneytracker.utils.ConstantManager;

import java.util.ArrayList;

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

    public UserCategoriesModel getAllCategories() throws UnauthorizedException {
        return restClient.getCategoryApi().getAllCategories(MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoriesModel syncCategories(String data) throws UnauthorizedException {
        return restClient.getCategoryApi().syncCategories(data, MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoryExpenseModel getCategory(Integer id) throws UnauthorizedException {
        return restClient.getCategoryApi().getCategory(id, MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoryExpenseModel getTransCat() throws UnauthorizedException {
        return restClient.getCategoryApi().getTransCat(MoneyTrackerApplication.getAuthToken());
    }

    public UserBalanceModel getBalance(String gToken) throws UnauthorizedException {
        return restClient.getUserBalanceApi().getBalance(gToken, MoneyTrackerApplication.getAuthToken());
    }
    
    public UserBalanceModel setBalance(float balance) throws UnauthorizedException{
        return restClient.getUserBalanceApi().setBalance(balance, MoneyTrackerApplication.getAuthToken());
    }

    public UserExpensesModel getAllExpenses() throws UnauthorizedException{
        return restClient.getUserExpenseApi().getAllExpenses(MoneyTrackerApplication.getAuthToken());
    }

    public UserExpensesModel syncExpenses(String data) throws UnauthorizedException{
        return restClient.getUserExpenseApi().syncExpenses(data, MoneyTrackerApplication.getAuthToken());
    }

    public UserExpenseModel addExpense(int sum, String comment, int categoryId, String trDate) throws UnauthorizedException {
        return restClient.getUserExpenseApi().addExpense(sum, comment, categoryId, trDate, MoneyTrackerApplication.getAuthToken());
    }

}
