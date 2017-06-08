package com.seekting.demo2016.util;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by seekting on 2016/3/16.
 */
public class AlarmMather {
    private static final String TAG = "AlarmMather";
    private static final boolean DEBUG = true;
    private static final SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("E HH:mm");
    private static final SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("E a h:mm");
    private static final Date DATE = new Date();
    private static final long MAX_DELTA_TIME = 1000 * 50;
    private static List<String> alarms = new ArrayList<String>();
    private static final int MAX_ALARM_SIZE = 6;

    public static void initArams(Context context) {
        alarms.clear();
        readAlarm(context, alarms);
    }

    public static void refreshAlarm(Context context) {
        boolean changed = false;

        String next = Settings.System.getString(context.getContentResolver(), Settings.System.NEXT_ALARM_FORMATTED);
        if (TextUtils.isEmpty(next)) {
            return;

        }
        if (!alarms.contains(next)) {
            alarms.add(next);
            changed = true;
        }

        while (alarms.size() > MAX_ALARM_SIZE) {
            alarms.remove(0);
            changed = true;
        }
        if (DEBUG) {
            Log.d(TAG, "refreshAlarm." + alarms);
        }
        if (changed) {
            saveAlarm(context, alarms);
        }

    }

    private static void saveAlarm(Context context, List<String> alarms) {
        JSONArray jsonArray = new JSONArray();
        for (String alarm : alarms) {
            jsonArray.put(alarm);
        }
        String str = jsonArray.toString();
        SharedPref.setString(context, SharedPref.MOBILE_CHARING_ALARM, str);
    }

    private static void readAlarm(Context context, List<String> out) {
        String str = SharedPref.getString(context, SharedPref.MOBILE_CHARING_ALARM);
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONArray json = new JSONArray(str);
                for (int i = 0; i < json.length(); i++) {
                    out.add(json.optString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

        }

    }

    public static boolean isCurrentAlarm() {

        long current = System.currentTimeMillis();
        DATE.setTime(current);
        for (String alarm : alarms) {
            if (match(current, simpleDateFormat1, alarm)) return true;
            if (match(current, simpleDateFormat2, alarm)) return true;
        }

        return false;

    }

    private static boolean match(long current, SimpleDateFormat simpleDateFormat, String alarm) {
        if (!TextUtils.isEmpty(alarm)) {
            if (DEBUG) {
                Log.d(TAG, "match.alarm=" + alarm);
            }
            String str1 = simpleDateFormat.format(DATE);
            if (DEBUG) {
                Log.d(TAG, "isCurrentAlarm.估计值1" + str1);
            }
            if (alarm.contains(str1)) {
                return true;
            }
            DATE.setTime(current + MAX_DELTA_TIME);
            str1 = simpleDateFormat.format(DATE);
            if (DEBUG) {
                Log.d(TAG, "isCurrentAlarm.估计值2" + str1);
            }
            if (alarm.contains(str1)) {
                return true;
            }
            DATE.setTime(current - MAX_DELTA_TIME);
            str1 = simpleDateFormat.format(DATE);
            if (DEBUG) {
                Log.d(TAG, "isCurrentAlarm.估计值3" + str1);
            }
            if (alarm.contains(str1)) {
                return true;
            }

        }
        return false;
    }
}
