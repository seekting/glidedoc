package com.seekting.demo2016;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static com.android.internal.R.id.body;

//http://blog.csdn.net/beifengdelei/article/details/7861395
public class App extends Application {

    private static App app;

    private String processName;
    private ActivityManager activityManager;
    private WindowManager windowManager;
    private ConnectivityManager connectionManager;

    private PowerManager powerManager;
    private WifiManager wifiManager;
    private TelephonyManager telephonyManager;

    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        processName = getCurProcessName(this);
        loadManagers();
//        daemonNotification = new DaemonNotification(this);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(257, daemonNotification.getNotification());

        testUrl();

    }


    private void testUrl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

//                    URL url = new URL("http://www.baidu.com");
                    URL url = new URL("http://testweather.i.360overseas.com/index/locationCity");
//                    URL url = new URL("http://52.76.143.107/index/locationCity");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    String bodyContentType = "application/x-www-form-urlencoded; charset=UTF-8";
                    con.addRequestProperty("Content-Type", bodyContentType);
                    con.addRequestProperty("APPINFO", "com.qihoo.mm.weather|3000|en|17|US");
                    DataOutputStream out = new DataOutputStream(con.getOutputStream());
                    out.write(body);
                    out.close();
                    InputStream intput = con.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    while (true) {
                        int length = intput.read(bytes);
                        if (length <= 0) {
                            break;
                        }
                        byteArrayOutputStream.write(bytes, 0, length);
                    }
                    Log.d("xxxx", new String(byteArrayOutputStream.toByteArray()));
                    byteArrayOutputStream.close();
                    intput.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public String getProcessName() {
        return processName;
    }

    private void loadManagers() {

        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

    }

    private static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }

    public static App getApp() {
        return app;
    }

    public ConnectivityManager getConnectionManager() {
        return connectionManager;
    }

    public WindowManager getWindowManager() {
        return windowManager;
    }

    public PowerManager getPowerManager() {
        return powerManager;
    }

    public WifiManager getWifiManager() {
        return wifiManager;
    }

    public TelephonyManager getTelephonyManager() {
        return telephonyManager;
    }
}
