package com.wbteam.onesearch.app.http;


import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.utils.Logger;

/**
 * 网络数据传输操作类
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-5-27 上午10:20:05
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class ApiHttpClient {
	
	private static AsyncHttpClient client;

	private static final int RESPONSE_TIMEOUT = 40 * 1000; // 响应超时：40秒
    
    private final static String HTTP = "http://";

    private final static String HTTPS = "https://";
    
    public final static String HOST = "";

    private final static String SERVER_URL = "http://onesearch.wbteam.cn/index.php?s=/Out/";
	
    /**
     * 拼接完整的请求地址
     * 
     * @param path
     * @return
     */
    public final static String getRemoteUrl(String path) {
        return SERVER_URL + path;
    }
    
    /**
     * 获取完整的请求地址
     * 
     * @param partUrl
     * @return
     */
    public static String getAbsoluteApiUrl(String partUrl) {
        String url = partUrl;
        if (!TextUtils.isEmpty(partUrl) && !partUrl.startsWith(HTTP) && !partUrl.startsWith(HTTPS)) {
            url = getRemoteUrl(partUrl);
        }

        return url;
    }
    
    public static AsyncHttpClient getClient() {
		if (client == null) {
			setClient(new AsyncHttpClient());
		}
		return client;
	}
    
    
	public static void setClient(AsyncHttpClient asyncHttpClient) {
		if (asyncHttpClient == null) {
			return;
		}
		client = asyncHttpClient;
		client.setTimeout(RESPONSE_TIMEOUT);
		client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
		client.setThreadPool(AppContext.getInstance().getDefaultThreadPool());
	}
	
	public static void get(String url, AsyncHttpResponseHandler responseHandler){
		getClient().get(url, responseHandler);
	}

    /**
     * POST 请求
     *
     * @param url
     * @param params
     * @param handler
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        post(null, url, params, handler);
    }

    /**
     * GET 请求
     *
     * @param url
     * @param params
     * @param handler
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        get(null, url, params, handler);
    }


    public static void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler handler) {
        if (Logger.isDebug()) {
        	Logger.d(new StringBuilder(context == null ? "GET:" : context.getClass().getName() + ", GET: ").append(url).append(" & ").append(params).toString());
        }
        getClient().get(context, getAbsoluteApiUrl(url), params, handler);
    }


    public static void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler handler) {
        if (Logger.isDebug()) {
        	Logger.d(new StringBuilder(context == null ? "POST:" : context.getClass().getName() + ", POST: ").append(url).append(" & ").append(params).toString());
        }
        getClient().post(context, getAbsoluteApiUrl(url), params, handler);
    }


//    public static void post(Context context, String url, Header[] headers, RequestParams params, AsyncHttpResponseHandler handler) {
//        if (Logger.isDebug()) {
//        	Logger.d(new StringBuilder(context == null ? "POST:" : context.getClass().getName() + ", POST: ").append(url).append(" &Headers:").append((headers == null || headers.length == 0) ? "nil" : headers[0]).append(" &Param: ").append(params).toString());
//        }
//        
//        getClient().post(context, getAbsoluteApiUrl(url), headers, params, "application/x-www-form-urlencoded", handler);
//    }
//
//
//    public static void post(String url, Header[] headers, RequestParams params, AsyncHttpResponseHandler handler) {
//        post(null, url, headers, params, handler);
//    }

    public void cancel(Context context) {
        getClient().cancelRequests(context, true);
    }

    public void cancelAll(Context context) {
        getClient().cancelAllRequests(true);
    }

}
