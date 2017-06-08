package com.seekting.demo2016.activity;

import android.os.Bundle;
import android.util.Log;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.seekting.demo2016.AppEnv;
import com.seekting.demo2016.xutil.User;

import java.util.List;

public class XUtilActivity extends BaseActivity {
    static {
        MainActivity.putTitle(XUtilActivity.class, "Xutil的使用");
        MainActivity.putDesc(XUtilActivity.class, "");
    }

    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "XUtilActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbUtils db = DbUtils.create(this);

        User us = new User();
        us.setId("1112");
        us.setName("seekting");
        us.setAge(14);
        try {
            db.save(us);
        } catch (DbException e) {
            e.printStackTrace();
        }

        try {
            List<User> list = db.findAll(User.class);
            if (DEBUG) {
                Log.d(TAG, "onCreate." + list);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

    }


}
