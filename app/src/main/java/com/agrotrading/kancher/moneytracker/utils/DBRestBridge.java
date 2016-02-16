package com.agrotrading.kancher.moneytracker.utils;

import android.accounts.Account;
import android.content.Context;

import com.agrotrading.kancher.moneytracker.MoneyTrackerApplication;
import com.agrotrading.kancher.moneytracker.database.Categories;
import com.agrotrading.kancher.moneytracker.database.Expenses;
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
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.SupposeBackground;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit.RetrofitError;

@EBean
public class DBRestBridge {

    private RestService restService = new RestService();
    private String gToken;
    private Gson gson = new Gson();

    @Pref
    ApplicationPreferences_ prefs;

    @Bean
    NotificationUtil notificationUtil;

    @RootContext
    Context context;

    public void firstSync() {
        List<CategoryData> categories;
        List<ExpenseData> expenses;

        gToken = MoneyTrackerApplication.getGoogleToken(context);
        categories = restService.getAllCategories(gToken).getData();
        expenses = restService.getAllExpenses(gToken).getData();

        for(CategoryData category : categories) {
            Categories newCategory = new Categories(category.getTitle(), category.getId());
            newCategory.save();

            Iterator<ExpenseData> iterator = expenses.iterator();

            while (iterator.hasNext()) {
                ExpenseData expense = iterator.next();
                if(expense.getCategoryId() == category.getId()) {
                    new Expenses(
                            expense.getId(),
                            expense.getComment(),
                            expense.getSum(),
                            expense.getTrDate(),
                            newCategory).save();
                    iterator.remove();
                }
            }
        }
    }

    @Background(serial = "sync")
    public void initSync() {

        gToken = MoneyTrackerApplication.getGoogleToken(context);

        if (!gToken.equalsIgnoreCase(ConstantManager.DEFAULT_GOOGLE_TOKEN)) {

            try {
                GoogleTokenStatusModel statusModel = restService.getGoogleTokenStatus(gToken);
                if (statusModel.getStatus().equalsIgnoreCase(ConstantManager.STATUS_ERROR)) {
                    getGToken();
                }
            } catch (RetrofitError error) {
                RetrofitEventBusBridge.showEvent(error);
                return;
            }
        }
        startSync();
    }



    @SupposeBackground
    void startSync() {
        boolean syncCategories = startSyncCategories();
        boolean syncExpenses = startSyncExpenses();

        if(syncCategories || syncExpenses) {
            notificationUtil.updateNotifications();
        }
    }

    @SupposeBackground
    void getGToken() {
        Account account = new Account(prefs.googleAccountEmail().get(), ConstantManager.GOOGLE_ACCOUNT_TYPE);

        try {
            gToken = GoogleAuthUtil.getToken(context, account, ConstantManager.SCOPES);
            MoneyTrackerApplication.setGoogleToken(context, gToken);
        } catch (IOException | GoogleAuthException ioEx) {
            ioEx.printStackTrace();
        }
    }

    private String getCategoriesDataJson(List<Categories> categories) {
        ArrayList<String> data = new ArrayList<>();
        CategoryData categoryData = new CategoryData();

        if(categories.size() == 0) return "{}";

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

        if(expenses.size() == 0) return "{}";

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

    @SupposeBackground
    boolean startSyncCategories() {
        List<Categories> categories = Categories.getAllCategoriesOrderById();
        String jsonRequest = getCategoriesDataJson(categories);

        if (!prefs.needSyncCategories().get()) return false;

        try {
            UserCategoriesModel syncCategories = restService.syncCategories(jsonRequest, gToken);
            List<CategoryData> syncCategoriesData;

            if (!syncCategories.getStatus().equals(ConstantManager.STATUS_SUCCESS)) return false;

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

        } catch (RetrofitError error) {
            RetrofitEventBusBridge.showEvent(error);
        }

        return true;
    }

    @SupposeBackground
    boolean startSyncExpenses(){
        List<Expenses> expenses = Expenses.getAllExpensesOrderById();
        String jsonRequest = getExpensesDataJson(expenses);

        if(prefs.needSyncCategories().get() || !prefs.needSyncExpenses().get()) return false;

        try {
            UserExpensesModel syncExpenses = restService.syncExpenses(jsonRequest, gToken);
            List<ExpenseData> syncExpensesData;

            if(!syncExpenses.getStatus().equals(ConstantManager.STATUS_SUCCESS)) return false;

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

        } catch (RetrofitError error) {
            RetrofitEventBusBridge.showEvent(error);
        }

        return true;
    }
}
