package com.seekting.demo2016.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.seekting.demo2016.AppEnv;
import com.seekting.demo2016.R;

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
    private ImageView mImageView;
    private ImageView mImageView1;
    private ImageView mImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageView = new ImageView(this);
        mImageView1 = new ImageView(this);
        mImageView2 = new ImageView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setId(R.id.img_main);
        linearLayout.addView(mImageView, 500, 500);
        linearLayout.addView(mImageView1, 500, 500);
        linearLayout.addView(mImageView2, 500, 500);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);
        Context app = getApplicationContext();
        if (DEBUG) {
            Log.d(TAG, "onCreate.app=" + app + "app instanceof ContextWrapper=" + (app instanceof ContextWrapper));
        }
        RequestManager requestManager = Glide.with(this);
        final DrawableTypeRequest<String> drawableTypeRequest = requestManager.load(URL);
        drawableTypeRequest.fallback(R.mipmap.ic_launcher);
        drawableTypeRequest.error(R.mipmap.day_night_time_bg);
//        Target<GlideDrawable> j = drawableTypeRequest.into(mImageView);


        final DrawableTypeRequest<Integer> request = requestManager.load(R.mipmap.ic_launcher);


//        final Target<GlideDrawable> target2 = request.into(mImageView1);
        Fragment fragment = new MyFragment();


        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.img_main, fragment, "fragment").commitAllowingStateLoss();
        Fragment fragment1 = new MyFragment();
        fm.beginTransaction().add(R.id.img_main, fragment1, "fragment1").commitAllowingStateLoss();

        Log.d("seekting", "GlideActivity.onCreate()");

        BlockingQueue<Runnable> mRunnables = new PriorityBlockingQueue<Runnable>(100);
        ThreadPoolExecutor threadPoolExecutor = new MyThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, mRunnables);
        mImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    request.into(mImageView1);

            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawableTypeRequest.into(mImageView);
            }
        });
        final DrawableTypeRequest<Integer> request2 = requestManager.load(R.mipmap.ic_launcher);
//        request2.into(mImageView2);
        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request2.into(mImageView2);
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
}
