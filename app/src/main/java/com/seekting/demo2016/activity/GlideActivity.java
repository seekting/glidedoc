package com.seekting.demo2016.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.seekting.demo2016.AppEnv;
import com.seekting.demo2016.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GlideActivity extends FragmentActivity {
    static {
        MainActivity.putTitle(GlideActivity.class, "glide");
        MainActivity.putDesc(GlideActivity.class, "glide");
    }

    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "GlideActivity";
    private static final String URL = "http://img.blog.csdn.net/20140621112749546?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGlmZXNob3c=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center";
//    private static final String URL = "http://192.168.1.108:8080/img/20170803162540603.jpg";
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3, mImageView4, mImageView5, mImageView6;
    private Button mButton1, mButton2, mButton3, mButton4, mButton5, mButton6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glide_layout);
        mImageView1 = (ImageView) findViewById(R.id.imageview1);
        mImageView2 = (ImageView) findViewById(R.id.imageview2);
        mImageView3 = (ImageView) findViewById(R.id.imageview3);
        mImageView4 = (ImageView) findViewById(R.id.imageview4);
        mImageView5 = (ImageView) findViewById(R.id.imageview5);
        mImageView6 = (ImageView) findViewById(R.id.imageview6);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton5 = (Button) findViewById(R.id.button5);
        mButton6 = (Button) findViewById(R.id.button6);
        Context app = getApplicationContext();
        if (DEBUG) {
            Log.d(TAG, "onCreate.app=" + app + "app instanceof ContextWrapper=" + (app instanceof ContextWrapper));
        }
        final RequestManager requestManager = Glide.with(this);
        requestManager.setDefaultOptions(new RequestManager.DefaultOptions() {
            @Override
            public <T> void apply(GenericRequestBuilder<T, ?, ?, ?> requestBuilder) {
                requestBuilder.placeholder(new ColorDrawable(Color.RED));

            }
        });
        final DrawableTypeRequest<String> drawableTypeRequest = requestManager.load(URL);
        drawableTypeRequest.fallback(R.mipmap.ic_launcher);
        drawableTypeRequest.error(R.mipmap.day_night_time_bg);
//        Target<GlideDrawable> j = drawableTypeRequest.into(mImageView1);


        final DrawableTypeRequest<Integer> request = requestManager.load(R.mipmap.ic_launcher);


//        final Target<GlideDrawable> target2 = request.into(mImageView2);
        Fragment fragment = new MyFragment();


        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.img_main, fragment, "fragment").commitAllowingStateLoss();
        Fragment fragment1 = new MyFragment();
        fm.beginTransaction().add(R.id.img_main, fragment1, "fragment1").commitAllowingStateLoss();

        Log.d("seekting", "GlideActivity.onCreate()");

        BlockingQueue<Runnable> mRunnables = new PriorityBlockingQueue<Runnable>(100);
        ThreadPoolExecutor threadPoolExecutor = new MyThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, mRunnables);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.into(mImageView2);

            }
        });
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawableTypeRequest.into(mImageView1);
            }
        });
        final DrawableTypeRequest<Integer> request2 = requestManager.load(R.mipmap.ic_launcher);
//        request2.into(mImageView3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request2.into(mImageView3);
            }
        });

        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.mipmap.day_night_time_bg);
        final File file = new File(getFilesDir(), "test.jpg");

        try {
            bitmapDrawable.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawableTypeRequest<File> j = requestManager.load(file);
                j.into(mImageView4);

            }
        });

        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getP();
            }
        });

        mButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {

                        if (DEBUG) {
                            Log.d(TAG, "onResourceReady." + resource);

                        }
                        mImageView6.setImageDrawable(resource);

                    }

                };

                DrawableTypeRequest<String> builder = requestManager.load(URL);
                builder.into(simpleTarget);

            }
        });

    }

    private class MyRunable implements Runnable {

        public MyRunable(int provity) {
            this.provity = provity;
        }

        private int provity;

        @Override
        public void run() {
            Log.d("seekting", "MyRunable.run" + provity);
//            if (provity == 0) {
            SystemClock.sleep(100);
//            }

        }
    }

    private class MyThreadPoolExecutor extends ThreadPoolExecutor {
        private final AtomicInteger ordering = new AtomicInteger();

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
            return super.newTaskFor(callable);

        }

        @Override
        protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {

            Log.d("seekting", "MyThreadPoolExecutor.newTaskFor");
            return new LoadTask<T>(runnable, value);

        }
    }

    static class LoadTask<T> extends FutureTask<T> implements Comparable<LoadTask<?>> {
        int pro;

        public LoadTask(@NonNull Runnable runnable, T t) {
            super(runnable, t);
            MyRunable myRunable = (MyRunable) runnable;
            pro = myRunable.provity;
        }

        public boolean equals(Object o) {
            if (o instanceof LoadTask) {
                LoadTask<Object> other = (LoadTask<Object>) o;
                return pro == other.pro;
            }
            return false;
        }

        @Override
        public int compareTo(@NonNull LoadTask<?> loadTask) {
            return loadTask.pro - pro;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("seekting", "GlideActivity.onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("seekting", "GlideActivity.onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("seekting", "GlideActivity.onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("seekting", "GlideActivity.onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("seekting", "GlideActivity.onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("seekting", "GlideActivity.onDestroy()");
    }

    @SuppressLint("ValidFragment")
    private static class MyFragment extends Fragment {
        public MyFragment() {
            Log.d("seekting", "MyFragment.MyFragment()");

        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            Glide.with(this);

            Log.d("seekting", "MyFragment.onAttach()" + getTag());
            Log.d("seekting", "MyFragment.onAttach()getChildFragmentManager=" + getChildFragmentManager());

        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d("seekting", "MyFragment.onCreate()");
            RequestManager fragmentRequestManager = Glide.with(this);
            fragmentRequestManager.load(URL);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.d("seekting", "MyFragment.onCreateView()");
            TextView tv = new TextView(getActivity());
            tv.setText("fragment");
            return tv;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Log.d("seekting", "MyFragment.onViewCreated()");
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.d("seekting", "MyFragment.onActivityCreated()");
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.d("seekting", "MyFragment.onStart()");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d("seekting", "MyFragment.onResume()");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d("seekting", "MyFragment.onPause()");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d("seekting", "MyFragment.onStop()");
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            Log.d("seekting", "MyFragment.onDestroyView()");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d("seekting", "MyFragment.onDestroy()");
        }

        @Override
        public void onDetach() {
            super.onDetach();
            Log.d("seekting", "MyFragment.onDetach()");
        }
    }

    public class list {
//        public void into(Activity activity) {
//
//        }
//        public <T extends Activity> void into(T target) {
//
//
//        }


    }

    private void getP() {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = this.getContentResolver();

        //只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(mImageUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

        if (mCursor.moveToFirst()) {
            String path = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));
            Uri ri = Uri.fromFile(new File(path));
            MediaStore.Files.getContentUri("external");

            DrawableTypeRequest<Uri> requestBuilder = Glide.with(this).load(ri);
            requestBuilder.into(mImageView5);


        }
    }
}
