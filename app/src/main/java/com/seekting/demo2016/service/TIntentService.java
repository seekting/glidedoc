package com.seekting.demo2016.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.seekting.demo2016.AppEnv;

/**
 * Created by seekting on 17-1-12.
 */

public class TIntentService extends IntentService {
    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "TIntentService";


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public TIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (DEBUG) {
            Log.d(TAG, "onCreate.");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (DEBUG) {
            Log.d(TAG, "onStartCommand.");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (DEBUG) {
            Log.i(TAG, "onHandleIntent." + intent);
            Log.i(TAG, Thread.currentThread().getName());
            SystemClock.sleep(3000);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) {
            Log.i(TAG, "onDestroy.");
        }
    }

}
