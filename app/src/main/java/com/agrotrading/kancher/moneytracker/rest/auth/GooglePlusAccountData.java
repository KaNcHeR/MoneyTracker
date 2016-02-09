package com.agrotrading.kancher.moneytracker.rest.auth;

import com.agrotrading.kancher.moneytracker.rest.model.GoogleTokenUserDataModel;

import org.androidannotations.annotations.EBean;

@EBean(scope = EBean.Scope.Singleton)
public class GooglePlusAccountData {
    GoogleTokenUserDataModel accountData;

    public GoogleTokenUserDataModel getAccountData() {
        return accountData;
    }

    public void setAccountData(GoogleTokenUserDataModel accountData) {
        this.accountData = accountData;
    }
}
