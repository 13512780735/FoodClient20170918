package com.wbteam.onesearch.app.utils;

import android.view.View;

/**
 * view 操作工具集
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-13 下午11:25:42
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class ViewUtils {
	
	/**
	 * View 可见
	 * 
	 * @param v
	 */
	public static void setVisible(View v) {
		if (v.getVisibility() == View.GONE || v.getVisibility() == View.INVISIBLE) {
			v.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * view 消失不可见
	 * 
	 * @param v
	 */
	public static void setGone(View v) {
		if (v.getVisibility() == View.VISIBLE) {
			v.setVisibility(View.GONE);
		}
	}

	/**
	 * view 消失不可见并且占住位置
	 * 
	 * @param v
	 */
	public static void setInVisible(View v) {
		if (v.getVisibility() == View.VISIBLE) {
			v.setVisibility(View.INVISIBLE);
		}
	}
}
