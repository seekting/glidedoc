package com.seekting.demo2016.activity;

import android.os.Bundle;
import android.util.Log;

import com.qihoo.mm.liba.LibSum;
import com.seekting.demo2016.AppEnv;

public class NDKActivity extends BaseActivity {
    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "NDKActivity";

    static {
        MainActivity.putTitle(NDKActivity.class, "NDK");
        MainActivity.putDesc(NDKActivity.class, "NDK");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int sum = LibSum.sum(1, 6);
        if (DEBUG) {
            Log.d(TAG, "onCreate.sum=" + sum);
        }


    }

}
