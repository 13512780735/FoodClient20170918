package com.wbteam.onesearch.app.fragment;

import java.util.List;
import java.util.TreeMap;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wbteam.app.base.BaseListFragment;
import com.wbteam.app.base.adapter.ListBaseAdapter;
import com.wbteam.app.base.interf.ListEntity;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.adapter.DishAdapter2;
import com.wbteam.onesearch.app.file.cache.Imageloader;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.http.JsonResponseCallback;
import com.wbteam.onesearch.app.model.BizResult;
import com.wbteam.onesearch.app.model.DishModel;
import com.wbteam.onesearch.app.model.DishModelList;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.model.banner.Advertisements;
import com.wbteam.onesearch.app.model.banner.Banner;
import com.wbteam.onesearch.app.model.banner.BizResultOfBanner;
import com.wbteam.onesearch.app.module.shop.ShopDetailActivity;
import com.wbteam.onesearch.app.module.webview.BannerWebView;
import com.wbteam.onesearch.app.ui.AddShopActivity;
import com.wbteam.onesearch.app.ui.LoginActivity;
import com.wbteam.onesearch.app.ui.SuggestActivity;
import com.wbteam.onesearch.app.utils.JsonParseUtils;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.Preferences;
import com.wbteam.onesearch.app.utils.ViewUtils;
import com.wbteam.onesearch.app.weight.CircleImageView;
import com.wbteam.onesearch.app.weight.LoadingLayout;

