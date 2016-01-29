package com.agrotrading.kancher.moneytracker.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.database.Expenses;
import com.agrotrading.kancher.moneytracker.event.MessageEvent;
import com.agrotrading.kancher.moneytracker.exceptions.UnauthorizedException;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenStatusModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.CategoryData;
import com.agrotrading.kancher.moneytracker.rest.model.category.UserCategoriesModel;
import com.agrotrading.kancher.moneytracker.rest.model.expense.ExpenseData;
import com.agrotrading.kancher.moneytracker.rest.model.expense.UserExpensesModel;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.gson.Gson;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EBean
public class DbToRestBridge {

    @Pref
    ApplicationPreferences_ prefs;

    private Context context;

    private RestService restService;

    private String gToken;

    private Gson gson = new Gson();

    public DbToRestBridge(Context context) {
        this.context = context;
        restService = new RestService();
        gToken = MoneyTrackerApplication.getGoogleToken(context);
    }

    public void initSync() {

        if (!gToken.equalsIgnoreCase(ConstantManager.DEFAULT_GOOGLE_TOKEN)) {
            restService.getGoogleTokenStatus(gToken, new Callback<GoogleTokenStatusModel>() {
                @Override
                public void success(GoogleTokenStatusModel googleTokenStatusModel, Response response) {

                    if (googleTokenStatusModel.getStatus().equalsIgnoreCase(ConstantManager.STATUS_ERROR)) {
                        getGToken();
                    } else {
                        startSync();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    getGToken();
                }

            });

            return;
        }

        startSync();
    }

    private void startSync() {
        startSyncCategories();
        startSyncExpenses();
    }

    @Background
    void getGToken() {

        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account = accounts[0];

        if(account == null) return;

        try {
            gToken = GoogleAuthUtil.getToken(context, account, ConstantManager.SCOPES);
            MoneyTrackerApplication.setGoogleToken(context, gToken);
            startSyncCategories();
            startSyncExpenses();
        } catch (IOException | GoogleAuthException ioEx) {
            ioEx.printStackTrace();
        }

    }

    private String getCategoriesDataJson(List<Categories> categories) {

        ArrayList<String> data = new ArrayList<>();
        CategoryData categoryData = new CategoryData();

        if(categories.size() == 0) return null;

        for(Categories category : categories) {
            categoryData.setId(category.getsId());
            categoryData.setTitle(category.toString());
            data.add(gson.toJson(categoryData));
        }

        return data.toString();
    }

    private String getExpensesDataJson(List<Expenses> expenses) {
        ArrayList<String> data = new ArrayList<>();
        ExpenseData expenseData = new ExpenseData();

        if(expenses.size() == 0) return null;

        for(Expenses expense : expenses) {
            expenseData.setId(expense.getId());
            expenseData.setCategoryId(expense.getCategoryId());
            expenseData.setComment(expense.getName());
            expenseData.setSum(expense.getPrice());
            expenseData.setTrDate(expense.getDate());
            data.add(gson.toJson(expenseData));
        }

        return data.toString();
    }

    private void startSyncCategories() {

        List<Categories> categories = Categories.getAllCategoriesOrderById();
        String jsonRequest = getCategoriesDataJson(categories);

        if (jsonRequest == null || !prefs.needSyncCategories().get()) return;

        try {
            UserCategoriesModel syncCategories = restService.syncCategories(jsonRequest, gToken);
            List<CategoryData> syncCategoriesData;

            if (!syncCategories.getStatus().equals(ConstantManager.STATUS_SUCCESS)) return;

            syncCategoriesData = syncCategories.getData();
            for (int i = 0; i < categories.size(); i++) {
                Categories category = categories.get(i);
                CategoryData syncCategory = syncCategoriesData.get(i);
                if (category.getsId() != syncCategory.getId()) {
                    category.setsId(syncCategory.getId());
                    category.save();
                }
            }

            prefs.needSyncCategories().put(false);

        } catch (UnauthorizedException e) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.MOVE_USER_TO_LOGIN));
        }
    }

    private void startSyncExpenses(){

        List<Expenses> expenses = Expenses.getAllExpensesOrderById();
        String jsonRequest = getExpensesDataJson(expenses);

        if(jsonRequest == null || prefs.needSyncCategories().get() || !prefs.needSyncExpenses().get()) return;

        try {
            UserExpensesModel syncExpenses = restService.syncExpenses(jsonRequest, gToken);
            List<ExpenseData> syncExpensesData;

            if(!syncExpenses.getStatus().equals(ConstantManager.STATUS_SUCCESS)) return;

            syncExpensesData = syncExpenses.getData();
            for (int i = 0; i < expenses.size(); i++) {
                Expenses expense = expenses.get(i);
                ExpenseData syncExpense = syncExpensesData.get(i);
                if (expense.getsId() != syncExpense.getId()) {
                    expense.setsId(syncExpense.getId());
                    expense.save();
                }
            }

            prefs.needSyncExpenses().put(false);

        } catch (UnauthorizedException e) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.MOVE_USER_TO_LOGIN));
        } catch (GoogleAuthException e) {
            e.printStackTrace();
        }
    }

}
