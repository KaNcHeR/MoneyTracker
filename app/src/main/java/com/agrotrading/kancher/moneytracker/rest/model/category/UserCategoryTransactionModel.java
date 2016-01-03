package com.agrotrading.kancher.moneytracker.rest.model.category;

import java.util.ArrayList;
import java.util.List;

import com.agrotrading.kancher.moneytracker.rest.model.transaction.TransactionData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCategoryTransactionModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("transactions")
    @Expose
    private List<TransactionData> transactions = new ArrayList<TransactionData>();

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
    public List<TransactionData> getTransactions() {
        return transactions;
    }

    /**
     *
     * @param transactions
     * The transactions
     */
    public void setTransactions(List<TransactionData> transactions) {
        this.transactions = transactions;
    }

}