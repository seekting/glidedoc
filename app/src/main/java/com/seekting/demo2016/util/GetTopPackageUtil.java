package com.seekting.demo2016.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.usage.UsageEvents;
import android.app.usage.UsageEvents.Event;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@SuppressLint("NewApi")
public class GetTopPackageUtil {
    private static final boolean DEBUG = true;
    private static final String TAG = DEBUG ? "GetTopPackageUtil" : GetTopPackageUtil.class.getSimpleName();
    public static final int USAGE_INTERVAL_TIME = 10000;
    private boolean mRunningAppProcessesEnable = true;
    private Context mContext;

    public GetTopPackageUtil(Context context) {
        mContext = context.getApplicationContext();
        mRunningAppProcessesEnable = ApplockRunningProcessUtil.isRunningAppProcessesEnable(mContext);
    }

    /**
     * 获得栈顶的包名
     *
     * @return
     */
    public String getTopPackageName() {
        try {
            if (Build.VERSION.SDK_INT < 21) {
                return getRunningTasksTopPackage();
            } else {
                if (mRunningAppProcessesEnable) {
                    return getRunningAppProcessesTopPackage();
                } else {
                    return getUsageStatsTopPackage();
                }
            }
        } catch (Exception e) {
            if (DEBUG) {
                Log.e(TAG, "getTopPackageName", e);
            }
        }

        return "";
    }

    @SuppressWarnings("deprecation")
    private String getRunningTasksTopPackage() throws Exception {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> taskList = null;
        try {
            taskList = activityManager.getRunningTasks(1);
        } catch (Exception e) {
        }
        if (taskList != null && !taskList.isEmpty()) {
            String topAppPkg = taskList.get(0).topActivity.getPackageName();
            return topAppPkg;
        }
        return "";
    }

    // http://stackoverflow.com/questions/24625936/getrunningtasks-doesnt-work-in-android-l/27140347#27140347
    private String getRunningAppProcessesTopPackage() throws Exception {
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> pis = activityManager.getRunningAppProcesses();
        if (pis != null && pis.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo pi : pis) {
                if (pi.pkgList.length > 0) {
                    String pkg = pi.pkgList[0];
                    if (isForegroundProccess(pi)) {
                        return pkg;
                    } else {
                        if (DEBUG) {
                            Log.e(TAG, "pkg==" + pkg + " 没有界面忽略");
                        }
                    }
                }
            }
        }
        return "";
    }

    private boolean isForegroundProccess(ActivityManager.RunningAppProcessInfo processInfo) {
        if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            return true;
        } else {
            return false;
        }
    }

    //    //API 21 and above
    //    //http://stackoverflow.com/questions/24625936/getrunningtasks-doesnt-work-in-android-l/28277427#28277427
    //    private String getUsageStatsTopPackage11() throws Exception {
    //        String topPackageName = null;
    //        UsageStatsManager usage = (UsageStatsManager) mContext.getSystemService("usagestats");
    //        long time = System.currentTimeMillis();
    //        List<UsageStats> stats = usage.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - USAGE_INTERVAL_TIME,
    //                time);
    //        if (stats != null) {
    //            SortedMap<Long, UsageStats> runningTask = new TreeMap<Long, UsageStats>();
    //            for (UsageStats usageStats : stats) {
    //                runningTask.put(usageStats.getLastTimeUsed(), usageStats);
    //            }
    //            if (runningTask.isEmpty()) {
    //                if (DEBUG) {
    //                    Log.e(TAG, "getProcessNew runningTask isEmpty");
    //                }
    //                return null;
    //            }
    //            topPackageName = runningTask.get(runningTask.lastKey()).getPackageName();
    //        } else {
    //            if (DEBUG) {
    //                Log.e(TAG, "getProcessNew 没有数据");
    //            }
    //        }
    //        return topPackageName;
    //    }

    //API 21 and above
    //http://stackoverflow.com/questions/24625936/getrunningtasks-doesnt-work-in-android-l/28277427#28277427
    private String getUsageStatsTopPackage() throws Exception {
        String topPackageName = "";
        UsageStatsManager usage = (UsageStatsManager) mContext.getSystemService("usagestats");
        long time = System.currentTimeMillis();
        UsageEvents uEvents = usage.queryEvents(time - USAGE_INTERVAL_TIME, time);
        if (uEvents != null) {
            SortedMap<Long, Event> runningTask = new TreeMap<Long, Event>();
            try {
                while (uEvents.hasNextEvent()) {
                    UsageEvents.Event e = new UsageEvents.Event();
                    uEvents.getNextEvent(e);
                    runningTask.put(e.getTimeStamp(), e);
                }
            } catch (Exception e) {
                if (DEBUG) {
                    Log.e(TAG, "getUsageStatsTopPackage while ", e);
                }
            }

            if (runningTask.isEmpty()) {
                if (DEBUG) {
                    Log.e(TAG, "getProcessNew runningTask isEmpty");
                }
                return null;
            }
            try {
                Event event = runningTask.get(runningTask.lastKey());
                if (event.getEventType() == Event.MOVE_TO_FOREGROUND) {
                    topPackageName = event.getPackageName();
                    if (DEBUG) {
                        Log.i(TAG, "topPackageName=" + topPackageName + "   " + event.getEventType());
                    }
                } else {
                    if (DEBUG) {
                        Log.e(TAG, "topPackageName=" + topPackageName + " 不在前台");
                    }
                }

            } catch (Exception e) {
                if (DEBUG) {
                    Log.e(TAG, "getUsageStatsTopPackage end ", e);
                }
            }
        } else {
            if (DEBUG) {
                Log.e(TAG, "getProcessNew 没有数据");
            }
        }
        return topPackageName;
    }

    //    /**
    //     * 获得栈顶的包名
    //     * 
    //     * @param activityManager
    //     * @return
    //     */
    //    public static String getTopPackageName1(ActivityManager activityManager) {
    //        try {
    //            if (Build.VERSION.SDK_INT < 21) {
    //                List<RunningTaskInfo> taskList = null;
    //                try {
    //                    taskList = activityManager.getRunningTasks(1);
    //                } catch (Exception e) {
    //                }
    //                if (taskList != null && !taskList.isEmpty()) {
    //                    String topAppPkg = taskList.get(0).topActivity.getPackageName();
    //                    return topAppPkg;
    //                }
    //                // return activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
    //            } else {
    //                // Hack, see
    //                // http://stackoverflow.com/questions/24625936/getrunningtasks-doesnt-work-in-android-l/27140347#27140347
    //                final List<ActivityManager.RunningAppProcessInfo> pis = activityManager.getRunningAppProcesses();
    //                if (pis != null && pis.size() > 0) {
    //                    for (ActivityManager.RunningAppProcessInfo pi : pis) {
    //                        if (pi.pkgList.length > 0) {
    //                            String pkg = pi.pkgList[0];
    //                            if (isForegroundProccess(pi)) {
    //                                return pkg;
    //                            } else {
    //                                if (DEBUG) {
    //                                    Log.e(TAG, "pkg==" + pkg + " 没有界面忽略");
    //                                }
    //                            }
    //
    //                        }
    //
    //                    }
    //                }
    //            }
    //        } catch (Exception e) {
    //
    //        }
    //
    //        return "";
    //    }
}
