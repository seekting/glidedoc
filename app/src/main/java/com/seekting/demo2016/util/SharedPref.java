package com.seekting.demo2016.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by seekting on 2016/3/16.
 */
public class SharedPref {

    public static final String MOBILE_CHARING_ALARM = "mobile_charing_alarm";

    public static final void setString(Context context, String key, String value) {
        SharedPreferences.Editor e = context.getSharedPreferences("security", Context.MODE_PRIVATE).edit();
        e.putString(key, value);
        e.commit();
    }

    public static String getString(Context context, String key) {
        return context.getSharedPreferences("security", Context.MODE_PRIVATE).getString(key, "");
    }
}
