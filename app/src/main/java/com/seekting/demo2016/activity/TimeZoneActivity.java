package com.seekting.demo2016.activity;

import android.os.Bundle;
import android.util.Log;

import com.seekting.demo2016.AppEnv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeZoneActivity extends BaseActivity {
    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "TimeZoneActivity";

    static {
        MainActivity.putTitle(TimeZoneActivity.class, "");
        MainActivity.putDesc(TimeZoneActivity.class, "");
    }

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printTime("GMT-00:31");
        printTime("GMT+00:00");
        printTime("GMT+01:00");
        printTime("GMT+02:00");
        printTime("GMT+03:00");
        printTime("GMT+04:00");
        printTime("GMT+05:00");
        printTime("GMT+06:00");
        printTime("GMT+07:00");
        printTime("GMT+08:00");
        printTime("GMT+08:30");
        printTime("GMT+08:31");


    }

    private void printTime(String timeZoneName) {
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneName);
        SIMPLE_DATE_FORMAT.setTimeZone(timeZone);
        if (DEBUG) {
            Log.d(TAG, "TimeZoneActivity.onCreate()" + SIMPLE_DATE_FORMAT.format(new Date()));
        }
    }

}
