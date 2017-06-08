package com.seekting.demo2016.service;


import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by seekting on 16-11-9.
 */

public class MyInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("FCM", "onTokenRefresh");
    }

    @Override
    public void onTokenRefresh() {
        Log.d("FCM", "onTokenRefresh");
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
