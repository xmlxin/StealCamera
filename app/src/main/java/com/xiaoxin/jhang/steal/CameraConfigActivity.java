package com.xiaoxin.jhang.steal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaoxin.jhang.steal.util.PrefUtils;

public class CameraConfigActivity extends AppCompatActivity {

    private TextView mCameraStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_config);

        mCameraStatus = findViewById(R.id.tv_camera_status);

        findViewById(R.id.bt_open_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config(Config.CAMERA_FACING_BACK);
                getCameraConfig();
            }
        });

        findViewById(R.id.bt_open_front).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config(Config.CAMERA_FACING_FRONT);
                getCameraConfig();
            }
        });
        getCameraConfig();

    }

    private void getCameraConfig() {
        int cameraBack = PrefUtils.getInt(this, Config.cameraBack, 0);
        mCameraStatus.setText("当前摄像头状态"+cameraBack);
    }

    /**
     * 设置前置or后置摄像头 0：后置；1前置
     * @param cameraBlack
     */
    private void config(int cameraBlack) {
        PrefUtils.putInt(this,Config.cameraBack,cameraBlack);
    }
}
