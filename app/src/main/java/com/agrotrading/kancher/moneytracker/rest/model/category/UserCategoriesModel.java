package com.agrotrading.kancher.moneytracker.rest.model.category;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCategoriesModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<CategoryData> data = new ArrayList<CategoryData>();

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
    public List<CategoryData> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<CategoryData> data) {
        this.data = data;
    }

}