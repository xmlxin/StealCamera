package com.xiaoxin.jhang.steal.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.xiaoxin.jhang.steal.CameraVideoActivity;
import com.xiaoxin.jhang.steal.Config;
import com.xiaoxin.jhang.steal.app.MyApplication;
import com.xiaoxin.jhang.steal.service.WxCameraService;

/**
 * @author: xiaoxin
 * date: 2018/5/31
 * describe:
 * 修改内容:
 */

public class StartCameraUtil {

    /**
     * 开启拍照
     * @param ctx
     * @param editContent
     * @return
     */
    public static boolean startCameraActivity(Context ctx, String editContent) {
        if (!TextUtils.isEmpty(editContent)) {
            String substring = editContent.substring(4, editContent.length()-2);
            if (TextUtils.isEmpty(substring)) {
                substring = "1";
            }
            if (editContent.contains(Config.SpecialPic)) {
                Intent intent = new Intent(MyApplication.mContext, CameraVideoActivity.class);
                intent.putExtra("pic_video",true);
                intent.putExtra("picNumber",Integer.parseInt(substring));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
                return false;
            }
            if (editContent.contains(Config.SpecialVideo)) {
//                String substring = editContent.substring(Config.SpecialVideo.length(), editContent.length());
//                if (TextUtils.isEmpty(substring)) {
//                    substring = "10";
//                }
                Intent intent = new Intent(MyApplication.mContext, CameraVideoActivity.class);
                intent.putExtra("pic_video",false);
                intent.putExtra("time", Integer.parseInt(substring));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
                return false;
            }
        }
        return true;
    }

    /**
     * 开启拍照
     * @param ctx
     * @param editContent
     * @return
     */
    public static boolean startCameraService(Context ctx, String editContent) {
        if (!TextUtils.isEmpty(editContent)) {
            String substring = editContent.substring(4, editContent.length()-2);
            if (TextUtils.isEmpty(substring)) {
                substring = "1";
            }
            if (editContent.contains(Config.SpecialPic)) {
                Intent intent = new Intent(MyApplication.mContext, WxCameraService.class);
                intent.putExtra("pic_video",true);
                intent.putExtra("picNumber",Integer.parseInt(substring));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startService(intent);
                return false;
            }
            if (editContent.contains(Config.SpecialVideo)) {
//                String substring = editContent.substring(Config.SpecialVideo.length(), editContent.length());
//                if (TextUtils.isEmpty(substring)) {
//                    substring = "10";
//                }
                Intent intent = new Intent(MyApplication.mContext, WxCameraService.class);
                intent.putExtra("pic_video",false);
                intent.putExtra("time", Integer.parseInt(substring));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startService(intent);
                return false;
            }
        }
        return true;
    }

}
