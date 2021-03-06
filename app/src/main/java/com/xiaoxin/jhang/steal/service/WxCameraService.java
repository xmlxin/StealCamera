package com.xiaoxin.jhang.steal.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.xiaoxin.jhang.steal.Config;
import com.xiaoxin.jhang.steal.util.BitmapUtil;
import com.xiaoxin.jhang.steal.util.FileUtil;
import com.xiaoxin.jhang.steal.util.PrefUtils;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: xiaoxin
 * date: 2018/5/31
 * describe:
 * 修改内容:
 */

public class WxCameraService extends Service {

    private static String TAG = "CameraService";
    private Context mContext;

    private MediaRecorder mMediaRecorder;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private boolean mIsRecording = false;
    private int mTime;
    private boolean mPicVideo;
    public WindowManager mWindowManager;
    private int count = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mContext = getApplication();
        mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mSurfaceView = new SurfaceView(this);
        LayoutParams params = new LayoutParams();
        params.width = 1;
        params.height = 1;
        params.alpha = 0;
        params.type = LayoutParams.TYPE_APPLICATION_OVERLAY ;
        // 屏蔽点击事件
        params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
        mWindowManager.addView(mSurfaceView, params);

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                releaseCamera();
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                initPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {

            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPicVideo = intent.getBooleanExtra("pic_video",false);
        mTime = intent.getIntExtra("time",10);
        count = intent.getIntExtra("picNumber",1);

        if (mPicVideo) {  //拍照
            takePicture();
        }else {  //录像
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG,"录像");
                    startMediaRecorder();
                    Timer timer = new Timer();
                    timer.schedule(new TimerThread(), mTime * 1000);
                }
            },2000);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void takePicture() {
        Log.e(TAG, "onClick: ");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG,"拍照"+count);
                if (mCamera != null) {
                    mCamera.takePicture(null, null, mPicture);
                    count--;
                }
            }
        },2000);//延迟2s做准备
    }

    /**
     * 设置camera参数
     */
    protected void initPreview() {
        mCamera = Camera.open(PrefUtils.getInt(this, Config.cameraBack, 0));//设置前后摄像头0,1
        Camera.Parameters parameters = mCamera.getParameters();
//        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);//设置自动对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);//设置不断聚焦
        List<Camera.Size> photoSizes  = parameters.getSupportedPictureSizes();//获取相机可支持的尺寸

        for (Camera.Size size : photoSizes) {//因为我需要的是4：3的图片，所以设置为4：3图片尺寸。
//            Log.e(TAG, "width: "+size.width+"height: "+size.height );
            if (size.width / 4 == size.height / 3) {
                parameters.setPictureSize(size.width, size.height);//设置图片像素尺寸
                Log.e(TAG, "SET width:" + size.width + " height " + size.height);
                break;
            }
        }
        mCamera.setParameters(parameters);//最后一定要把parameters设置给camera
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCameraDisplayOrientation(mContext, Camera.CameraInfo.CAMERA_FACING_BACK,mCamera);
        mCamera.startPreview();
    }

    public void setCameraDisplayOrientation(Context ctx, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = mWindowManager.getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270;  break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  //  compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
//        camera.setDisplayOrientation(180);
        camera.setDisplayOrientation(result);
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // 将得到的照片进行270°旋转，使其竖直
            //处理图像数据
            BitmapUtil.obtainPic(WxCameraService.this,data);

            try {
                mCamera.reconnect();
                if (count == 0) {
                    releaseCamera();
                    vibrate();
                    stopSelf();
                }else {
                    initPreview();
                    takePicture();
                    vibrate();
                }
            } catch (Exception e) {
                Log.e(TAG, "File not found: " + e.getMessage());
            }
        }
    };

    protected void releaseCamera() {
        if(mCamera!=null){
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    private void startMediaRecorder() {
        //https://blog.csdn.net/qwildwolf/article/details/78664149
        CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        // BEGIN_INCLUDE (configure_media_recorder)
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.reset();
        mIsRecording = true;
        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setOrientationHint(90);//后置摄像头选择90度，前置摄像头旋转270度

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);//VOICE_RECOGNITION
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a Camera Parameters
        mMediaRecorder.setOutputFormat(mProfile.fileFormat);
        /* 设置分辨率*/
        mMediaRecorder.setVideoSize(mProfile.videoFrameWidth, mProfile.videoFrameHeight);
        //视频文件的流畅度主要跟VideoFrameRate有关，参数越大视频画面越流畅，但实际中跟你的摄像头质量有很大关系
        mMediaRecorder.setVideoFrameRate(mProfile.videoFrameRate);
        /* Encoding bit rate: 1 * 1024 * 1024*/
        //设置帧频率，然后就清晰了 清晰度和录制文件大小主要和EncodingBitRate有关，参数越大越清晰，同时录制的文件也越大
        mMediaRecorder.setVideoEncodingBitRate(mProfile.videoBitRate);
        mMediaRecorder.setAudioEncodingBitRate(mProfile.audioBitRate);

        mMediaRecorder.setAudioChannels(mProfile.audioChannels);
        mMediaRecorder.setAudioSamplingRate(mProfile.audioSampleRate);
        // 视频录制格式
        mMediaRecorder.setVideoEncoder(mProfile.videoCodec);
        mMediaRecorder.setAudioEncoder(mProfile.audioCodec);

        // Step 4: Set output file
//        CamcorderProfile mCamcorderProfile = CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_BACK,
//                CamcorderProfile.QUALITY_HIGH);//QUALITY_2160P
//        mMediaRecorder.setProfile(mCamcorderProfile);
        mMediaRecorder.setOutputFile(FileUtil.videoPath());
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
        try {
            //准备录制
            mMediaRecorder.prepare();
            // 开始录制
            mMediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopMediaRecorder() {
        if(mMediaRecorder !=null){
            if(mIsRecording){
                mMediaRecorder.stop();
                //mCamera.lock();
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder =null;
                mIsRecording = false;
                try {
                    releaseCamera();//防止内存泄漏
                    vibrate();
                    stopSelf();
                } catch (Exception e) {
                    Log.e(TAG, "reconect fail"+e.toString());
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

    class TimerThread extends TimerTask {

        /**
         * 停止录像
         */
        @Override
        public void run() {
            stopMediaRecorder();
            this.cancel();
//            finish();
        }
    }

    /**
     * 震动00毫秒
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }


}
