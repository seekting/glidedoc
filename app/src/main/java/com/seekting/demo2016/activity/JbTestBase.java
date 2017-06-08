package com.seekting.demo2016.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;


/**
 * Created by seekting on 17-1-20.
 */

public class JbTestBase extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        }, 1000);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("seekting", "onDestroy.");
    }
}
