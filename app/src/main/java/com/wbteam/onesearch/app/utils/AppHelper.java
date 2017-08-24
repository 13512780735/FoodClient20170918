package com.wbteam.onesearch.app.utils;

import org.apache.http.Header;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wbteam.onesearch.app.http.ApiHttpClient;
import com.wbteam.onesearch.app.model.VersionModel;

/**
 * FIR检测版本操作
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-11-9 下午3:10:25
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class AppHelper {

	private static final String appId = "57e5dc15ca87a865a1000186";//"57accf17ca87a83cd2000067";
	private static final String token = "b228e7945bb013532f9890e0a35300c3";

	public static void checkAppVersion(final Context context, final onCheckVersionListener listener) {
		String baseUrl = "http://fir.im/api/v2/app/version/%s?token=%s";
		String checkUpdateUrl = String.format(baseUrl, appId, token);
		ApiHttpClient.get(checkUpdateUrl, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				try {
					String result = new String(arg2);
					Logger.d("Fir", result);
					VersionModel mVersionModel = JSON.parseObject(result, VersionModel.class);
					if (mVersionModel != null) {
						PackageManager pm = context.getPackageManager();
						PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
						if (pi != null) {
							int currentVersionCode = pi.versionCode;
							String currentVersionName = pi.versionName;
							if (mVersionModel.getVersion() > currentVersionCode) {
								// 需要更新
								Logger.d("Fir", "need update");
								listener.onNeedUpdateListener(mVersionModel.getInstall_url(), mVersionModel.getChangelog());
							} else if (mVersionModel.getVersion() == currentVersionCode) {
								// 如果本地app的versionCode与FIR上的app的versionCode一致，则需要判断versionName.
								if (!currentVersionName.equals(mVersionModel.getVersionShort())) {
									Logger.d("Fir", "need update");
									listener.onNeedUpdateListener(mVersionModel.getInstall_url(), mVersionModel.getChangelog());
								}else{
									Logger.d("Fir", "no need update");
									listener.onTargetVersionListener();
								}
							} else {
								// 不需要更新,当前版本高于FIR上的app版本.
								Logger.d("Fir", "no need update");
								listener.onTargetVersionListener();
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				String errorMsg = new String(arg2);
				Logger.e("Fir", errorMsg);
			}
		});
	}
	

	public interface onCheckVersionListener {

		void onNeedUpdateListener(String url, String log);

		void onTargetVersionListener();
	}

}
