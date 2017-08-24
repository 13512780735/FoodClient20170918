package com.wbteam.app.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.wbteam.ioc.utils.InjectUtils;
import com.wbteam.onesearch.app.AppManager;

/**
 * Activity基类
 * 
 * @autor:Bin
 * @version:1.0
 * @created:2016-6-7 上午11:28:07
 * @contact:QQ-441293364 TEL-15105695563
 **/
public abstract class BaseActivity extends FragmentActivity {
	
	private static final String TAG = BaseActivity.class.getName();

	public Context context;
	public ProgressDialog parentDialog;
	private static long lastClickTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "Activity life cycle onCreate " + getClass().getSimpleName());
		// 注解框架初始化
		context = this;
		InjectUtils.inject(this);
//		setContentView();
		initIntent();
		initListener();
		// initView();
		initProgress();
		initData();
		AppManager.getInstance().addActivity(this);

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			if (isFastDoubleClick()) {
				return true;
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (timeD >= 0 && timeD <= 200) {
			return true;
		} else {
			lastClickTime = time;
			return false;
		}
	}

	private void initProgress() {
		parentDialog = new ProgressDialog(context);
	}

	/**
	 * TODO 设置界面视图
	 */
	//public abstract void setContentView();

	/**
	 * TODO 初始化监听事件
	 */
	 public abstract void initListener();

	/**
	 * TODO 初始化View
	 */
	// public abstract void initView();

	/**
	 * TODO 初始化数据传输意图
	 */
	public abstract void initIntent();

	/**
	 * TODO 初始化数据
	 */
	public abstract void initData();

	protected void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Activity life cycle onDestroy " + getClass().getSimpleName());
		AppManager.getInstance().finishActivity(this);
	}

}
