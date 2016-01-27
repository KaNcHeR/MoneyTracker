package com.agrotrading.kancher.moneytracker.rest.model;

import com.google.gson.annotations.Expose;

public class GoogleTokenStatusModel {
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
