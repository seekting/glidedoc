package com.seekting.demo2016.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

public class NetSpeed {
    private final static String TAG = "NetSpeed";
    private long preRxBytes = 0;
    private long preTxBytes = 0;
    private Timer mTimer = null;
    private Context mContext;
    private Handler mHandler;
    private static final long INTERVAL_TIME = 3;
    /**
     * [Rx,Tx]
     */
    private float mSpeedOut[] = new float[2];

    public NetSpeed(Context mContext, Handler mHandler) {
        this.mContext = mContext;
        this.mHandler = mHandler;
    }

    public static NetSpeed create(Context mContext, Handler mHandler) {
        return new NetSpeed(mContext, mHandler);
    }


    public float getRxSpeed() {
        long curRxBytes = TrafficStats.getTotalRxBytes();
        long bytes = curRxBytes - preRxBytes;
        preRxBytes = curRxBytes;
        return bytes / (float) INTERVAL_TIME;
    }

    public float getTxSpeed() {
        long curTxBytes = TrafficStats.getTotalTxBytes();
        long bytes = curTxBytes - preTxBytes;
        preTxBytes = curTxBytes;
        return bytes / (float) INTERVAL_TIME;
    }

    public void startCalculateNetSpeed() {
        preRxBytes = TrafficStats.getTotalRxBytes();
        preTxBytes = TrafficStats.getTotalTxBytes();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = 1;
                    mSpeedOut[0] = getRxSpeed();
                    mSpeedOut[1] = getTxSpeed();
                    msg.obj = mSpeedOut;
                    mHandler.sendMessage(msg);
                }
            }, 0, INTERVAL_TIME * 1000);
        }
    }

    public void stopCalculateNetSpeed() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private int getUid() {
        try {
            PackageManager pm = mContext.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(
                    mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            return ai.uid;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
