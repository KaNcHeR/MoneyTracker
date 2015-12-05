package com.agrotrading.kancher.moneytracker;

public class Expense {

    public String title;
    public String sum;

    public Expense(String title, String sum) {
        this.title = title;
        this.sum = sum;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
