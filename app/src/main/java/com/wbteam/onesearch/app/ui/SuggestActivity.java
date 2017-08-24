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
 *	@date 2016-9-30下午11:22:59
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
@ContentView(R.layout.activity_suggest)
public class SuggestActivity extends BaseActivity implements OnClickListener{
	
	@ViewInject(R.id.header_title)
	private HeaderLayout headerLayout;
	
	@ViewInject(R.id.edit_name)
	private EditText edit_name;

	@ViewInject(R.id.edit_contact)
	private EditText edit_contact;

	@ViewInject(R.id.edit_content)
	private EditText edit_content;
	
	@ViewInject(R.id.button_send)
	private Button btn_send;
	
	@Override
	public void initIntent() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initListener() {
		headerLayout.setOnLeftImageViewClickListener(new OnLeftClickListener() {
			
			@Override
			public void onClick() {
				finish();
			}
		});
		btn_send.setOnClickListener(this);
	}

	
	@Override
	public void initData() {
		
	}
	
	@OnClick(R.id.button_send)
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_send:
			if (AppContext.getInstance().getUserInfo() != null) {
				String name = edit_name.getText().toString().trim();
				String contact = edit_contact.getText().toString().trim();
				String content = edit_content.getText().toString().trim();

				if (StringUtils.isEmpty(name)) {
					ToastUtils.showToast(context, "联系人不能为空！");
					return;
				}

				if (StringUtils.isEmpty(contact)) {
					ToastUtils.showToast(context, "联系方式不能为空！");
					return;
				}

				if (StringUtils.isEmpty(content)) {
					ToastUtils.showToast(context, "反馈内容不能为空！");
					return;
				}
				sendSuggestContent(name, contact, content);
			} else {
				ToastUtils.showToast(context, "请先登录！");
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * 提交反馈信息
	 * 
	 * @param name
	 * @param contact
	 * @param content
	 */
	private void sendSuggestContent(String name, String contact, String content) {
		if (NetUtils.isOnline()) {
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("ukey", AppContext.getInstance().getUserInfo().getUkey());
			params.put("name", name);
			params.put("mobile", contact);
			params.put("msg", content);
			DialogUtils.showProgressDialogWithMessage(context, "正在发送吐槽中");
			FoodClientApi.post("Msg/msg", params, new JsonResponseCallback<BizResult>() {

				@Override
				public void onSuccess(int statusCode, BizResult body) {
					if (body != null && body.getCode() == 1) {
						ToastUtils.showToast(context, "你的吐槽是我们最大的动力！");
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
