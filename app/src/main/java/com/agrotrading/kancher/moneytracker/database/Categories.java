package com.agrotrading.kancher.moneytracker.database;

import android.database.Cursor;
import android.graphics.Color;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.agrotrading.kancher.moneytracker.database.notable.CategoriesSumModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Table(name = "Categories")
public class Categories extends Model {

    @Column(name = "_id")
    private long sId = 0;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private int color = getRandomColor();

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

    public static List<Categories> getAllCategories(String filter) {
        return new Select()
                .from(Categories.class)
                .where("Name LIKE ?", new String[]{'%' + filter + '%'})
                .orderBy("Name ASC")
                .execute();
    }

    public static List<Categories> getAllCategoriesOrderById() {
        return new Select()
                .from(Categories.class)
                .orderBy("id ASC")
                .execute();
    }

    public static double inTotal() {

        double inTotal = 0;

        From query =  new Select(new String[]{"SUM(Price) AS inTotal"})
                .from(Expenses.class);

        Cursor cursor = Cache.openDatabase().rawQuery(query.toSql(), query.getArguments());

        if(cursor.moveToFirst()) {
            inTotal = cursor.getDouble(cursor.getColumnIndex("inTotal"));
        }
        cursor.close();

        return inTotal;
    }

    public static List<CategoriesSumModel> getCategoryWithSum() {

        List<CategoriesSumModel> categoriesSum = new ArrayList<>();

        From query =  new Select(new String[]{"Categories.Name as name, SUM(Expenses.Price) AS sum, Categories.Color as color"})
                .from(Expenses.class)
                .groupBy("Category")
                .leftJoin(Categories.class)
                .on("Categories.Id = Expenses.Category");

        Cursor cursor = Cache.openDatabase().rawQuery(query.toSql(), query.getArguments());

        if(cursor.moveToFirst()) {
            do {
                CategoriesSumModel categorySum;

                String name = cursor.getString(cursor.getColumnIndex("name"));
                double sum = cursor.getLong(cursor.getColumnIndex("sum"));
                int color = cursor.getInt(cursor.getColumnIndex("color"));

                categorySum = new CategoriesSumModel(name, sum, color);
                categoriesSum.add(categorySum);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return categoriesSum;

    }

    private int getRandomColor() {

        Random random = new Random();
        return Color.argb(128, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public long getsId() {
        return sId;
    }

    public void setsId(long sId) {
        this.sId = sId;
    }
}
