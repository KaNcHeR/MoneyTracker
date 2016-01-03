package com.agrotrading.kancher.moneytracker.rest.model.transaction;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserTransactionsModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<TransactionData> data = new ArrayList<TransactionData>();

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The data
     */
    public List<TransactionData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<TransactionData> data) {
        this.data = data;
    }

}