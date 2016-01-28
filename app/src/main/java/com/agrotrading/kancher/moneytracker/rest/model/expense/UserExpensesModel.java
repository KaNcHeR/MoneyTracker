package com.agrotrading.kancher.moneytracker.rest.model.expense;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserExpensesModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<ExpenseData> data = new ArrayList<>();

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
    public List<ExpenseData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<ExpenseData> data) {
        this.data = data;
    }

}