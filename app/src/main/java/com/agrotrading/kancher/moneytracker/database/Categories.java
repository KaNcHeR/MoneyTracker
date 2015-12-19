package com.agrotrading.kancher.moneytracker.database;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Categories")
public class Categories extends Model {

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
}
