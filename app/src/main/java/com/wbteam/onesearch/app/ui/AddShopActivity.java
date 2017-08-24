package com.wbteam.onesearch.app.ui;

import java.util.TreeMap;

import org.apache.http.Header;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.wbteam.app.base.BaseActivity;
import com.wbteam.ioc.annotation.ContentView;
import com.wbteam.ioc.annotation.OnClick;
import com.wbteam.ioc.annotation.ViewInject;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.http.JsonResponseCallback;
import com.wbteam.onesearch.app.model.BizResult;
import com.wbteam.onesearch.app.utils.DialogUtils;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.StringUtils;
import com.wbteam.onesearch.app.utils.ToastUtils;
import com.wbteam.onesearch.app.weight.HeaderLayout;
import com.wbteam.onesearch.app.weight.HeaderLayout.OnLeftClickListener;

/**
 *  @author 码农哥
 *	@date 2016-12-19上午12:03:46
 *	@email 441293364@qq.com
 *	@TODO 我要进驻
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
@ContentView(R.layout.activity_addshop)
public class AddShopActivity extends BaseActivity implements OnClickListener{
	
	@ViewInject(R.id.header_title)
	private HeaderLayout headerLayout;
	
	@ViewInject(R.id.edit_shop_name)
	private EditText edit_shop_name;
	
	@ViewInject(R.id.edit_name)
	private EditText edit_name;
	
	@ViewInject(R.id.edit_phone_num)
	private EditText edit_phone_num;
	
	@ViewInject(R.id.edit_shop_desc)
	private EditText edit_shop_desc;

	@ViewInject(R.id.button_send)
	private Button button_send;
	
	@Override
	public void initListener() {
		headerLayout.setOnLeftImageViewClickListener(new OnLeftClickListener() {
			
			@Override
			public void onClick() {
				finish();
			}
		});
		
		button_send.setOnClickListener(this);
	}

	@Override
	public void initIntent() {
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	@OnClick(R.id.button_send)
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_send:
			if (AppContext.getInstance().getUserInfo() != null) {
				String shop_name = edit_shop_name.getText().toString().trim();
				String name = edit_name.getText().toString().trim();
				String phone_num = edit_phone_num.getText().toString().trim();
				String shop_desc = edit_shop_desc.getText().toString().trim();

				if (StringUtils.isEmpty(shop_name)) {
					ToastUtils.showToast(context, "餐厅名称不能为空！");
					return;
				}

				if (StringUtils.isEmpty(name)) {
					ToastUtils.showToast(context, "姓名不能为空！");
					return;
				}

				if (StringUtils.isEmpty(phone_num)) {
					ToastUtils.showToast(context, "手机号不能为空！");
					return;
				}
				sendAddShop(shop_name,name, phone_num, shop_desc);
			} else {
				ToastUtils.showToast(context, "请先登录！");
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 发送入驻餐厅信息
	 * 
	 * @param shop_name
	 * @param name
	 * @param phone_num
	 * @param shop_desc
	 */
	private void sendAddShop(String shop_name, String name, String phone_num, String shop_desc) {
		if (NetUtils.isOnline()) {
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("ukey", AppContext.getInstance().getUserInfo().getUkey());
			params.put("title", shop_name);
			params.put("name", name);
			params.put("mobile", phone_num);
			params.put("desc", shop_desc);
			DialogUtils.showProgressDialogWithMessage(context, "正在提交进驻信息");
			FoodClientApi.post("Res/inshop", params, new JsonResponseCallback<BizResult>() {

				@Override
				public void onSuccess(int statusCode, BizResult body) {
					if (body != null && body.getCode() == 1) {
						ToastUtils.showToast(context, "进驻信息提交成功!");
						finish();
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
			// 网络不可用
			ToastUtils.showToast(context, "当前无网络连接");
		}
	}

}
