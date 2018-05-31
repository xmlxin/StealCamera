package com.xiaoxin.jhang.steal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic(MainActivity.this);
            }
        });

        findViewById(R.id.bt_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video(MainActivity.this);
            }
        });
    }

    public void pic(Context context) {

        Intent intent3 = new Intent(context, CameraVideoActivity.class);
        intent3.putExtra("pic_video",true);
//        intent3.putExtra("time",10);
        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent3);
    }

    public void video(Context context) {

        Intent intent3 = new Intent(context, CameraVideoActivity.class);
        intent3.putExtra("pic_video",false);
        intent3.putExtra("time",10);
        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent3);
    }

    int count = -1;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        int action = event.getAction();

        if (action ==KeyEvent.ACTION_DOWN) {
            count++;
            Log.e(TAG, "ACTION_DOWN: "+count );
        }

        if (action== KeyEvent.ACTION_UP) {
            count--;
            Log.e(TAG, "ACTION_UP: "+count );
        }
        return super.dispatchKeyEvent(event);
    }

}
