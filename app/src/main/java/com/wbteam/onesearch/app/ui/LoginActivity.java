package com.wbteam.onesearch.app.ui;

import java.util.HashMap;
import java.util.TreeMap;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import com.alibaba.fastjson.JSON;
import com.wbteam.app.base.BaseActivity;
import com.wbteam.ioc.annotation.ContentView;
import com.wbteam.ioc.annotation.OnClick;
import com.wbteam.ioc.annotation.ViewInject;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.http.JsonResponseCallback;
import com.wbteam.onesearch.app.model.BizResult;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.utils.DialogUtils;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.Preferences;
import com.wbteam.onesearch.app.utils.StringUtils;
import com.wbteam.onesearch.app.utils.ToastUtils;

/**
 *  @author 码农哥
 *	@date 2016-8-27下午11:03:58
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
@ContentView(R.layout.activity_user_login)
public class LoginActivity extends BaseActivity implements OnClickListener, Callback, PlatformActionListener{
	
	private static final int MSG_AUTH_CANCEL = 1;
	private static final int MSG_AUTH_ERROR= 2;
	private static final int MSG_AUTH_COMPLETE = 3;
	
	@ViewInject(R.id.layout_qq_login)
	private RelativeLayout layout_qq_login;

	@ViewInject(R.id.layout_weibo_login)
	private RelativeLayout layout_weibo_login;

	@ViewInject(R.id.layout_weixin_login)
	private RelativeLayout layout_weixin_login;
	
	@ViewInject(R.id.tv_look_res)
	private TextView tv_look_res;
	
	private Handler handler;
	
	@Override
	public void initIntent() {
		// 初始化ui
		ShareSDK.initSDK(context);
		handler = new Handler(this);
	}

	@Override
	public void initListener() {
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@OnClick({R.id.tv_look_res, R.id.layout_qq_login, R.id.layout_weibo_login, R.id.layout_weixin_login })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_qq_login:
			// QQ登录
			Platform qzone = ShareSDK.getPlatform(QQ.NAME);
			authorize(qzone);
			ToastUtils.showToast(context, "QQ登录");
			break;

		case R.id.layout_weibo_login:
			// 新浪微博登录
			// 新浪微博
			Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
			authorize(sina);
			ToastUtils.showToast(context, "新浪微博登录");
			break;

		case R.id.layout_weixin_login:
			// 微信登录
			Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
			authorize(wechat);
			ToastUtils.showToast(context, "微信登录");
			break;
			
		case R.id.tv_look_res:
			startActivity(new Intent(context, MainActivity.class));
			finish();
			break;

		default:
			break;
		}
	}

	// 执行授权,获取用户信息
	// 文档：http://wiki.mob.com/Android_%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E8%B5%84%E6%96%99
	private void authorize(Platform plat) {
//		if (plat == null) {
//			popupOthers();
//			return;
//		}

		plat.setPlatformActionListener(this);
		// 关闭SSO授权
//		plat.SSOSetting(true);
		plat.SSOSetting(false);
//		plat.authorize();
		plat.showUser(null);
	}
	
	@SuppressWarnings("unchecked")
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_AUTH_CANCEL: {
			// 取消授权
			ToastUtils.showToast(context, getString(R.string.auth_cancel));
		}
			break;
		case MSG_AUTH_ERROR: {
			// 授权失败
			ToastUtils.showToast(context, getString(R.string.auth_error));
		}
			break;
		case MSG_AUTH_COMPLETE: {
			// 授权成功
			ToastUtils.showToast(context, getString(R.string.auth_complete));
			Object[] objs = (Object[]) msg.obj;
			String platform = (String) objs[0];
			HashMap<String, Object> res = (HashMap<String, Object>) objs[1];
			Logger.e("TAG", "授权返回的信息1：" + JSON.toJSONString(res));
			Logger.e("TAG", QQ.NAME+"授权返回的信息2：" + platform);
			if(res!=null){
				if (platform.equals(QQ.NAME)) { 
					// QQ认证回调
					userRegister(QQ.NAME, (String) res.get("nickname"), (String) res.get("nickname"), (String) res.get("figureurl_qq_1"));
				} else if (platform.equals(SinaWeibo.NAME)) {
					// 新浪微博认证回调
					userRegister(SinaWeibo.NAME, (String) res.get("name"), (String) res.get("idstr"), (String) res.get("avatar_hd"));
				} else if (platform.equals(Wechat.NAME)) {
					// 微信认证回调
					userRegister(Wechat.NAME, (String)res.get("nickname"), (String)res.get("unionid"), (String)res.get("headimgurl"));
				}
			}else{
				ToastUtils.showToast(context, "获取用户信息失败！");
			}
		}
			break;
		}
		return false;
	}

	@Override
	public void onCancel(Platform platform, int action) {
		if (action == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(MSG_AUTH_CANCEL);
		}
	}

	@Override
	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
		if (action == Platform.ACTION_USER_INFOR) {
			Message msg = new Message();
			msg.what = MSG_AUTH_COMPLETE;
			msg.obj = new Object[] {platform.getName(), res};
			handler.sendMessage(msg);
		}
	}

	@Override
	public void onError(Platform platform, int action, Throwable t) {
		if (action == Platform.ACTION_USER_INFOR) {
			handler.sendEmptyMessage(MSG_AUTH_ERROR);
		}
		t.printStackTrace();
	}

	/**
	 * 用户注册
	 * @param name
	 * @param faceUrl
	 */
	private void userRegister(String type,final String name,  String uid, final String faceUrl) {
		if (NetUtils.isOnline()) {
			Logger.e("TAG", "userRegister");
			
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("type", type);
			params.put("third_uid", uid);
			params.put("nickname", name);
			params.put("avatar", faceUrl);
			DialogUtils.showProgressDialogWithMessage(context, "正在注册中");
			FoodClientApi.post("Login/third_login", params, new JsonResponseCallback<BizResult>() {

				@Override
				public void onSuccess(int statusCode, BizResult body) {
					try {
						if (body != null && body.getCode() == 1) {
							JSONObject jsonObject = new JSONObject(body.getData());
							String ukey = jsonObject.getString("ukey");
							Logger.e("TAG", "ukey:"+ukey);
							UserInfo userInfo = new UserInfo();
							userInfo.setAvatar(faceUrl);
							userInfo.setNickname(name);
							userInfo.setUkey(ukey);
							
							String lng = Preferences.getString("lng", "", context);// 经度
							String lat = Preferences.getString("lat", "", context);// 纬度
							if(StringUtils.notBlank(lng)&&StringUtils.notBlank(lat)){
								userInfo.setLat(lat);
								userInfo.setLng(lng);
							}
							AppContext.doLogin(userInfo);
							startActivity(new Intent(context, MainActivity.class));
							finish();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					DialogUtils.dismiss();
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					super.onFailure(arg0, arg1, arg2, arg3);
					DialogUtils.dismiss();
					ToastUtils.showToast(context, "当前无网络连接");
				}
			});
		} else {
			ToastUtils.showToast(context, "当前无网络连接");
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(context);  
	}
	
}
