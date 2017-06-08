package com.qihoo.mm.liba;

/**
 * Created by seekting on 17-2-16.
 */

public class LibSum {

    static {
        System.loadLibrary("sum");
    }

    public static native int sum(int a, int b);
}
