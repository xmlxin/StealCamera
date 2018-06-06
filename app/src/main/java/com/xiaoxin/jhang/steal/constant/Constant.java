package com.xiaoxin.jhang.steal.constant;

import com.xiaoxin.jhang.steal.app.MyApplication;
import com.xiaoxin.jhang.steal.util.AppUtil;

/**
 * @author xiaoxin
 * @date 2018/04/08
 * @describe ：常量
 * 修改内容
 */

public class Constant {

    public static final String SPCONFIG = "wxConfig";
    public static final String SPEAK = "speak";

    public static final String TARGET_PACKAGE_MMS = "com.tencent.mm";
    public static final String SEND_MSG_MM_CLASS = "com.tencent.mm.ui.chatting.o";
    public static final String SEND_MSG_MM_METHOD = "EM";

    //wx
    public static String EDITTEXT_ID;
    public static final String SEND_ID = "com.tencent.mm:id/aai";
    public static final String SEND_TEXT = "发送";
    public static final String BUTTON = "android.widget.Button";
    public static final String TEXTVIEW = "android.widget.TextView";

    //dingding
    public static String DD_ET_ID = "com.alibaba.android.rimet:id/et_sendmessage";
    public static final String PACKAGE_DD = "com.alibaba.android.rimet";

    public static final String APP_ID = "20180524000165309";
    public static final String SECURITY_KEY = "v0Ct47KEYz_VLJgTsANZ";
    //若当月翻译字符数≤2百万，当月免费 我不信能用超

    //http://api.fanyi.baidu.com/api/trans/product/apidoc#appendix 翻译

    static {

        switch (AppUtil.getVersionName(MyApplication.mContext,TARGET_PACKAGE_MMS)) {
            case "6.6.5":
                EDITTEXT_ID = "com.tencent.mm:id/aac";
                break;
            case "6.6.6":
                EDITTEXT_ID = "com.tencent.mm:id/aaa";
                break;
            case "6.6.7":
                EDITTEXT_ID = "com.tencent.mm:id/ac8";
                break;
            default:
                EDITTEXT_ID = "com.tencent.mm:id/aaa";
                break;
        }
    }

}
