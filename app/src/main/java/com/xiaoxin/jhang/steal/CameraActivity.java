package com.xiaoxin.jhang.steal;

import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    private Button mStart;
    private SurfaceView mSurfaceView;

    private final static String TAG = CameraActivity.class.getSimpleName();
    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mStart = (Button) findViewById(R.id.bt_start);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);

        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        startPreviewDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceHolder.getSurface() == null){
            return;
        }
//        Cameras.followScreenOrientation(this, mCamera);
//        Log.d(TAG, "Restart preview display[SURFACE-CHANGED]");
//        stopPreviewDisplay();
//        startPreviewDisplay(mSurfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
