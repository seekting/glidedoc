package com.seekting.demo2016.activity;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.seekting.demo2016.AppEnv;

public class EvaluatorActivity extends BaseActivity {
    static {
        MainActivity.putTitle(EvaluatorActivity.class, "Evaluator的用法");
        MainActivity.putDesc(EvaluatorActivity.class, "Evaluator");
    }

    public static final boolean DEBUG = AppEnv.bAppdebug;
    public static final String TAG = "EvaluatorActivity";
    private ValueAnimator mValueAnimator;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Person person1 = new Person("aaa");
        Person person2 = new Person("bbb");
        mValueAnimator = ValueAnimator.ofObject(new MyEvaluator(), person1, person2);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.setEvaluator(new MyEvaluator());
        mTv = new TextView(this);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                Person value = (Person) animation.getAnimatedValue();
                if (DEBUG) {
                    Log.d(TAG, "onAnimationUpdate.value=" + value);
                }
                mTv.setText(value.name);
            }
        });

        mValueAnimator.start();
        setContentView(mTv);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mValueAnimator.cancel();
    }

    private static class MyEvaluator implements TypeEvaluator<Person> {


        private Person mPerson = new Person("result");

        @Override
        public Person evaluate(float fraction, Person startValue, Person endValue) {

            mPerson.name = "result" + fraction;

            return mPerson;
        }
    }

    private static class Person {
        String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
