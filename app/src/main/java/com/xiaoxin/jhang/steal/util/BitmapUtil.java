package com.xiaoxin.jhang.steal.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

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
    public static Bitmap obtainPic(byte[] data) {
        // 将得到的照片进行270°旋转，使其竖直
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Matrix matrix = new Matrix();
        matrix.setRotate(90);//后置摄像头选择90度，前置摄像头旋转270度
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        File picturePath = FileUtil.picturePath();

        try {
            FileOutputStream fos = new FileOutputStream(picturePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return bitmap;
        } catch (Exception e) {
            Log.e(TAG, "File not found: " + e.getMessage());
        }
        return bitmap;
    }

}
