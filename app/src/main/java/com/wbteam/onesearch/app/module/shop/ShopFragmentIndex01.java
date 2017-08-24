package com.wbteam.onesearch.app.module.shop;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.scrollablelayout.ScrollableHelper.ScrollableContainer;
import com.wbteam.app.base.BaseFragmentV4;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.ViewUtils;

/**
 * @autor:码农哥
 * @version:1.0
 * @created:2016-10-8 下午11:43:47
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class ShopFragmentIndex01 extends BaseFragmentV4 implements ScrollableContainer {

    private WebView webView;
    private String contact;
    private ProgressBar app_loading_layout;
    private FrameLayout app_progress_layout;

    private String id = null;

    public ShopFragmentIndex01() {
        super();
    }

    @SuppressLint("ValidFragment")
    public ShopFragmentIndex01(String id) {
        super();
        this.id = id;
    }

    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_webview, container, false);
    }

    @Override
    public void initView(View currentView) {
//		id = getArguments().getString(ShopDetailActivity.BUNDLE_KEY_ID);

        webView = (WebView) currentView.findViewById(R.id.webView);
        app_loading_layout = (ProgressBar) currentView.findViewById(R.id.app_progressbar);
        app_progress_layout = (FrameLayout) currentView.findViewById(R.id.app_progress_layout);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initListener() {
        webView.getSettings().setJavaScriptEnabled(true);// 设置响应js
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// 设置不缓存
        ViewUtils.setVisible(app_loading_layout);
        contact = AppConfig.Shop_Detail_Url + "/" + id;
        webView.loadUrl(contact);
        Logger.e("TAG", "bannerUrl:" + contact);

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
                try {
                    getActivity().setTitle(title);
                    super.onReceivedTitle(view, title);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

//		webView.setOnTouchListener(new OnTouchListener() {  
//		      
//		    @Override  
//		    public boolean onTouch(View v, MotionEvent ev) {  
//		  
//		        ((WebView)v).requestDisallowInterceptTouchEvent(true);  
//		          
//		  
//		        return false;  
//		    }  
//		});
    }

    @Override
    public void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    public View getSlidableView() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public View getScrollableView() {
        return webView;
    }

}
