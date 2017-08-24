package com.wbteam.onesearch.app.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wbteam.app.base.BaseActivity;
import com.wbteam.ioc.annotation.ContentView;
import com.wbteam.ioc.annotation.OnClick;
import com.wbteam.ioc.annotation.ViewInject;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.adapter.DishAdapter;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.http.JsonResponseCallback;
import com.wbteam.onesearch.app.model.BizResult;
import com.wbteam.onesearch.app.model.DishModel;
import com.wbteam.onesearch.app.model.ProvinceModel;
import com.wbteam.onesearch.app.model.StyleModel;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.module.shop.ShopDetailActivity;
import com.wbteam.onesearch.app.utils.AreaDialog;
import com.wbteam.onesearch.app.utils.DialogUtils;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.Preferences;
import com.wbteam.onesearch.app.utils.StringUtils;
import com.wbteam.onesearch.app.utils.ToastUtils;

/**
 * 搜索
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-25 上午5:36:02
 * @contact:QQ-441293364 TEL-15105695563
 **/
@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.iv_back_icon)
	private ImageView iv_back_icon;

	@ViewInject(R.id.search_content_edit)
	private EditText search_content_edit;

	@ViewInject(R.id.tv_search)
	private TextView tv_search;

	@ViewInject(R.id.view_area_layout)
	private RelativeLayout view_area_layout;

	@ViewInject(R.id.view_nearby_layout)
	private RelativeLayout view_nearby_layout;

	@ViewInject(R.id.view_style_layout)
	private RelativeLayout view_style_layout;

	// @ViewInject(R.id.view_filter_style)
	// private LinearLayout view_filter_style;
	
	@ViewInject(R.id.listview)
	private ListView mListView;
	private DishAdapter mAdapter = null;

	@ViewInject(R.id.tv_style_title)
	private TextView tv_style_title;

	private UserInfo mUserInfo = null;
	
	private String ukey="";
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			String search_content = search_content_edit.getText().toString().trim();
			searchShopList(search_content);
		};
	};

	@Override
	public void initListener() {
		mUserInfo = AppContext.getInstance().getUserInfo();
		if(mUserInfo!=null){
			ukey = mUserInfo.getUkey();
		}
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
					if (mAdapter != null) {
						DishModel dishModel = mAdapter.getItem(position);
						if (dishModel != null) {
							Intent mIntent = new Intent(context, ShopDetailActivity.class);
							mIntent.putExtra("shop_id", dishModel.getId());
							startActivity(mIntent);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void initIntent() {

	}

	@Override
	public void initData() {
		initSearchData();
	}

	private void initSearchData() {
		if (NetUtils.isOnline()) {
			TreeMap<String, String> params = new TreeMap<String, String>();
			if (null != mUserInfo) {
				params.put("ukey", mUserInfo.getUkey());
			} else {
				params.put("ukey", "");
			}
			FoodClientApi.post("Index/search_type", params, new JsonResponseCallback<BizResult>() {

				@Override
				public void onSuccess(int statusCode, BizResult body) {
					if (body != null && body.getCode() == 1) {
						JSONObject jsonObject = JSONObject.parseObject(body.getData());
						mStyleModels = JSON.parseArray(jsonObject.getString("style"), StyleModel.class);
						StyleModel styleModel = new StyleModel();
						styleModel.setTitle("全部");
						styleModel.setId("-1");
						styleModel.setLogo("");
						mStyleModels.add(0, styleModel);
						
						provinceModels = JSON.parseArray(jsonObject.getString("city"), ProvinceModel.class);
						if (mDialog == null)
							mDialog = new AreaDialog(context, mHandler);
						mDialog.setProvinceList(provinceModels);
					}
				}
			});
		} else {
			ToastUtils.showToast(context, "当前无网络链接");
		}
	}

	List<StyleModel> mStyleModels = null;
	List<ProvinceModel> provinceModels = null;
	AreaDialog mDialog = null;

	@OnClick({ R.id.iv_back_icon, R.id.tv_search, R.id.view_area_layout, R.id.view_style_layout, R.id.view_nearby_layout })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back_icon:
			finish();
			break;

		case R.id.tv_search:
			String search_content = search_content_edit.getText().toString().trim();
			if (StringUtils.isEmpty(search_content)) {
				ToastUtils.showToast(context, "搜索内容不能为空！");
				return;
			}
			searchShopList(search_content);
			break;
			
		case R.id.view_area_layout:
			if (mDialog == null){
				mDialog = new AreaDialog(context, mHandler);
				mDialog.setProvinceList(provinceModels);
			}
			mDialog.show();
			break;
			
		case R.id.view_nearby_layout:
			//附近餐厅
			searchNearby();
			break;

		case R.id.view_style_layout:
			DialogUtils.chooseStyleDialog(SearchActivity.this, mStyleModels, new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					try {
						if (mStyleModels != null) {
							StyleModel styleModel = mStyleModels.get(position);
							tv_style_title.setText(styleModel.getTitle());
							styleId = styleModel.getId();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					DialogUtils.dismiss();
					if(mStyleModels!=null&&mStyleModels.get(position).getId().equals("-1")){
						searchAll();
					}else{
						searchShopList("");
					}
				}
			});
			break;

		default:
			break;
		}
	}
	
	private void searchAll(){
		if (NetUtils.isOnline()) {
			DialogUtils.showProgressDialogWithMessage(context, "正在搜索中");
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("ukey", ukey);
			params.put("keyword", "");
			if(mDialog!=null){
				params.put("city", mDialog.getCityId());// 城市id
				params.put("area", mDialog.getAreaId());// 全市时传递0
				params.put("area1", mDialog.getArea1Id());// 镇id 全市、全区传递0
			}else{
				params.put("city", "");// 城市id
				params.put("area", "");// 全市时传递0
				params.put("area1", "");// 镇id 全市、全区传递0
			}
			params.put("style", "");// 菜系id
			if (null != mUserInfo) {
				params.put("lng", mUserInfo.getLng());// 经度
				params.put("lat", mUserInfo.getLat());// 纬度
			} else {
				params.put("lng", Preferences.getString("lng", "", context));// 经度
				params.put("lat", Preferences.getString("lat", "", context));// 纬度
			}
			FoodClientApi.post("Index/lists", params, new JsonResponseCallback<BizResult>() {
				
				@Override
				public void onSuccess(int statusCode, BizResult body) {
					Logger.e("TAG", "" + JSON.toJSONString(body));
					DialogUtils.dismiss();
					if (body != null && body.getCode() == 1) {
						List<DishModel> dishList = JSON.parseArray(body.getData(), DishModel.class);
						mAdapter = new DishAdapter(context, dishList);
						mListView.setAdapter(mAdapter);
					}else{
						List<DishModel> dishList = new ArrayList<DishModel>();
						mAdapter = new DishAdapter(context, dishList);
						mListView.setAdapter(mAdapter);
						ToastUtils.showToast(context, "未搜索到附近的餐厅");
					}
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					super.onFailure(arg0, arg1, arg2, arg3);
					DialogUtils.dismiss();
				}
			});
		} else {
			ToastUtils.showToast(context, "当前无网络连接");
		}
	}
	
	/**
	 * 搜索附近餐厅
	 */
	private void searchNearby() {
		if (NetUtils.isOnline()) {
			DialogUtils.showProgressDialogWithMessage(context, "正在搜索中");
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("ukey", ukey);
			if (null != mUserInfo) {
				params.put("lng", mUserInfo.getLng());// 经度
				params.put("lat", mUserInfo.getLat());// 纬度
			} else {
				params.put("lng", Preferences.getString("lng", "", context));// 经度
				params.put("lat", Preferences.getString("lat", "", context));// 纬度
			}
			Logger.e("TAG", "====="+JSON.toJSONString(params));
			FoodClientApi.post("Res/get_near", params, new JsonResponseCallback<BizResult>() {

				@Override
				public void onSuccess(int statusCode, BizResult body) {
					Logger.e("TAG", "" + JSON.toJSONString(body));

					if (body != null && body.getCode() == 1) {
						List<DishModel> dishList = JSON.parseArray(body.getData(), DishModel.class);
						mAdapter = new DishAdapter(context, dishList);
						mListView.setAdapter(mAdapter);
					}else{
						List<DishModel> dishList = new ArrayList<DishModel>();
						mAdapter = new DishAdapter(context, dishList);
						mListView.setAdapter(mAdapter);
						ToastUtils.showToast(context, "未搜索到附近的餐厅");
					}
					
					DialogUtils.dismiss();
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					super.onFailure(arg0, arg1, arg2, arg3);
					DialogUtils.dismiss();

				}
			});
		}else {
			ToastUtils.showToast(context, "当前无网络连接");
		}
	}
	
	private String styleId = "";

	/**
	 * 搜索餐馆
	 * 
	 * @param areaInfo
	 *            区域信息
	 * @param search_content
	 *            搜索内容
	 * @param styleInfo
	 *            菜系
	 */
	private void searchShopList(String search_content) {
		if (NetUtils.isOnline()) {
			DialogUtils.showProgressDialogWithMessage(context, "正在搜索中");
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("ukey", ukey);
			params.put("keyword", search_content);
			if(mDialog!=null){
				params.put("city", mDialog.getCityId());// 城市id
				params.put("area", mDialog.getAreaId());// 全市时传递0
				params.put("area1", mDialog.getArea1Id());// 镇id 全市、全区传递0
			}else{
				params.put("city", "");// 城市id
				params.put("area", "");// 全市时传递0
				params.put("area1", "");// 镇id 全市、全区传递0
			}
			params.put("style", styleId);// 菜系id
			if (null != mUserInfo) {
				params.put("lng", mUserInfo.getLng());// 经度
				params.put("lat", mUserInfo.getLat());// 纬度
			} else {
				params.put("lng", Preferences.getString("lng", "", context));// 经度
				params.put("lat", Preferences.getString("lat", "", context));// 纬度
			}
			
			Logger.e("TAG", "====="+JSON.toJSONString(params));
			
			FoodClientApi.post("Index/lists", params, new JsonResponseCallback<BizResult>() {

				@Override
				public void onSuccess(int statusCode, BizResult body) {
					Logger.e("TAG", "" + JSON.toJSONString(body));

					if (body != null && body.getCode() == 1) {
						List<DishModel> dishList = JSON.parseArray(body.getData(), DishModel.class);
						mAdapter = new DishAdapter(context, dishList);
						mListView.setAdapter(mAdapter);
					}else{
						List<DishModel> dishList = new ArrayList<DishModel>();
						mAdapter = new DishAdapter(context, dishList);
						mListView.setAdapter(mAdapter);
						ToastUtils.showToast(context, "未搜索到你要找的餐厅");
					}
					
					DialogUtils.dismiss();
				}
				
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					super.onFailure(arg0, arg1, arg2, arg3);
					DialogUtils.dismiss();

				}
			});
		} else {
			ToastUtils.showToast(context, "当前无网络连接");
		}
	}

}
