package com.xiaoxin.jhang.steal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaoxin.jhang.steal.util.PrefUtils;

public class CameraConfigActivity extends AppCompatActivity {

    private static final String TAG = "CameraConfigActivity";
    private TextView mCameraStatus;
    private TextView mTvLight;
    private TextView mTvBlack,mTvVideoLength;
    private EditText mEvVideoLength;
    private AppCompatSpinner mSpLight;
    private AppCompatSpinner mSpBlack;
    private Button mBtVideoLengthSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_config);

        mCameraStatus = findViewById(R.id.tv_camera_status);
        mTvLight = (TextView) findViewById(R.id.tv_light);
        mTvBlack = (TextView) findViewById(R.id.tv_black);
        mSpLight = (AppCompatSpinner)findViewById(R.id.sp_light);
        mSpBlack = (AppCompatSpinner)findViewById(R.id.sp_black);
        mTvVideoLength = (TextView)findViewById(R.id.tv_video_length);
        mEvVideoLength = (EditText)findViewById(R.id.et_video_length);
        mBtVideoLengthSave = (Button)findViewById(R.id.bt_video_length_save);


        mSpLight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PrefUtils.putInt(CameraConfigActivity.this,Config.LIGHT,position);
                light();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       mSpBlack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               PrefUtils.putInt(CameraConfigActivity.this,Config.BLACK,position);
               black();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

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

        mBtVideoLengthSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoLength = mEvVideoLength.getText().toString().trim();
                if (!TextUtils.isEmpty(videoLength)) {
                    PrefUtils.putInt(CameraConfigActivity.this,Config.VIDEO_LENGTH,Integer.parseInt(videoLength));
                }
            }
        });
        getCameraConfig();
        light();
        light();
        mTvVideoLength.setText("视频时长:"+PrefUtils.getInt(this,Config.VIDEO_LENGTH,Config.TIME)+"s");

    }

    private void light() {
        int light = PrefUtils.getInt(CameraConfigActivity.this,Config.LIGHT,0);
        mTvLight.setText("屏幕亮屏:"+light);
    }

    private void black() {
        int light = PrefUtils.getInt(CameraConfigActivity.this,Config.BLACK,0);
        mTvBlack.setText("屏幕灭屏:"+light);
    }

    private void getCameraConfig() {
        int cameraBack = PrefUtils.getInt(this, Config.cameraBack, 0);
        mCameraStatus.setText("当前摄像头状态:"+cameraBack+"\n0代表后置摄像头,1代表前置摄像头");
    }

    /**
     * 设置前置or后置摄像头 0：后置；1前置
     * @param cameraBlack
     */
    private void config(int cameraBlack) {
        PrefUtils.putInt(this,Config.cameraBack,cameraBlack);
    }
}
