package com.seekting.demo2016.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.seekting.demo2016.AppEnv;
import com.seekting.demo2016.R;
import com.seekting.demo2016.util.ClassUtils;
import com.seekting.demo2016.util.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "MainActivity";
    ListView list;
    private List<Class> data;
    private BaseAdapter baseAdapter;
    private static HashMap<Class, String> DESC_MAP = new HashMap<Class, String>();
    private static HashMap<Class, String> TITLE_MAP = new HashMap<Class, String>();

    public static void putDesc(Class clazz, String desc) {
        DESC_MAP.put(clazz, desc);
    }

    public static void putTitle(Class clazz, String title) {
        TITLE_MAP.put(clazz, title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) {
            Log.d(TAG, "onCreate.");
        }
        long time = SystemClock.elapsedRealtime();
        int[] screenSize = UIUtils.getScreenRealPixels(this);
        setContentView(R.layout.activity_main);
        list = new ListView(this);
        List<Class> excludeList = new ArrayList<Class>();
        excludeList.add(MainActivity.class);
        data = ClassUtils.getActivitiesClass(this, this.getPackageName(), excludeList);
        final LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        baseAdapter = new BaseAdapter() {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.demo_layout, null);
                    ViewHolder viewHolder = new ViewHolder();
                    viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                    viewHolder.description = (TextView) convertView.findViewById(R.id.description);
                    viewHolder.className = (TextView) convertView.findViewById(R.id.class_name);
                    viewHolder.thumb = (ImageView) convertView.findViewById(R.id.thumb);
                    convertView.setTag(viewHolder);
                }
                setView((ViewHolder) convertView.getTag(), position);
                return convertView;
            }

            private void setView(ViewHolder viewHolder, int position) {
                Class class1 = getItem(position);
                viewHolder.className.setText(class1.getSimpleName());
                viewHolder.title.setText(TITLE_MAP.get(class1));
                viewHolder.description.setText(DESC_MAP.get(class1));

            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public Class getItem(int position) {
                return data.get(position);
            }

            @Override
            public int getCount() {
                return data.size();
            }
        };
        list.setAdapter(baseAdapter);
        setContentView(list);
        list.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private static class ViewHolder {
        TextView title;
        TextView description;
        TextView className;
        ImageView thumb;
        long requestCode;
        String key;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class clazz = (Class) baseAdapter.getItem(position);
        startActivity(new Intent(this, clazz));

    }


}
