package com.agrotrading.kancher.moneytracker.rest.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCategoryModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private CategoryData data;

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
    public CategoryData getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(CategoryData data) {
        this.data = data;
    }

}


