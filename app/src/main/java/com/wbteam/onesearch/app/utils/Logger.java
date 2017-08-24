package com.wbteam.onesearch.app.utils;

import android.util.Log;

/**
 *  Log日志的记录
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-5-23  下午6:10:54
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class Logger {
	
	private static final String TAG = Logger.class.getName();
	
	public static boolean isDebug() {
        return true;
//        return false;
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        try {
            if (isDebug())
                Log.i(TAG, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(String msg) {
        try {
            if (isDebug())
                Log.d(TAG, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void e(String msg) {
        try {
            if (isDebug())
                Log.e(TAG, msg);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static void v(String msg) {
        try {
            if (isDebug())
                Log.v(TAG, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 传入自定义的TAG的函数

    /**
     * Log Info
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        try {
            if (isDebug())
                Log.i(tag, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Log debug
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        try {
            if (isDebug())
                Log.d(tag, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Log verbose
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        try {
            if (isDebug())
                Log.v(tag, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Log error
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        try {
            if (isDebug())
                Log.e(tag, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Log warn
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        try {
            if (isDebug())
                Log.w(tag, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}