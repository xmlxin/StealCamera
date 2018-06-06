package com.xiaoxin.jhang.steal.app;

import android.app.Application;
import android.content.Context;

/**
 * @author: xiaoxin
 * date: 2018/5/24
 * describe:
 * 修改内容:
 */

public class MyApplication extends Application {

    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }
}
