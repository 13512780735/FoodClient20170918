package com.wbteam.onesearch.app.module.webview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.wbteam.app.base.BaseActivity;
import com.wbteam.ioc.annotation.ContentView;
import com.wbteam.ioc.annotation.ViewInject;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.utils.JumpUtils;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.ViewUtils;
import com.wbteam.onesearch.app.weight.HeaderLayout;

/**
 * Created by user on 2015/10/20.
 */
@SuppressLint("SetJavaScriptEnabled")
@ContentView(R.layout.layout_webview)
public class BannerWebView extends BaseActivity {

	@ViewInject(R.id.header_title)
	private HeaderLayout headerLayout;

	@ViewInject(R.id.webView)
	private WebView webView;

	private String contact;

	@ViewInject(R.id.app_progressbar)
	private ProgressBar app_loading_layout;
	
	@ViewInject(R.id.app_progress_layout)
	private FrameLayout app_progress_layout;

	@Override
	public void initIntent() {
		contact = getIntent().getStringExtra("bannerUrl");
	}

	@Override
	public void initListener() {
		headerLayout.setOnLeftImageViewClickListener(new HeaderLayout.OnLeftClickListener() {
			@Override
			public void onClick() {
				back();
			}
		});
		
		webView.getSettings().setJavaScriptEnabled(true);// 设置响应js
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// 设置不缓存
		ViewUtils.setVisible(app_loading_layout);
		webView.loadUrl(contact);
		Logger.e("TAG", "bannerUrl:"+contact);
		
		
		webView.setWebViewClient(new WebViewClient() {
			// url拦截
			@Override
			public boolean shouldOverrideUrlLoading(final WebView view, String url) {
				// 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
				if (url.indexOf("tel:") >= 0) {
					//JumpUtils.callPhone(BannerWebView.this);
				} else {
					ViewUtils.setVisible(app_loading_layout);
					view.loadUrl(url);
				}
				// 相应完成返回true
				return true;
				// return super.shouldOverrideUrlLoading(view, url);
			}

			// 页面开始加载
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			// 页面加载完成
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				ViewUtils.setGone(app_progress_layout);
				ViewUtils.setGone(app_loading_layout);
			}

			// WebView加载的所有资源url
			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

		});

		// 设置WebChromeClient
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			// 处理javascript中的alert
			public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}


			@Override
			// 处理javascript中的confirm
			public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
				return super.onJsConfirm(view, url, message, result);
			}


			@Override
			// 处理javascript中的prompt
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}


			// 设置网页加载的进度条
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
			}

			// 设置程序的Title
			@Override
			public void onReceivedTitle(WebView view, String title) {
				setTitle(title);
				headerLayout.setMidText("" + title);
				super.onReceivedTitle(view, title);
			}
		});
	}

	@Override
	public void initData() {
	}

	@Override
	public void onPause() {
		super.onPause();
		webView.onPause();
	}

	/**
	 * 拦截返回键 如果网页可以返回就响应返回 不可以正常响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 是否触发按键为back键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back();
			return true;
			// 如果不是back键正常响应
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void back() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			finish();
		}
	}

}
