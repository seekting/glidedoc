package com.seekting.demo2016.service;

import android.app.IntentService;
import android.content.Intent;
import android.telecom.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.seekting.demo2016.R;
import com.seekting.demo2016.activity.FCMActivity;

import java.io.IOException;

/**
 * Created by seekting on 16-11-9.
 */

public class RegistrationIntentService extends IntentService {

    public RegistrationIntentService() {
        super("RegistrationIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        Log.d("FCM", "onHandleIntent");
        try {
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            FCMActivity.saveToken(token, this);
            subscribeTopics(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        Log.d("FCM", "token=" + token);


//        for (String topic : TOPICS) {
//            pubSub.subscribe(token, "/topics/" + topic, null);
//        }
    }

}
