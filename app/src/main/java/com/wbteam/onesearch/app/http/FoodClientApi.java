package com.wbteam.onesearch.app.http;

import java.util.TreeMap;

import com.loopj.android.http.RequestParams;
import com.wbteam.onesearch.app.model.BizResult;
import com.wbteam.onesearch.app.utils.EncoderHandler;

/**
 * 食More客户端API
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-9  下午3:58:58
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class FoodClientApi {
	
	public static void post(String url, TreeMap<String, String> mapParams, JsonResponseCallback<BizResult> callback) {
		RequestParams params = new RequestParams();
		params.put("sign", EncoderHandler.createSignString(mapParams));
		params.put("data", EncoderHandler.getDataParams(mapParams));
		ApiHttpClient.post(url, params, callback);
	}

	public static void post(String url, TreeMap<String, String> mapParams, MyTextHttpResponseHandler callback) {
		RequestParams params = new RequestParams();
		params.put("sign", EncoderHandler.createSignString(mapParams));
		params.put("data", EncoderHandler.getDataParams(mapParams));
		ApiHttpClient.post(url, params, callback);
	}

}
