package com.agrotrading.kancher.moneytracker;

public class Expense {

    public String title;
    public int sum;

    public Expense(String title, int sum) {
        this.title = title;
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }
    public String getSumStr() {
        return Integer.toString(sum);
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
