package com.seekting.demo2016.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.seekting.demo2016.service.TIntentService;

public class IntentServiceActivity extends BaseActivity {
    static {
        MainActivity.putTitle(IntentServiceActivity.class, "IntentService的生命周期");
        MainActivity.putDesc(IntentServiceActivity.class, "如果处理速度过慢，连续启动它的话会把所有的任务　执行完最后执行onDestory");
    }

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btn = new Button(this);
        setContentView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(IntentServiceActivity.this, TIntentService.class));
            }
        });


    }

}
