package com.agrotrading.kancher.moneytracker.database;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.agrotrading.kancher.moneytracker.exceptions.UnauthorizedException;
import com.agrotrading.kancher.moneytracker.rest.RestService;
import com.agrotrading.kancher.moneytracker.rest.model.category.CreateCategoryModel;
import com.agrotrading.kancher.moneytracker.rest.model.category.Data;

import java.util.List;

@Table(name = "Categories")
public class Categories extends Model {

    private static final String LOG_TAG = Categories.class.getSimpleName();

    @Column(name = "name")
    public String name;

    public Categories() {
        super();
    }

    public Categories(String name) {
        super();
        this.name = name;
    }

    public List<Expenses> expenses() {
        return getMany(Expenses.class, "category");
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<Categories> getAllCategories() {
        return new Select()
                .from(Categories.class)
                .orderBy("Name ASC")
                .execute();
    }

    public Long saveAndRest() throws UnauthorizedException {

        RestService restService = new RestService();
        CreateCategoryModel createCategory = restService.createCategory(name);
        Data data = createCategory.getData();
        Log.d(LOG_TAG, "Status: " + createCategory.getStatus() + ", title: " + data.getTitle() + ", id: " + data.getId());
        return save();

    }


}
