package com.xiaoxin.jhang.steal;

import android.hardware.Camera;
import android.os.Environment;

import com.xiaoxin.jhang.steal.util.DateUtil;

/**
 * @author: xiaoxin
 * date: 2018/5/31
 * describe:参数配置文件
 * 修改内容:
 */

public class Config {

    //路径
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xiaoxin/";
    public static final String DAY_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xiaoxin/"+ DateUtil.formatDataToFile()+"/";

    public static final String SECRET_CODE = "123456";
    /** camera */
    //设置摄像头状态
    public static String cameraBack = "cameraBack";
    //后置摄像头
    public static int CAMERA_FACING_BACK = Camera.CameraInfo.CAMERA_FACING_BACK; //Camera.CameraInfo.CAMERA_FACING_BACK
    //前置摄像头
    public static int CAMERA_FACING_FRONT = Camera.CameraInfo.CAMERA_FACING_FRONT;//Camera.CameraInfo.CAMERA_FACING_FRONT
    //后置摄像头旋转角度
    public static int ROTATE_ANGLE_BACK = 90;
    //前置摄像头旋转角度
    public static int ROTATE_ANGLE_FRONT = 270;

    //选择拍照还是录像 默认拍照
    public static String PIC_OR_VIDEO = "pic_video";
    public static String VIDEO_TIME = "time";
    //video时长，默认10s
    public static int TIME = 10;

    public static String LIGHT = "light";
    public static String BLACK = "black";
    public static String VIDEO_LENGTH = "video_length";

}
