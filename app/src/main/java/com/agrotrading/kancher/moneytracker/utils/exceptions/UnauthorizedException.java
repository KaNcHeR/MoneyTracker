package com.agrotrading.kancher.moneytracker.utils.exceptions;

public class UnauthorizedException extends Exception {

    public UnauthorizedException(Throwable e) {
        initCause(e);
    }

}
