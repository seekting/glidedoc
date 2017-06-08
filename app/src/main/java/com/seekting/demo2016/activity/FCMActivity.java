package com.seekting.demo2016.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.seekting.demo2016.service.RegistrationIntentService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by seekting on 16-11-9.
 */

public class FCMActivity extends Activity {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static void saveToken(String token, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FCM", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString("token", token);
        edit.apply();
    }

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("FCM", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        return token;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String token = getToken(this);
        if (token == null) {
            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }

        Button btn = new Button(this);
        btn.setText("发消息");
        setContentView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request();
                    }
                }).start();
            }
        });

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("FCM", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private void request() {
//        String to = "ePx0ZCpNYEc:APA91bF113MYF5Y-bUXT2XzQ820xTsXTDMSNfYWdhZpk-peThRVqkvwyvH-r8RNObtBpJxGWoyPGtWsiy4uCK6mL5TneMwVsA3h4aK3veCfLuFg_FlZcktBCr_pxj8taGFhWz4LbvGI-";
        String to = getToken(this);
        try {
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.addRequestProperty("Content-Type", "application/json");
            urlConnection.addRequestProperty("Authorization", "key=AIzaSyAU1P5uVW4k4R6rscQCS_Tsl4If9zHYaX8");
//            Content-Type:application/json
            OutputStream outputStream = urlConnection.getOutputStream();
            JSONObject jsonObject = new JSONObject();
            JSONObject data = new JSONObject();
            data.put("score", "5x1");
            data.put("time", "15:10");
            data.put("message", "15:10");
            jsonObject.put("data", data);
            jsonObject.put("to", to);
            outputStream.write(jsonObject.toString().getBytes());
            int code = urlConnection.getResponseCode();
            Log.d("FCM", "code=" + code);
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader stringBuffer = new BufferedReader(new InputStreamReader(inputStream));

            while (true) {
                String line = stringBuffer.readLine();
                if (line == null) {
                    break;
                }
                Log.d("FCM", line);

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
