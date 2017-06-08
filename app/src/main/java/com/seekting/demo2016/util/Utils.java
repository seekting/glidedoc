package com.seekting.demo2016.util;

import android.content.Context;

/**
 */
public class Utils {
    public static Object getSystemService(Context context, String name) {

        return context.getApplicationContext().getSystemService(name);

    }
}
