package com.seekting.demo2016.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstantAppActivity extends BaseActivity {
    static {
        MainActivity.putTitle(InstantAppActivity.class, "InstantAppActivity");
        MainActivity.putDesc(InstantAppActivity.class, "InstantAppActivity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("360Weather");
        setContentView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //android.intent.action.VIEW -c android.intent.category.BROWSABLE -d http://instantapp.weather.mm.qihoo.com/hello/parameter -n "com.google.android.instantapps.supervisor/.UrlHandler"
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.BROWSABLE");
                String url = "https://instantapp.weather.mm.qihoo.com/enter/parameter";
                intent.setData(Uri.parse(url));
                intent.setComponent(new ComponentName("com.google.android.instantapps.supervisor", "com.google.android.instantapps.supervisor.UrlHandler"));
                startActivity(intent);

            }
        });


    }

}
