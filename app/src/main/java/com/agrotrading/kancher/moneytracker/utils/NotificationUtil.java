package com.agrotrading.kancher.moneytracker.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.NotificationCompat;

import com.agrotrading.kancher.moneytracker.R;
import com.agrotrading.kancher.moneytracker.ui.activities.MainActivity;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EBean
public class NotificationUtil {

    @RootContext
    Context context;

    @Pref
    NotificationsPreferences_ prefs;

    @StringRes(R.string.pref_enable_notifications_key)
    String displayNotificationsKey;

    @StringRes(R.string.pref_enable_sound_key)
    String soundNotificationsKey;

    @StringRes(R.string.pref_enable_vibrate_key)
    String vibrateNotificationsKey;

    @StringRes(R.string.pref_enable_indicator_key)
    String indicatorNotificationsKey;

    private static final int NOTIFICATION_ID = 4004;

    public void updateNotifications() {

        if (!prefs.displayNotifications().get()) return;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent intent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        String title = context.getString(R.string.app_name);
        String contentText = context.getResources().getString(R.string.notification_message);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);

        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        builder.setContentIntent(intent);
        builder.setSmallIcon(R.mipmap.ic_logo_status_bar);

        if(prefs.indicatorNotifications().get()) builder.setLights(Color.BLUE, 300, 1500);
        if(prefs.vibrateNotifications().get()) builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        if(prefs.soundNotifications().get()) builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        builder.setAutoCancel(true);
        builder.setLargeIcon(largeIcon);
        builder.setContentTitle(title);
        builder.setContentText(contentText);

        notification = builder.build();
        nm.notify(NOTIFICATION_ID, notification);
    }
}
