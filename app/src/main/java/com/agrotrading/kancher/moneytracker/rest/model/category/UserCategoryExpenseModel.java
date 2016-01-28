package com.agrotrading.kancher.moneytracker.rest.model.category;

import java.util.ArrayList;
import java.util.List;

import com.agrotrading.kancher.moneytracker.rest.model.expense.ExpenseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCategoryExpenseModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("transactions")
    @Expose
    private List<ExpenseData> transactions = new ArrayList<>();

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The transactions
     */
    public List<ExpenseData> getTransactions() {
        return transactions;
    }

    /**
     *
     * @param transactions
     * The transactions
     */
    public void setTransactions(List<ExpenseData> transactions) {
        this.transactions = transactions;
    }

}