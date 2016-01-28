package com.agrotrading.kancher.moneytracker.event;

public class MessageEvent {

    public static final int MOVE_USER_TO_LOGIN = 0;

    public final int code;

    public MessageEvent(int code) {
        this.code = code;
    }
}
