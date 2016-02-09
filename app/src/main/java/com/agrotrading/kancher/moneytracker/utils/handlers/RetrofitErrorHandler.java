package com.agrotrading.kancher.moneytracker.utils.handlers;

import com.agrotrading.kancher.moneytracker.utils.exceptions.UnauthorizedException;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RetrofitErrorHandler implements ErrorHandler{

    @Override
    public Throwable handleError(RetrofitError cause) {

        Response r = cause.getResponse();
        if (r != null) {
            switch (r.getStatus()) {
                case 401:
                    return new UnauthorizedException(cause);
            }
        }
        return cause;
    }
}
