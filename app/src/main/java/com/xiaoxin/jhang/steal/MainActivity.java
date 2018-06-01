package com.xiaoxin.jhang.steal;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.xiaoxin.jhang.steal.service.CameraService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWhiteApp();
            }
        });
        findViewById(R.id.bt_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic();
            }
        });

        findViewById(R.id.bt_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video();
            }
        });

        findViewById(R.id.bt_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });

        findViewById(R.id.bt_stopService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, CameraService.class));
            }
        });
    }

    public void startService() {
        Intent intent = new Intent(this, CameraService.class);
        intent.putExtra("pic_video",true);
        startService(intent);
    }

    public void pic() {
        Intent intent = new Intent(this, CameraVideoActivity.class);
        intent.putExtra("pic_video",true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void video() {
        Intent intent = new Intent(this, CameraVideoActivity.class);
        intent.putExtra("pic_video",false);
        intent.putExtra("time",10);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void addWhiteApp() {
        if(Build.MANUFACTURER.equals("Xiaomi")) {
            Intent intent = new Intent();
//            intent.setAction("miui.intent.action.OP_AUTO_START");//小米开机自启action
//            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setClassName("com.miui.powerkeeper","com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity");
            startActivity(intent);
        }
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
