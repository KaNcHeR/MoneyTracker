package com.agrotrading.kancher.moneytracker;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {

    private String title;
    private int sum;
    private Date date;

    public Expense(String title, int sum, Date date) {
        this.title = title;
        this.sum = sum;
        this.date = date;
    }

    public Expense() {}

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

    public Date getDate() {
        return date;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDateStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
