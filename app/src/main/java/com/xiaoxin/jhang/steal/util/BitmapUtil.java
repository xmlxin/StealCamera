package com.xiaoxin.jhang.steal.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import com.xiaoxin.jhang.steal.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: xiaoxin
 * date: 2018/5/31
 * describe:
 * 修改内容:
 */

public class BitmapUtil {

    private static final String TAG = "BitmapUtil";

    /**
     * 处理Camera传过来的data[]数据，转化成bitmap
     * @param data
     * @return
     */
    public static void obtainPic(final Context ctx, final byte[] data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 将得到的照片进行270°旋转，使其竖直
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Matrix matrix = new Matrix();
                int cameraBack = PrefUtils.getInt(ctx, Config.cameraBack, 0);
                if (cameraBack == 0) {
                    matrix.setRotate(90);
                }else if (cameraBack == 1) {
                    matrix.setRotate(270);
                }else {
                    matrix.setRotate(90);
                }
//                matrix.setRotate(90);//后置摄像头:正着拍需要选择90度倒着拍需要选择270度，前置摄像头旋转270度
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                File picturePath = FileUtil.picturePath();

                try {
                    FileOutputStream fos = new FileOutputStream(picturePath);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                } catch (Exception e) {
                    Log.e(TAG, "File not found: " + e.getMessage());
                }
            }
        }).start();

    }

}
