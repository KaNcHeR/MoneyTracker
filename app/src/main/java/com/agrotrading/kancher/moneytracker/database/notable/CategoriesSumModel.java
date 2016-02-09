package com.agrotrading.kancher.moneytracker.database.notable;

public class CategoriesSumModel {

    private String name;
    private double sum;
    private int color;

    public CategoriesSumModel(String name, double sum, int color) {
        this.name = name;
        this.sum = sum;
        this.color = color;
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
