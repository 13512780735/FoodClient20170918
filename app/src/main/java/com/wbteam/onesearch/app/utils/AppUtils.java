package com.wbteam.onesearch.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.AppContext;

/**
 *  @author 码农哥
 *	@date 2016-3-25下午2:20:12
 *	@email 441293364@qq.com
 *	@TODO
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

public class AppUtils {
	
	public static TipsToast tipsToast;
	
	private static Gson gson;
	
	/**
     * 自定义taost
     *
     * @param iconResId 图片
     * @param tips      提示文字
     */
    public static void showTips(Context context, int iconResId, String tips) {
        if (tipsToast != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                tipsToast.cancel();
            }
            //两种toast加载处理
            if (TipsToast.toastTag != 1) {
                tipsToast.cancel();
                tipsToast = TipsToast.makeTexts(context, tips,
                        TipsToast.LENGTH_SHORT);
            }
        } else {
            tipsToast = TipsToast.makeTexts(context, tips,
                    TipsToast.LENGTH_SHORT);
        }
        tipsToast.show();
        tipsToast.setIcon(iconResId);
        tipsToast.setText(tips);
    }

    /**
     * 显示toast信息
     *
     * @param context
     * @param log
     */
    public static void showToast(Context context, String log) {
        if (tipsToast != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                tipsToast.cancel();
            }
            if (TipsToast.toastTag != 2) {
                tipsToast = TipsToast.showToast(context, log);
            }
        } else {
            tipsToast = TipsToast.showToast(context, log);
        }
        tipsToast.show();
        tipsToast.setMsg(log);
    }
	
	/**
     * 判断字符串是否非空，首尾的空串不考虑
     *
     * @param val
     * @return
     */
    public static boolean isEmpty(String val) {
        return TextUtils.isEmpty(val) || val.trim().length() == 0;
    }

    public static boolean isNotEmpty(String val) {
        return !isEmpty(val);
    }

    public static boolean isNotEmpty(Collection<?> collections) {

        return collections != null && collections.size() > 0;
    }

	
	
	public static String getDeviceId() {
        String deviceId = Preferences.getString(Preferences.Key.DEVICE_ID, null);

        if (AppUtils.isNotEmpty(deviceId)) {
            return deviceId;
        } else {

            String telKey = null;

            try {
                TelephonyManager telephonyMgr = (TelephonyManager) AppContext.getInstance().getSystemService(Context.TELEPHONY_SERVICE);

                telKey = telephonyMgr.getDeviceId();
            } catch (Exception e) {
                if (Logger.isDebug()) {
                	Logger.d("Wbteam", "获取设备唯一标识出错：TelephonyManager.getDeviceId（）");
                }
            }


            if (AppUtils.isEmpty(telKey)) {

                File file = FileUtils.getSaveFile(AppConfig.APP_PATH, ".device_id");
                if (file.exists()) {
                    try {
                        telKey = FileUtils.inputStream2String(new FileInputStream(file));
                    } catch (FileNotFoundException e) {
                        if (Logger.isDebug()) {
                        	Logger.d("Wbteam", "获取设备唯一标识出错：" + file.getAbsolutePath());
                        }
                    }

                    if (AppUtils.isEmpty(telKey)) {
                        telKey = UUID.randomUUID().toString().replace("-", "");

                        try {
                            FileUtils.writeStringToFile(file, telKey);
                        } catch (IOException ex) {
                            if (Logger.isDebug()) {
                            	Logger.d("Wbteam", "获取设备唯一标识出错：" + file.getAbsolutePath());
                            }
                        }
                    }

                }


            }
            deviceId = getMd5(telKey);
            Preferences.saveString(Preferences.Key.DEVICE_ID, deviceId);
            return deviceId;
        }
    }
	
	private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String toHexString(byte[] b) {
        // String to byte
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }
	
	/**
     * 获取字符串的MD5值
     *
     * @param orgs
     * @return
     */
    public static String getMd5(String orgs) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            if (Logger.isDebug()) {
            	Logger.e("Wbteam", "MD5获取出错");
            }
            return null;
        }

        md5.update(orgs.getBytes());
        byte messageDigest[] = md5.digest();
        return toHexString(messageDigest);
    }
    
    private static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }
        return gson;
    }


    public static <T> T fromJson(String jsonData, Class<T> clazz) {
        return fromJson(jsonData, (Type) clazz);
    }

    public static <T> T fromJson(String jsonData, Type type) {
        try {

            T object = getGson().fromJson(jsonData, type);
            return object;
        } catch (Exception ex) {
            if (Logger.isDebug()){
            	Logger.d(ex.getMessage()+"");
            }
            return null;
        }
    }

    public static String toJson(Object object) {
        return getGson().toJson(object);
    }
    
    public static LinearLayout.LayoutParams getParams(Activity context){
		DisplayMetrics displayMetrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int width = displayMetrics.widthPixels;
		double f = 0.3125;
		int height = (int) (width * f);
		LinearLayout.LayoutParams laparms = new LinearLayout.LayoutParams(width, height);
		Logger.d("TAG", "params:" + width + height);
		return laparms;
    }

    public static LinearLayout.LayoutParams getParams2(Activity context){
    	DisplayMetrics displayMetrics = new DisplayMetrics();
    	context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    	int width = displayMetrics.widthPixels;
    	double f = 0.1422;
    	int height = (int) (width * f);
    	LinearLayout.LayoutParams laparms = new LinearLayout.LayoutParams(width, height);
    	Logger.d("TAG", "params:" + width + height);
    	return laparms;
    }
    
    /**
     * 左进启动Activity动画
     *
     * @param cls
     * @param context
     */
    public static void startwithLanim(Activity context, Intent intent, String code) {
        if (null != context && null != intent) {
            if (null == code) {
                context.startActivity(intent);
            } else {
                context.startActivityForResult(intent, Integer.parseInt(code));
            }
        }
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

}
