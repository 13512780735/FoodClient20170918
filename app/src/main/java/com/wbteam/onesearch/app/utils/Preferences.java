package com.wbteam.onesearch.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wbteam.onesearch.app.AppContext;
/**
 *  @author 码农哥
 *	@date 2016-3-25下午1:50:07
 *	@email 441293364@qq.com
 *	@TODO SharPreference工具集实现存储值、获取保存的值
 * 		   int long string boolean
 *
 *  ** *** ━━━━━━神兽出没━━━━━━
 *  ** ***       ┏┓　　  ┏┓
 *  ** *** 	   ┏┛┻━━━┛┻┓
 *  ** *** 　  ┃　　　　　　　┃
 *  ** *** 　　┃　　　━　　　┃
 *  ** *** 　　┃　┳┛　┗┳　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┃　　　┻　　　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┗━┓　　　┏━┛
 *  ** *** 　　　　┃　　　┃ 神兽保佑,代码永无bug
 *  ** *** 　　　　┃　　　┃
 *  ** *** 　　　　┃　　　┗━━━┓
 *  ** *** 　　　　┃　　　　　　　┣┓
 *  ** *** 　　　　┃　　　　　　　┏┛
 *  ** *** 　　　　┗┓┓┏━┳┓┏┛
 *  ** *** 　　　　  ┃┫┫  ┃┫┫
 *  ** *** 　　　　  ┗┻┛　┗┻┛
 */
public class Preferences {

    public  static class Key {
        public  final static  String DEVICE_ID = "Guanai_Device_Id";
    }

    private static SharedPreferences instance;

    public static SharedPreferences getInstance(Context... contexts) {
        if (instance == null) {
            Context ctx = null;
            if (contexts == null || contexts.length == 0) {
                ctx = AppContext.getInstance();
            } else {
                ctx = contexts[0];
            }
            instance = PreferenceManager.getDefaultSharedPreferences(ctx);
        }
        return instance;
    }

    /**
     * 保存String类型值
     *
     * @param key
     * @param values
     * @param context
     */
    public static void saveString(String key,String values, Context... context) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putString(key, values);
        editor.commit();
    }

    /**
     * 获取String类型值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key,
                                   String defValue, Context... context) {
        return getInstance()
                .getString(key, defValue);
    }

    /**
     * 保存int类型值
     *
     * @param context
     * @param key
     * @param values
     */
    public static void saveInt( String key,
                               int values, Context  ...context) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putInt(key, values);
        editor.commit();

    }

    /**
     * 获取int类型值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt( String key,
                                        int defValue, Context ... context) {
        return getInstance(context).getInt(key, defValue);
    }

    /**
     * 保存boolean类型值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean( String key,
                                              Boolean value, Context ...context) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    /**
     * 获取boolean类型值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Boolean getBoolean(String key,
                                     Boolean defValue, Context... context) {
        return getInstance(context)
                .getBoolean(key, defValue);
    }

    /**
     * 保存long类型值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveLong(String key,
                                           Long value, Context... context) {
        SharedPreferences.Editor editor = getInstance(context).edit();
        editor.putLong(key, value);
        editor.commit();

    }

    /**
     * 获取long类型值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Long getLong(String key,
                               Long defValue, Context... context) {
        return getInstance(context).getLong(
                key, defValue);
    }
}