/**
 *  @author 码农哥
 *	@date 2016-3-28下午3:43:33
 *	@email 441293364@qq.com
 *	@TODO 主界面(我的)
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
public class Index05Fragment extends BaseListFragment<DishModel> implements OnClickListener{

	private CircleImageView iv_user_avatar;
	private TextView tv_user_name;
	
	private LinearLayout view_user_login;

	private RelativeLayout bottom_adt_view;
	private LinearLayout ll_bottom_adt;
	private ImageView iv_close_adt;
	
	private Imageloader mImageloader;
	private UserInfo mUserInfo = null;
	
	private TextView tv_user_tucao;
	private TextView tv_app_about;
	private TextView tv_user_jinzhu;
	
	@Override
	public View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_index_05, container, false);
	}
	
	@Override
	public void initView(View currentView) {
		super.initView(currentView);
		mUserInfo = AppContext.getInstance().getUserInfo();
			
		view_user_login = (LinearLayout) currentView.findViewById(R.id.view_user_login);
		mImageloader = Imageloader.getInstance(getActivity());
		iv_user_avatar = (CircleImageView) currentView.findViewById(R.id.iv_user_avatar);
		tv_user_name = (TextView) currentView.findViewById(R.id.tv_user_name);
		
		bottom_adt_view = (RelativeLayout) currentView.findViewById(R.id.bottom_adt_view);
		ll_bottom_adt = (LinearLayout) currentView.findViewById(R.id.ll_bottom_adt);
		iv_close_adt = (ImageView) currentView.findViewById(R.id.iv_close_adt);

		tv_user_tucao = (TextView) currentView.findViewById(R.id.tv_user_tucao);
		tv_app_about = (TextView) currentView.findViewById(R.id.tv_about);
		tv_user_jinzhu = (TextView) currentView.findViewById(R.id.tv_user_jinzhu);
		InitBottomAdt();
	}

	@Override
	public void initListener() {
		super.initListener();
		view_user_login.setOnClickListener(this);
		iv_close_adt.setOnClickListener(this);
		tv_user_tucao.setOnClickListener(this);
		tv_app_about.setOnClickListener(this);
		tv_user_jinzhu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.view_user_login:
			if (mUserInfo == null)
				startActivity(new Intent(getActivity(), LoginActivity.class));
			break;
			
		case R.id.iv_close_adt:
			//Preferences.saveBoolean("index_05", true, getActivity());
			bottom_adt_view.setVisibility(View.GONE);
			break;
			
		case R.id.tv_user_tucao:
			getActivity().startActivity(new Intent(getActivity(), SuggestActivity.class));
			break;
			
		case R.id.tv_about:
			Intent intent = new Intent(getActivity(), BannerWebView.class);
			intent.putExtra("bannerUrl", AppConfig.APP_ABOUT_URL);
			getActivity().startActivity(intent);
			break;
			
		case R.id.tv_user_jinzhu://我要进驻
			getActivity().startActivity(new Intent(getActivity(), AddShopActivity.class));
			break;
			
		default:
			break;
		}
	}

	@Override
	public void initData() {
		super.initData();
		mUserInfo = AppContext.getInstance().getUserInfo();
		if (null != mUserInfo) {
			mImageloader.DisplayImage(mUserInfo.getAvatar(), iv_user_avatar);
			tv_user_name.setText(mUserInfo.getNickname());
			sendRequestData();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
//		mUserInfo = AppContext.getInstance().getUserInfo();
		mCurrentPage = 1;
		initData();
//		sendReuqest();
	}

	@Override
	protected void sendRequestData() {
		super.sendRequestData();
		if (NetUtils.isOnline()) {
			TreeMap<String, String> params = new TreeMap<String, String>();
			if (null != mUserInfo) {
				params.put("ukey", mUserInfo.getUkey());
				params.put("lng", mUserInfo.getLng());// 经度
				params.put("lat", mUserInfo.getLat());// 纬度
			} else {
				params.put("ukey", "");
				params.put("lng", Preferences.getString("lng", "", getActivity()));// 经度
				params.put("lat", Preferences.getString("lat", "", getActivity()));// 纬度
			}
			params.put("page", mCurrentPage+"");
			FoodClientApi.post("User/user_collect", params, mHandler);
		} else {
			executeOnLoadFinish();
			mErrorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
		}
	}
	
	@Override
	protected ListBaseAdapter<DishModel> getListAdapter() {
		return new DishAdapter2(getActivity());
	}
	
	
	@Override
	protected ListEntity<DishModel> parseList(String message) throws Exception {
		DishModelList entityList = JsonParseUtils.fromJson(message, DishModelList.class);
		if (null != entityList) {
			return entityList;
		} else {
			return null;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		super.onItemClick(parent, view, position, id);
		try {
			DishModel dishModel = mAdapter.getItem(position);
			if (dishModel != null) {
				Intent mIntent = new Intent(getActivity(), ShopDetailActivity.class);
				mIntent.putExtra("shop_id", dishModel.getRid());
				startActivity(mIntent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化底部广告
	 */
	private void InitBottomAdt() {
		if(Preferences.getBoolean("index_05", false, getActivity())){
			ViewUtils.setGone(bottom_adt_view);
			return;
		}
		
		if (NetUtils.isOnline()) {
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("cat_id", "4");
			params.put("create_time", System.currentTimeMillis() / 1000 + "");
			FoodClientApi.post("Ad/adList", params, new JsonResponseCallback<BizResult>() {

				@Override
				public void onSuccess(int statusCode, BizResult body) {
					Logger.e("TAG", "广告信息：" + JSON.toJSONString(body));
					if (body.getCode() == 1) {
						List<Banner> bannerList = JSON.parseArray(body.getData(), Banner.class);
						if (bannerList != null) {
							BizResultOfBanner banner = new BizResultOfBanner();
							banner.setBannerList(bannerList);
							ll_bottom_adt.addView(new Advertisements(fatherActivity, fatherActivity.getLayoutInflater(), 3000).initView(banner));
						} else {
							View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
							ll_bottom_adt.addView(view);
						}
					} else {
						View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
						ll_bottom_adt.addView(view);
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					super.onFailure(statusCode, headers, responseBody, error);
					View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
					ll_bottom_adt.addView(view);
				}
			});
		} else {
			View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
			ll_bottom_adt.addView(view);
		}
		ViewUtils.setVisible(iv_close_adt);
	}

	@Override
	public View getSlidableView() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
//public class Index05Fragment extends BaseFragmentV4 implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
//	
//	private CircleImageView iv_user_avatar;
//	private TextView tv_user_name;
//	
//	private LinearLayout view_user_login;
//
//	private RelativeLayout bottom_adt_view;
//	private LinearLayout ll_bottom_adt;
//	private ImageView iv_close_adt;
//	
//	protected SwipeRefreshLayout mSwipeRefreshLayout;
//	private ListView mListView;
//	private DishAdapter mAdapter = null;
//	
//	private Imageloader mImageloader;
//	private UserInfo mUserInfo = null;
//	
//	private TextView tv_user_tucao;
//	
//	@Override
//	public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.fragment_index_05, container, false);
//	}
//
//	@Override
//	public void initView(View currentView) {
//		mUserInfo = AppContext.getInstance().getUserInfo();
//			
//		view_user_login = (LinearLayout) currentView.findViewById(R.id.view_user_login);
//		mImageloader = Imageloader.getInstance(getActivity());
//		iv_user_avatar = (CircleImageView) currentView.findViewById(R.id.iv_user_avatar);
//		tv_user_name = (TextView) currentView.findViewById(R.id.tv_user_name);
//		
//		mSwipeRefreshLayout = (SwipeRefreshLayout) currentView.findViewById(R.id.swiperefreshlayout);
//		mListView = (ListView) currentView.findViewById(R.id.listview);
//		
//		mSwipeRefreshLayout.setOnRefreshListener(this);
//		mSwipeRefreshLayout.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
//		
//		bottom_adt_view = (RelativeLayout) currentView.findViewById(R.id.bottom_adt_view);
//		ll_bottom_adt = (LinearLayout) currentView.findViewById(R.id.ll_bottom_adt);
//		iv_close_adt = (ImageView) currentView.findViewById(R.id.iv_close_adt);
//
//		tv_user_tucao = (TextView) currentView.findViewById(R.id.tv_user_tucao);
//		InitBottomAdt();
//		
//	}
//
//	@Override
//	public void initData() {
//		UserInfo userInfo = AppContext.getInstance().getUserInfo();
//		if (null != userInfo) {
//			mImageloader.DisplayImage(userInfo.getAvatar(), iv_user_avatar);
//			tv_user_name.setText(userInfo.getNickname());
//			sendReuqest();
//		}
//	}
//	
//	@Override
//	public void onResume() {
//		super.onResume();
////		mUserInfo = AppContext.getInstance().getUserInfo();
//		initData();
////		sendReuqest();
//	}
//
//	@Override
//	public void initListener() {
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				try {
//					DishModel dishModel = mAdapter.getItem(position);
//					if (dishModel != null) {
//						Intent mIntent = new Intent(getActivity(), ShopDetailActivity.class);
////						mIntent.putExtra("dish_model", dishModel);
//						mIntent.putExtra("shop_id", dishModel.getRid());
//						startActivity(mIntent);
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		view_user_login.setOnClickListener(this);
//		iv_close_adt.setOnClickListener(this);
//		tv_user_tucao.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.view_user_login:
//			if (mUserInfo == null)
//				startActivity(new Intent(getActivity(), LoginActivity.class));
//			break;
//			
//		case R.id.iv_close_adt:
//			Preferences.saveBoolean("index_05", true, getActivity());
//			bottom_adt_view.setVisibility(View.GONE);
//			break;
//			
//		case R.id.tv_user_tucao:
//			getActivity().startActivity(new Intent(getActivity(), SuggestActivity.class));
//			break;
//			
//		default:
//			break;
//		}
//	}
//	
//	/**
//	 * 获取收藏列表数据
//	 */
//	private void sendReuqest() {
//		if (NetUtils.isOnline()) {
//			TreeMap<String, String> params = new TreeMap<String, String>();
//			if (null != mUserInfo) {
//				params.put("ukey", mUserInfo.getUkey());
//				params.put("lng", mUserInfo.getLng());// 经度
//				params.put("lat", mUserInfo.getLat());// 纬度
//			} else {
//				params.put("ukey", "");
//				params.put("lng", Preferences.getString("lng", "", getActivity()));// 经度
//				params.put("lat", Preferences.getString("lat", "", getActivity()));// 纬度
//			}
//			FoodClientApi.post("User/user_collect", params, new JsonResponseCallback<BizResult>() {
//
//				@Override
//				public void onSuccess(int statusCode, BizResult body) {
//					// TODO Auto-generated method stub
//					Logger.e("TAG", "" + JSON.toJSONString(body));
//
//					if (body != null && body.getCode() == 1) {
//						List<DishModel> dishList = JSON.parseArray(body.getData(), DishModel.class);
//						mAdapter = new DishAdapter(getActivity(), dishList);
//						mListView.setAdapter(mAdapter);
//					}
//					
//					setSwipeRefreshLoadedState();
//				}
//
//				@Override
//				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//					super.onFailure(arg0, arg1, arg2, arg3);
//
//				}
//			});
//		} else {
//			ToastUtils.showToast(getActivity(), "当前无网络连接");
//		}
//	}
//	
//	/**
//	 * 初始化底部广告
//	 */
//	private void InitBottomAdt() {
//		if(Preferences.getBoolean("index_05", false, getActivity())){
//			ViewUtils.setGone(bottom_adt_view);
//			return;
//		}
//		
//		if (NetUtils.isOnline()) {
//			TreeMap<String, String> params = new TreeMap<String, String>();
//			params.put("cat_id", "4");
//			params.put("create_time", System.currentTimeMillis() / 1000 + "");
//			FoodClientApi.post("Ad/adList", params, new JsonResponseCallback<BizResult>() {
//
//				@Override
//				public void onSuccess(int statusCode, BizResult body) {
//					Logger.e("TAG", "广告信息：" + JSON.toJSONString(body));
//					if (body.getCode() == 1) {
//						List<Banner> bannerList = JSON.parseArray(body.getData(), Banner.class);
//						if (bannerList != null) {
//							BizResultOfBanner banner = new BizResultOfBanner();
//							banner.setBannerList(bannerList);
//							ll_bottom_adt.addView(new Advertisements(fatherActivity, fatherActivity.getLayoutInflater(), 3000).initView(banner));
//						} else {
//							View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//							ll_bottom_adt.addView(view);
//						}
//					} else {
//						View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//						ll_bottom_adt.addView(view);
//					}
//				}
//
//				@Override
//				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//					super.onFailure(statusCode, headers, responseBody, error);
//					View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//					ll_bottom_adt.addView(view);
//				}
//			});
//		} else {
//			View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//			ll_bottom_adt.addView(view);
//		}
//		ViewUtils.setVisible(iv_close_adt);
//	}
//	
//	@Override
//	public void onRefresh() {
//		// 设置顶部正在刷新
//		mListView.setSelection(0);
//		setSwipeRefreshLoadingState();
//		sendReuqest();
//	}
//	
//	/** 设置顶部正在加载的状态 */
//	private void setSwipeRefreshLoadingState() {
//		if (mSwipeRefreshLayout != null) {
//			mSwipeRefreshLayout.setRefreshing(true);
//			// 防止多次重复刷新
//			mSwipeRefreshLayout.setEnabled(false);
//		}
//	}
//
//	/** 设置顶部加载完毕的状态 */
//	private void setSwipeRefreshLoadedState() {
//		if (mSwipeRefreshLayout != null) {
//			mSwipeRefreshLayout.setRefreshing(false);
//			mSwipeRefreshLayout.setEnabled(true);
//		}
//	}
//
//}
