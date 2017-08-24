package com.wbteam.onesearch.app;

import im.fir.sdk.FIR;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.location.service.LocationService;
import com.baidu.mapapi.SDKInitializer;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.utils.ImageLoaderConfig;
import com.wbteam.onesearch.app.utils.PathUtils;
import com.wbteam.onesearch.app.utils.Preferences;
import com.wbteam.onesearch.app.utils.Storage;

/** 33:BA:88:DB:E5:C9:67:49:A8:EB:24:73:5E:47:58:69:75:44:E1:E6
 *  @author 码农哥
 *	@date 2016-7-15下午6:26:08
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

public class AppContext extends Application{
	
	private static AppContext appContext;

	private ExecutorService executorService;
	
	private UserInfo userInfo = null;
	
	// 百度定位服务
	public LocationService locationService;
	
	public Vibrator mVibrator;

	public static AppContext getInstance() {
		if (appContext == null) {
			return new AppContext();
		} else {
			return appContext;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		FIR.init(this);
		/***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		SDKInitializer.initialize(this);
        
        //初始化缓存路径
        ImageLoaderConfig.initImageLoader(this, PathUtils.getCachePath());
	}

	public ExecutorService getDefaultThreadPool() {
		if (executorService == null) {
			executorService = Executors.newFixedThreadPool(5);
		}
		return executorService;
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public UserInfo getUserInfo() {
		if (userInfo == null)
			init();
		return userInfo;
	}
	
	private void init() {
		userInfo = Storage.getObject(AppConfig.USER_INFO, UserInfo.class);
		
	}
	
	/**
	 * 登录信息的保存
	 * 
	 * @param user
	 */
	public static void doLogin(UserInfo user) {
        Storage.saveObject(AppConfig.USER_INFO, user);
        Preferences.saveString(AppConfig.USER_ID, user.getUkey(),getInstance());
        AppContext.getInstance().init();
    }

	/**
	 * 清除登录信息(退出账号)
	 */
	public static void doLogout() {
        Storage.clearObject(AppConfig.USER_INFO);
        AppContext.getInstance().init();
    }
}
