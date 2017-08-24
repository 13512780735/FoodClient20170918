package com.wbteam.onesearch.app.ui;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.app.base.BaseActivity;
import com.wbteam.ioc.annotation.ContentView;
import com.wbteam.ioc.annotation.ViewInject;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.http.JsonResponseCallback;
import com.wbteam.onesearch.app.model.BizResult;
import com.wbteam.onesearch.app.model.banner.Banner;
import com.wbteam.onesearch.app.module.webview.BannerWebView;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.ToastUtils;

/**
 *  @author 码农哥
 *	@date 2016-4-12下午4:25:11
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
@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.tv_start_cancel)
	private TextView tv_cancel;
	
	@ViewInject(R.id.iv_logo)
	private ImageView iv_logo;

	public TimerTask task;
	private int time = 5;
	private Timer timer = new Timer();
	
	@Override
	public void initIntent() {
	}
	
	@Override
	public void initListener() {
		tv_cancel.getBackground().setAlpha(50);
		tv_cancel.setOnClickListener(this);
		iv_logo.setOnClickListener(this);
	}
	
	@Override
	public void initData() {
		sendRequest();
	}
	
	private void sendRequest() {
		if (NetUtils.isOnline()) {
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("cat_id", "1");
			params.put("create_time", System.currentTimeMillis() / 1000 + "");
			FoodClientApi.post("Ad/adList", params, new JsonResponseCallback<BizResult>() {

				@Override
				public void onSuccess(int statusCode, BizResult body) {
					Logger.e("TAG", "广告信息：" + JSON.toJSONString(body));
					if (body.getCode() == 1) {
						final List<Banner> bannerList = JSON.parseArray(body.getData(), Banner.class);
						if (bannerList != null) {
							//Imageloader.getInstance(context).DisplayImage(AppConfig.IMAGE_URL_HOST+bannerList.get(0).getPic(), iv_logo);
							ImageLoader.getInstance().displayImage(AppConfig.IMAGE_URL_HOST+bannerList.get(0).getPic(), iv_logo);
							
							
							iv_logo.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Intent intent = new Intent(context, BannerWebView.class);
									intent.putExtra("bannerUrl", bannerList.get(0).getSlide_url());
									context.startActivity(intent);
								}
							});
						}
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					super.onFailure(statusCode, headers, responseBody, error);
				}
			});
		} else {
			ToastUtils.showToast(context, "当前无可用网络链接");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		startTimerTask();
	}
	
	/**
	 * 开始定时任务
	 */
	private void startTimerTask() {
		task = new TimerTask() {

			@Override
			public void run() {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (time <= 0) {
							try {
								task.cancel();
								timer.cancel();
							} catch (Exception e) {
								e.printStackTrace();
							}
							redirectTo();
						} else {
							tv_cancel.setText("跳过  " + (time--) + "S");
						}
					}
				});
			}
		};

		this.timer.schedule(task, 0, 1000);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (task != null) {
			task.cancel();
			task = null;
		}
	}

	
	
	/**
	 * 启动页结束时的跳转
	 */
	private void redirectTo() {
		if (AppContext.getInstance().getUserInfo() != null) {
			Intent intent = new Intent(context, MainActivity.class);
			startActivity(intent);
			finish();
		} else {
			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_start_cancel:
			// 跳过
			this.timer.cancel();
			this.tv_cancel.setVisibility(View.GONE);
			redirectTo();
			break;

		case R.id.iv_logo:
			// TODO 直接跳转到广告
			break;

		default:
			break;
		}
	}
	
}
