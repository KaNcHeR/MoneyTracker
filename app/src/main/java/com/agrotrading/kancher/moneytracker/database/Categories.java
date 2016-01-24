package com.agrotrading.kancher.moneytracker.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Categories")
public class Categories extends Model {

    @Column(name = "_id")
    private long sId = 0;

    @Column(name = "name")
    private String name;

    @Column(name = "sync")
    public boolean sync = false;

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

    public static List<Categories> getAllCategoriesOrderById() {
        return new Select()
                .from(Categories.class)
                .orderBy("id ASC")
                .execute();
    }

    public long getsId() {
        return sId;
    }

    public void setsId(long sId) {
        this.sId = sId;
    }
}
