package com.seekting.demo2016.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class UIUtils {

    public static int[] getScreenRealPixels(Context c) {
        if (Build.VERSION.SDK_INT >= 14) {
            try {

                WindowManager windowManager = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();

                Point point = new Point();
                Method method = Display.class.getMethod("getRealSize", new Class[] { Point.class });
                method.invoke(display, point);

                return new int[] { point.x, point.y };
            } catch (Exception e) {
            }

        }
        return getScreenPixels(c);
    }

    public static int[] getScreenPixels(Context c) {
        int[] pixels = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        dm = c.getResources().getDisplayMetrics();
        pixels[0] = dm.widthPixels;
        pixels[1] = dm.heightPixels;
        return pixels;
    }
}
