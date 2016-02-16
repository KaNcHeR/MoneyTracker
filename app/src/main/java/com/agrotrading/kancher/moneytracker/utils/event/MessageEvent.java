package com.agrotrading.kancher.moneytracker.utils.event;

public class MessageEvent {

    public static final int MOVE_USER_TO_LOGIN = 0;
    public static final int ALERT_NO_INTERNET = 1;
    public static final int CONNECTION_TIMEOUT = 2;
    public static final int SERVER_NOT_RESPOND = 3;
    public static final int ALERT_UNAUTHORIZED = 4;

    public final int code;

    public MessageEvent(int code) {
        this.code = code;
    }
}
