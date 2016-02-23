package com.agrotrading.kancher.moneytracker.utils;

import com.agrotrading.kancher.moneytracker.utils.event.MessageEvent;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import de.greenrobot.event.EventBus;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RetrofitEventBusBridge {

    public static void showEvent(RetrofitError error) {

        Response r = error.getResponse();
        if (r != null && r.getStatus() == 401) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.ALERT_UNAUTHORIZED));
            return;
        }

        if(error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            if (error.getCause() instanceof UnknownHostException) {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.ALERT_NO_INTERNET));
            } else if(error.getCause() instanceof SocketTimeoutException) {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.CONNECTION_TIMEOUT));
            } else {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.SERVER_NOT_RESPOND));
            }
        } else {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.SERVER_NOT_RESPOND));
        }

    }
}
