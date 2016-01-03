package com.agrotrading.kancher.moneytracker.rest;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.exceptions.UnauthorizedException;
import com.agrotrading.kancher.moneytracker.rest.model.UserBalanceModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserLogoutModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoriesModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoryModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserLoginModel;
import com.agrotrading.kancher.moneytracker.rest.model.UserRegistrationModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.CategoryData;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoryTransactionModel;
import com.agrotrading.kancher.moneytracker.rest.model.transaction.TransactionData;
import com.agrotrading.kancher.moneytracker.rest.model.transaction.UserTransactionModel;
import com.agrotrading.kancher.moneytracker.rest.model.transaction.UserTransactionsModel;
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

    public UserCategoryModel createCategory (String title) throws UnauthorizedException {
        return restClient.getCategoryApi().createCategory(title, MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoriesModel getAllCategories() throws UnauthorizedException {
        return restClient.getCategoryApi().getAllCategories(MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoriesModel synchCategories(ArrayList<CategoryData> data) throws UnauthorizedException {
        return restClient.getCategoryApi().synchCategories(data, MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoryModel editCategory(String title, Integer id) throws UnauthorizedException {
        return restClient.getCategoryApi().editCategory(title, id, MoneyTrackerApplication.getAuthToken());
    }

    public void deleteCategory(Integer id) throws UnauthorizedException {
        restClient.getCategoryApi().deleteCategory(id, MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoryTransactionModel getCategory(Integer id) throws UnauthorizedException {
        return restClient.getCategoryApi().getCategory(id, MoneyTrackerApplication.getAuthToken());
    }

    public UserCategoryTransactionModel getTransCat() throws UnauthorizedException {
        return restClient.getCategoryApi().getTransCat(MoneyTrackerApplication.getAuthToken());
    }

    public UserBalanceModel getBalance() throws UnauthorizedException {
        return restClient.getUserBalanceApi().getBalance(MoneyTrackerApplication.getAuthToken());
    }
    
    public UserBalanceModel setBalance(float balance) throws UnauthorizedException{
        return restClient.getUserBalanceApi().setBalance(balance, MoneyTrackerApplication.getAuthToken());
    }

    public UserTransactionsModel getAllTransactions() throws UnauthorizedException{
        return restClient.getUserTransactionApi().getAllTransactions(MoneyTrackerApplication.getAuthToken());
    }

    public UserTransactionsModel synchTransactions(ArrayList<TransactionData> data) throws UnauthorizedException{
        return restClient.getUserTransactionApi().synchTransactions(data, MoneyTrackerApplication.getAuthToken());
    }

    public UserTransactionModel addTransaction(int sum, String comment, int categoryId, String trDate) throws UnauthorizedException {
        return restClient.getUserTransactionApi().addTransaction(sum, comment, categoryId, trDate, MoneyTrackerApplication.getAuthToken());
    }

}
