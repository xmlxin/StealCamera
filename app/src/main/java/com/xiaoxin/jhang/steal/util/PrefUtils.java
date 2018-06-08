package com.xiaoxin.jhang.steal.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * 专门访问和设置SharePreference的工具类, 保存和配置一些设置信息
 * 
 * @author xiaoxin
 * 
 */
public class PrefUtils {

	private static final String SHARE_PREFS_NAME = "camera";

	public static void putBoolean(Context ctx, String key, boolean value) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		pref.edit().putBoolean(key, value).apply();
	}

	public static boolean getBoolean(Context ctx, String key,
			boolean defaultValue) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		return pref.getBoolean(key, defaultValue);
	}

	public static void putString(Context ctx, String key, String value) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		pref.edit().putString(key, value).apply();
	}

	public static String getString(Context ctx, String key, String defaultValue) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);


		return pref.getString(key, defaultValue);
	}

	public static void putInt(Context ctx, String key, int value) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);
		
		pref.edit().putInt(key, value).apply();
	}

	public static int getInt(Context ctx, String key, int defaultValue) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);
		return pref.getInt(key, defaultValue);
	}
	
	public static void putLong(Context ctx, String key, long value) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);

		pref.edit().putLong(key, value).apply();
	}

	public static long getLong(Context ctx, String key, long defaultValue) {
		SharedPreferences pref = ctx.getSharedPreferences(SHARE_PREFS_NAME,
				Context.MODE_PRIVATE);
		return pref.getLong(key, defaultValue);
	}
}
