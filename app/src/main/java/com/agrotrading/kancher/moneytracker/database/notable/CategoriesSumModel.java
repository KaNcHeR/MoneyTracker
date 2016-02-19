package com.agrotrading.kancher.moneytracker.database.notable;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.query.From;

import java.util.ArrayList;
import java.util.List;

public class CategoriesSumModel {

    private String name;
    private double sum;
    private int color;

    public CategoriesSumModel() { super();
    }

    public CategoriesSumModel(String name, double sum, int color) {
        this.name = name;
        this.sum = sum;
        this.color = color;
    }

    public static List<CategoriesSumModel> formModelList(From query) {
        List<CategoriesSumModel> categoriesSum = new ArrayList<>();

        Cursor cursor = Cache.openDatabase().rawQuery(query.toSql(), query.getArguments());
        if(cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                double sum = cursor.getDouble(cursor.getColumnIndex("sum"));
                int color = cursor.getInt(cursor.getColumnIndex("color"));

                categoriesSum.add(new CategoriesSumModel(name, sum, color));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return categoriesSum;
    }

    public String getName() {
        return name;
    }

    public double getSum() {
        return sum;
    }

    public int getColor() {
        return color;
    }
}
