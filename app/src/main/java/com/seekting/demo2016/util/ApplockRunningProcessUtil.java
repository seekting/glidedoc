package com.seekting.demo2016.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build;

import java.util.List;

public class ApplockRunningProcessUtil {

    public static boolean isRunningAppProcessesEnable(Context context) {
        if (Build.VERSION.SDK_INT < 21) {
            return true;
        }
        Context mApp = context.getApplicationContext();
        ActivityManager mAm = (ActivityManager) mApp.getSystemService(Context.ACTIVITY_SERVICE);
        try {
            List<RunningAppProcessInfo> list = mAm.getRunningAppProcesses();
            return isRunningAppProcessesEnable(list);
        } catch (Exception e) {

        }
        return false;
    }

    public static boolean isRunningAppProcessesEnable(List<RunningAppProcessInfo> list) {
        if (Build.VERSION.SDK_INT < 21) {
            return true;
        }
        if (list != null) {
            int myUid = android.os.Process.myUid();
            for (RunningAppProcessInfo item : list) {
                if (item != null && item.uid != myUid) {
                    return true;
                }
            }
        }
        return false;
    }

}
