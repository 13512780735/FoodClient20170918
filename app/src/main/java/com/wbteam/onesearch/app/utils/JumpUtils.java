package com.wbteam.onesearch.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.wbteam.onesearch.R;

/**
 * 页面跳转工具类
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-13 下午11:27:53
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class JumpUtils {

	/**
	 * 跳转至手机拨号页面（仅拨打公司热线电话）
	 * 
	 * @param context
	 */
	public static void callPhone(Activity context,String phone_num) {
		try {
			Intent intent = new Intent(Intent.ACTION_DIAL);
			Uri data = Uri.parse("tel:" + phone_num);
			intent.setData(data);
			AppUtils.startwithLanim(context, intent, null);
		} catch (Exception e) {
			AppUtils.showToast(context, "该设备不支持此功能");
		}

	}

}
