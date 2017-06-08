package com.seekting.demo2016.service;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by seekting on 16-11-9.
 */

public class MyGcmListenerService extends GcmListenerService {
    private static final String TAG = "MyGcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d("FCM", "From: " + from);
        Log.d("FCM", "Message: " + message);

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // ...
    }
}
