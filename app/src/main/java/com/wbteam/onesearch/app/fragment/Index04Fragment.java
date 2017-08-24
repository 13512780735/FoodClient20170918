package com.wbteam.onesearch.app.fragment;

import java.util.List;
import java.util.TreeMap;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.wbteam.app.base.BaseFragmentV4;
import com.wbteam.app.base.BaseListFragment;
import com.wbteam.app.base.adapter.ListBaseAdapter;
import com.wbteam.app.base.interf.ListEntity;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.adapter.FavorableAdapter;
import com.wbteam.onesearch.app.adapter.RecommendAdapter;
import com.wbteam.onesearch.app.adapter.RecommendAdapter2;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.http.JsonResponseCallback;
import com.wbteam.onesearch.app.model.BizResult;
import com.wbteam.onesearch.app.model.RecommendModel;
import com.wbteam.onesearch.app.model.RecommendModelList;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.model.banner.Advertisements;
import com.wbteam.onesearch.app.model.banner.Banner;
import com.wbteam.onesearch.app.model.banner.BizResultOfBanner;
import com.wbteam.onesearch.app.ui.JumpWebView;
import com.wbteam.onesearch.app.ui.SearchActivity;
import com.wbteam.onesearch.app.utils.JsonParseUtils;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.Preferences;
import com.wbteam.onesearch.app.utils.ToastUtils;
import com.wbteam.onesearch.app.utils.ViewUtils;
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
public class Index04Fragment extends BaseListFragment<RecommendModel> implements OnClickListener{
	
	private LinearLayout view_search_layout;
//	
	private RelativeLayout bottom_adt_view;
	private LinearLayout ll_bottom_adt;
	private ImageView iv_close_adt;
	
	private UserInfo mUserInfo = null;
	
	@Override
	public View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_index_04, container, false);
	}
	
	@Override
	public void initView(View currentView) {
		super.initView(currentView);
		mUserInfo = AppContext.getInstance().getUserInfo();
		
		view_search_layout = (LinearLayout) currentView.findViewById(R.id.view_search_layout);
		
		bottom_adt_view = (RelativeLayout) currentView.findViewById(R.id.bottom_adt_view);
		ll_bottom_adt = (LinearLayout) currentView.findViewById(R.id.ll_bottom_adt);
		iv_close_adt = (ImageView) currentView.findViewById(R.id.iv_close_adt);
		if (Preferences.getBoolean("index_04", false, getActivity())) {
			ViewUtils.setGone(bottom_adt_view);
		} else {
			InitBottomAdt();
		}
	}
	
	@Override
	public void initListener() {
		super.initListener();
		
		iv_close_adt.setOnClickListener(this);
		view_search_layout.setOnClickListener(this);
	}

	@Override
	public void initData() {
		super.initData();
	}

	@Override
	public void onResume() {
		super.onResume();
		mUserInfo = AppContext.getInstance().getUserInfo();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_close_adt:
			//Preferences.saveBoolean("index_04", true, getActivity());
			ViewUtils.setGone(bottom_adt_view);
			break;

		case R.id.view_search_layout:
			startActivity(new Intent(getActivity(), SearchActivity.class));
			break;

		default:
			break;
		}
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
			params.put("sort", "create_time");// sort
			params.put("type", "1");// 优惠：1 文章：2
			params.put("rid", "");// 餐厅id
			params.put("page", mCurrentPage+"");
			FoodClientApi.post("News/lists", params, mHandler);
		} else {
			executeOnLoadFinish();
			mErrorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
		}
	}
	
	@Override
	protected ListBaseAdapter<RecommendModel> getListAdapter() {
		return new RecommendAdapter2(getActivity());
	}
	
	@Override
	protected ListEntity<RecommendModel> parseList(String message) throws Exception {
		RecommendModelList entityList = JsonParseUtils.fromJson(message, RecommendModelList.class);
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
			Intent intent = new Intent(getActivity(), JumpWebView.class);
			intent.putExtra("id", mAdapter.getItem(position).getId());
			String url = AppConfig.Detail_Url + mAdapter.getItem(position).getId();
			intent.putExtra("contact", url);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 底部广告
	 */
	private void InitBottomAdt() {
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


//public class Index04Fragment extends BaseFragmentV4 implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
//
//	private LinearLayout view_search_layout;
//	
//	private RelativeLayout bottom_adt_view;
//	private LinearLayout ll_bottom_adt;
//	private ImageView iv_close_adt;
//
//	protected SwipeRefreshLayout mSwipeRefreshLayout;
//	private ListView mListView;
////	private FavorableAdapter mAdapter;
//	private RecommendAdapter mAdapter = null;
//	
//	private UserInfo mUserInfo = null;
//
//	@Override
//	public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.fragment_index_04, container, false);
//	}
//
//	@Override
//	public void initView(View currentView) {
//		mUserInfo = AppContext.getInstance().getUserInfo();
//		
//		view_search_layout = (LinearLayout) currentView.findViewById(R.id.view_search_layout);
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
//		if (Preferences.getBoolean("index_04", false, getActivity())) {
//			ViewUtils.setGone(bottom_adt_view);
//		} else {
//			InitBottomAdt();
//		}
//	}
//
//	@Override
//	public void initListener() {
//		mListView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				try {
//					Intent intent = new Intent(getActivity(), JumpWebView.class);
//					intent.putExtra("id", mAdapter.getItem(position).getId());
//					String url = AppConfig.Detail_Url + mAdapter.getItem(position).getId();
//					intent.putExtra("contact", url);
//					startActivity(intent);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//		});
//
//		iv_close_adt.setOnClickListener(this);
//		view_search_layout.setOnClickListener(this);
//	}
//	
//	@Override
//	public void onResume() {
//		super.onResume();
//		mUserInfo = AppContext.getInstance().getUserInfo();
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.iv_close_adt:
//			Preferences.saveBoolean("index_04", true, getActivity());
//			ViewUtils.setGone(bottom_adt_view);
//			break;
//			
//		case R.id.view_search_layout:
//			startActivity(new Intent(getActivity(), SearchActivity.class));
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public void initData() {
//		sendRequest();
//	}
//
//	private void sendRequest() {
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
//			params.put("sort", "create_time");// sort
//			params.put("type", "1");// 优惠：1 文章：2
//			params.put("rid", "");// 餐厅id
//			FoodClientApi.post("News/lists", params, new JsonResponseCallback<BizResult>() {
//
//				@Override
//				public void onSuccess(int statusCode, BizResult body) {
//					Logger.e("Bin", "==优惠列表数据==" + JSON.toJSONString(body));
//					List<RecommendModel> recommendModels = JSON.parseArray(body.getData(), RecommendModel.class);
//					mAdapter = new RecommendAdapter(getActivity(), recommendModels);
//					mListView.setAdapter(mAdapter);
//				}
//			});
//			setSwipeRefreshLoadedState();
//		} else {
//			setSwipeRefreshLoadedState();
//			ToastUtils.showToast(getActivity(), "当前无网络链接");
//		}
//	}
//
//	/**
//	 * 底部广告
//	 */
//	private void InitBottomAdt() {
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
//
//		ViewUtils.setVisible(iv_close_adt);
//	}
//
//	@Override
//	public void onRefresh() {
//		// 设置顶部正在刷新
//		mListView.setSelection(0);
//		setSwipeRefreshLoadingState();
//
//		if (Preferences.getBoolean("index_04", false, getActivity())) {
//			ViewUtils.setGone(bottom_adt_view);
//		} else {
//			InitBottomAdt();
//		}
//
//		sendRequest();
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
