package com.wbteam.onesearch.app.http;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.utils.DialogUtils;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.ToastUtils;

/**
 * 网络交互数据返回拦截句柄
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-5-27 上午10:34:53
 * @contact:QQ-441293364 TEL-15105695563
 **/
@SuppressWarnings("deprecation")
public abstract class JsonResponseCallback<T> extends AsyncHttpResponseHandler {

	public abstract void onSuccess(int statusCode, T body);

	@Override
	public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
		// TODO　失败
		DialogUtils.dismiss();
		ToastUtils.showToast(AppContext.getInstance().getBaseContext(), "失败,当前无网络连接");
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
		// TODO　成功
		if (responseBody == null || responseBody.length == 0) {
			this.onSuccess(statusCode, null);
		} else {
			Class<?> c = getClass();

			Type type = c.getGenericSuperclass();

			type = ((ParameterizedType) type).getActualTypeArguments()[0];
			String response = new String(responseBody);

			if (Logger.isDebug()) {
				Logger.d("成功：" + response);
			}

			T body = JSON.parseObject(response, type);// JsonParseUtils.fromJson(response,
														// type);
			this.onSuccess(statusCode, body);
		}
	}
}
