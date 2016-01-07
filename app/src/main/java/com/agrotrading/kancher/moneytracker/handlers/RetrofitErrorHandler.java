package com.agrotrading.kancher.moneytracker.handlers;

import com.agrotrading.kancher.moneytracker.exceptions.UnauthorizedException;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RetrofitErrorHandler implements ErrorHandler{

    @Override
    public Throwable handleError(RetrofitError cause) {

        Response r = cause.getResponse();
        if (r != null && r.getStatus() == 401) {
            return new UnauthorizedException(cause);
        }
        return cause;
    }
}
