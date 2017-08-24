package com.wbteam.onesearch.app.utils;


import android.content.Context;
import android.os.Build;

/**
 *  @author 码农哥
 *	@date 2016-8-5下午4:12:51
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

public class ToastUtils {

	public static TipsToast tipsToast;

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
}
