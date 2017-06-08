package com.seekting.demo2016.util;

import android.widget.Toast;

import com.seekting.demo2016.App;

/**
 * Created by seekting on 2015/12/30.
 */
public class ToastUtil {

    public static void showToast(String msg) {
        Toast.makeText(App.getApp(), msg, Toast.LENGTH_LONG).show();
    }
}
