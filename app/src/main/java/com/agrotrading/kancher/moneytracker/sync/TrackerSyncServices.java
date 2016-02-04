package com.agrotrading.kancher.moneytracker.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class TrackerSyncServices extends Service {

    private final static Object sSyncAdapterLock = new Object();
    private static TrackerSyncAdapter sTrackerSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.e("TrackerSyncServices", "onCreate");
        synchronized (sSyncAdapterLock) {
            if(sTrackerSyncAdapter == null) {
                Log.e("TrackerSyncServices", "sTrackerSyncAdapter == null");
                sTrackerSyncAdapter = new TrackerSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("TrackerSyncServices", "onBind");
        return sTrackerSyncAdapter.getSyncAdapterBinder();
    }
}
