package com.xiaoxin.jhang.steal;

import android.hardware.Camera;
import android.os.Environment;

/**
 * @author: xiaoxin
 * date: 2018/5/31
 * describe:参数配置文件
 * 修改内容:
 */

public class Config {

    //路径
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/xiaoxin/";

    /** camera */
    //后置摄像头
    public static int CAMERA_FACING_BACK = Camera.CameraInfo.CAMERA_FACING_BACK;
    //前置摄像头
    public static int CAMERA_FACING_FRONT = Camera.CameraInfo.CAMERA_FACING_FRONT;
    //后置摄像头旋转角度
    public static int ROTATE_ANGLE_BACK = 90;
    //前置摄像头旋转角度
    public static int ROTATE_ANGLE_FRONT = 270;

    //选择拍照还是录像 默认拍照
    public static String PIC_OR_VIDEO = "pic_video";
    public static String VIDEO_TIME = "time";
    //video时长，默认10s
    public static int TIME = 10;

}
