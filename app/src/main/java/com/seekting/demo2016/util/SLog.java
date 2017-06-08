package com.seekting.demo2016.util;

import android.util.Log;

import com.seekting.demo2016.App;


public class SLog {

    private static String TAG = "SLOG";

    static {
        if (App.getApp().getProcessName().endsWith("ui")) {
            TAG = TAG + ":ui";
        }
    }

    public static void d(Object o) {
        if (o == null) {
            return;
        }

        Log.d(TAG, o.toString());
    }

    public static void e(Object o) {
        if (o == null) {
            return;
        }
        Log.e(TAG, o.toString());
    }

    public static void d(String tag, Object o) {
        if (o == null) {
            return;
        }
        Log.d(TAG + ":" + tag, o.toString());

    }

    public static void d(String format, Object... objects) {
        String str = String.format(format, objects);
        d(str);
    }

    public static void e(String tag, Object o) {
        if (o == null) {
            return;
        }
        Log.e(TAG + ":" + tag, o.toString());
    }
}
