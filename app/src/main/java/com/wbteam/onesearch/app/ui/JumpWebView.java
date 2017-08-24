package com.wbteam.onesearch.app.ui;


import java.util.TreeMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wbteam.app.base.BaseActivity;
import com.wbteam.ioc.annotation.ContentView;
import com.wbteam.ioc.annotation.ViewInject;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.file.cache.Imageloader;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.http.JsonResponseCallback;
import com.wbteam.onesearch.app.model.BizResult;
import com.wbteam.onesearch.app.model.DishModel;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.module.shop.ShopDetailActivity;
import com.wbteam.onesearch.app.utils.DialogUtils;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.Preferences;
import com.wbteam.onesearch.app.utils.ViewUtils;
import com.wbteam.onesearch.app.weight.HeaderLayout;
import com.wbteam.onesearch.app.weight.HeaderLayout.OnRightClickListener;

@ContentView(R.layout.activity_webview)
public class JumpWebView extends BaseActivity {
	
	@ViewInject(R.id.header_title)
    private HeaderLayout headerLayout;

	@ViewInject(R.id.webView)
	private WebView webView;

	@ViewInject(R.id.loading_view)
	private FrameLayout loading_view;

	@ViewInject(R.id.app_progressbar)
	private ProgressBar app_loading_layout;
	
	private String title = null;
    private String contact;
    
    @ViewInject(R.id.bottom_view)
    private RelativeLayout bottom_view;
    
    @ViewInject(R.id.iv_user_avatar)
    private ImageView iv_user_avatar;

    @ViewInject(R.id.tv_recommend_name)
	private TextView tv_recommend_name;
	
    @ViewInject(R.id.tv_recommend_distance)
    private TextView tv_recommend_distance;
	
    @ViewInject(R.id.tv_recommend_address)
    private TextView tv_recommend_address;
    
    private Imageloader mImageloader = null;
    private UserInfo mUserInfo = null;
    
    private DishModel dishModel = null;
    private String id = null;
    
    @Override
    public void initIntent() {
    	mUserInfo = AppContext.getInstance().getUserInfo();
    	contact = getIntent().getStringExtra("contact");
    	id = getIntent().getStringExtra("id");
    }
    
    @Override
    public void initData() {
		mImageloader = Imageloader.getInstance(context);
		
		sendData();
		
    	webView.getSettings().setJavaScriptEnabled(true);//设置响应js
    	webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);//设置不缓存
    	ViewUtils.setVisible(loading_view);
    	ViewUtils.setVisible(app_loading_layout);
        headerLayout.setMidText(title);
        webView.loadUrl(contact);
        webView.setWebViewClient(new WebViewClient() {
            // url拦截
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                if (url.indexOf("tel:") >= 0) {
//                    JumpUtil.callPhone(JumpWebView.this);
                } else {
            		ViewUtils.setVisible(loading_view);
                    ViewUtils.setVisible(app_loading_layout);
                    view.loadUrl(url);
                }
                // 相应完成返回true
                return true;
                //return super.shouldOverrideUrlLoading(view, url);
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
                ViewUtils.setGone(loading_view);
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
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            // 处理javascript中的confirm
            public boolean onJsConfirm(WebView view, String url,
                                       String message, final JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            // 处理javascript中的prompt
            public boolean onJsPrompt(WebView view, String url, String message,
                                      String defaultValue, final JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            //设置网页加载的进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            //设置程序的Title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                setTitle(title);
                super.onReceivedTitle(view, title);
            }
        });

    }

    @Override
    public void initListener() {
        headerLayout.setOnLeftImageViewClickListener(new HeaderLayout.OnLeftClickListener() {
            @Override
            public void onClick() {
                back();
            }
        });
        
		bottom_view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(context, ShopDetailActivity.class);
				mIntent.putExtra("shop_id", dishModel.getRid());
				startActivity(mIntent);
			}
		});
	}
    
	private void sendData() {
		if (NetUtils.isOnline()) {
			TreeMap<String, String> params = new TreeMap<String, String>();
			if (null != mUserInfo) {
				params.put("ukey", mUserInfo.getUkey());
				params.put("lng", mUserInfo.getLng());// 经度
				params.put("lat", mUserInfo.getLat());// 纬度
			} else {
				params.put("ukey", "");
				params.put("lng", Preferences.getString("lng", "", context));// 经度
				params.put("lat", Preferences.getString("lat", "", context));// 纬度
			}
			params.put("id", id);
			params.put("ad_id", "");
			FoodClientApi.post("News/detail", params, new JsonResponseCallback<BizResult>() {

				@Override
				public void onSuccess(int statusCode, BizResult body) {
					Logger.e("TAG", "====" + JSON.toJSONString(body));
					if (body != null && body.getCode() == 1) {
							dishModel = JSON.parseObject(body.getData(), DishModel.class);
					
						if (dishModel != null) {
							ViewUtils.setVisible(bottom_view);
							headerLayout.setMidText(dishModel.getRtitle());
							mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + dishModel.getRlogo(), iv_user_avatar);
							tv_recommend_address.setText("地址:" + dishModel.getAddress());
							try {
								if (Long.valueOf(dishModel.getDistance()) > 1000) {
									tv_recommend_distance.setText(Long.valueOf(dishModel.getDistance()) / 1000 + "KM");
								} else {
									tv_recommend_distance.setText(dishModel.getDistance() + "M");
								}
							} catch (Exception e) {
								tv_recommend_distance.setText(dishModel.getDistance());
							}
							tv_recommend_name.setText(dishModel.getRtitle());
							
							headerLayout.setRightImage(R.drawable.icon_share_white);
							headerLayout.setOnRightImageViewClickListener(new OnRightClickListener() {
								
								@Override
								public void onClick() {
									DialogUtils.showShare(JumpWebView.this, dishModel.getLogo(), dishModel.getTitle(),dishModel.getDesc(),contact+"&app=1");
								}
							});
						}
					}
				}
			});
		} else {

		}
	}
    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    /**
     * 拦截返回键  如果网页可以返回就响应返回  不可以正常响应
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
