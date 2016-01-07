package com.agrotrading.kancher.moneytracker.exceptions;

public class UnauthorizedException extends Exception {

    public UnauthorizedException(Throwable e) {
        initCause(e);
    }

}
