package com.wbteam.onesearch.app.ui;

import java.util.TreeMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.wbteam.app.base.BaseListFragment;
import com.wbteam.app.base.adapter.ListBaseAdapter;
import com.wbteam.app.base.interf.ListEntity;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.adapter.RecommendAdapter2;
import com.wbteam.onesearch.app.fragment.Index02Fragment;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.model.RecommendModel;
import com.wbteam.onesearch.app.model.RecommendModelList;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.utils.JsonParseUtils;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.Preferences;
import com.wbteam.onesearch.app.weight.LoadingLayout;

/**
 * @author 码农哥
 * @date 2016-9-14上午1:11:58
 * @email 441293364@qq.com
 * @TODO
 */
public class RecommendFragment extends BaseListFragment<RecommendModel>{
	
	private UserInfo mUserInfo = null;
	private int bundle_key_type = 0;
	
	@Override
	public View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_dish_recommend, container, false);
	}
	
	@Override
	public void initView(View currentView) {
		bundle_key_type = getArguments().getInt(Index02Fragment.BUNDLE_KEY_TYPE);
		mUserInfo = AppContext.getInstance().getUserInfo();
		super.initView(currentView);
	}

	@Override
	public void initData() {
		super.initData();
		sendRequestData();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mUserInfo = AppContext.getInstance().getUserInfo();
	}
	
	@Override
	protected void sendRequestData() {
		super.sendRequestData();
		if (NetUtils.isOnline()) {
			if (bundle_key_type == 0) {
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
				params.put("type", "");// 优惠：1 文章：2
				params.put("rid", "");// 餐厅id
				params.put("page", mCurrentPage+"");
				FoodClientApi.post("News/lists", params, mHandler);
			} else {
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
				params.put("sort", "2");// sort
				params.put("type", "2");// 优惠：1 文章：2
				params.put("rid", "");// 餐厅id
				params.put("page", mCurrentPage+"");
				FoodClientApi.post("News/lists", params, mHandler);
			}
		}else{
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

	@Override
	public View getSlidableView() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
//public class RecommendFragment extends BaseFragmentV4 implements SwipeRefreshLayout.OnRefreshListener {
//
//	protected SwipeRefreshLayout mSwipeRefreshLayout;
//	private ListView mListview;
//	private RecommendAdapter mAdapter = null;
//
//	private UserInfo mUserInfo = null;
//	private int bundle_key_type = 0;
//
//	@Override
//	public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.fragment_dish_recommend, container, false);
//	}
//
//	@Override
//	public void initView(View currentView) {
//		bundle_key_type = getArguments().getInt(Index02Fragment.BUNDLE_KEY_TYPE);
//		mUserInfo = AppContext.getInstance().getUserInfo();
//		mSwipeRefreshLayout = (SwipeRefreshLayout) currentView.findViewById(R.id.swiperefreshlayout);
//		mListview = (ListView) currentView.findViewById(R.id.listview);
//
//		mSwipeRefreshLayout.setOnRefreshListener(this);
//		mSwipeRefreshLayout.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);
//
//	}
//
//	@Override
//	public void initListener() {
//		mListview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Intent intent = new Intent(getActivity(), JumpWebView.class);
//				intent.putExtra("id", mAdapter.getItem(position).getId());
//				String url = AppConfig.Detail_Url + mAdapter.getItem(position).getId();
//				intent.putExtra("contact", url);
//				startActivity(intent);
//			}
//		});
//	}
//	
//	@Override
//	public void onResume() {
//		super.onResume();
//		mUserInfo = AppContext.getInstance().getUserInfo();
//	}
//
//	@Override
//	public void initData() {
//		sendData();
//	}
//	
//	/**
//	 * 获取列表数据
//	 */
//	private void sendData() {
//		
//
//			setSwipeRefreshLoadedState();
//		} else {
//			setSwipeRefreshLoadedState();
//			ToastUtils.showToast(getActivity(), "当前无网络连接！");
//		}
//	}
//
//	@Override
//	public void onRefresh() {
//		// 设置顶部正在刷新
//		mListview.setSelection(0);
//		setSwipeRefreshLoadingState();
//		initData();
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
