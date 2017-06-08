package com.seekting.demo2016.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadPoolUtils {

    private static ScheduledExecutorService instance = null;

    private ThreadPoolUtils() {
    }

    public static ScheduledExecutorService getInstance() {
        if (instance == null) {
            instance = Executors.newScheduledThreadPool(10);
        }
        return instance;
    }
}
