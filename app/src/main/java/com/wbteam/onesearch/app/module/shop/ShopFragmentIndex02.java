//package com.wbteam.onesearch.app.module.shop;
//
//import java.util.TreeMap;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//
//import com.scrollablelayout.ScrollableHelper.ScrollableContainer;
//import com.wbteam.app.base.BaseListFragment;
//import com.wbteam.app.base.adapter.ListBaseAdapter;
//import com.wbteam.app.base.interf.ListEntity;
//import com.wbteam.onesearch.R;
//import com.wbteam.onesearch.app.AppContext;
//import com.wbteam.onesearch.app.http.FoodClientApi;
//import com.wbteam.onesearch.app.model.RecommendModel;
//import com.wbteam.onesearch.app.model.RecommendModelList;
//import com.wbteam.onesearch.app.model.UserInfo;
//import com.wbteam.onesearch.app.module.shop.adapter.ShopIndex02Adapter;
//import com.wbteam.onesearch.app.utils.JsonParseUtils;
//import com.wbteam.onesearch.app.utils.NetUtils;
//import com.wbteam.onesearch.app.utils.Preferences;
//import com.wbteam.onesearch.app.weight.LoadingLayout;
//
///**
// *  餐厅推荐
// * 
// * @autor:码农哥
// * @version:1.0
// * @created:2016-9-22  下午3:25:44
// * @contact:QQ-441293364 TEL-15105695563
// **/
//public class ShopFragmentIndex02 extends BaseListFragment<RecommendModel> implements ScrollableContainer {//SwipeRefreshLayout.OnRefreshListener,
//	
//	private UserInfo mUserInfo = null;
//	private String id = null;
//	
//	private ListView mListView;
//	
//	public ShopFragmentIndex02(String id) {
//		this.id = id;
//	}
//	
//	@Override
//	public View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.shop_fragment_index_2, container, false);
//	}
//
//	@Override
//	public void initView(View currentView) {
//		mUserInfo = AppContext.getInstance().getUserInfo();
//		super.initView(currentView);
//	}
//	
//	@Override
//	public void initListener() {
//		
//	}
//
//	@Override
//	public void initData() {
//		sendRequestData();
//	}
//	
//	@Override
//	protected void sendRequestData() {
//		super.sendRequestData();
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
//			params.put("sort", "sort");// sort
//			params.put("type", "2");
//			params.put("rid", id);// 餐厅id
//			params.put("page", mCurrentPage+"");
//			FoodClientApi.post("News/lists", params, mHandler);
//		} else {
//			executeOnLoadFinish();
//			mErrorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
//		}
//	}
//
//	@Override
//	public View getSlidableView() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public View getScrollableView() {
//		return mListView;
//	}
//
//	@Override
//	protected ListBaseAdapter<RecommendModel> getListAdapter() {
//		// TODO Auto-generated method stub
//		return new ShopIndex02Adapter(getActivity());
//	}
//	
//	@Override
//	protected ListEntity<RecommendModel> parseList(String message) throws Exception {
//		RecommendModelList entityList = JsonParseUtils.fromJson(message, RecommendModelList.class);
//		if (null != entityList) {
//			return entityList;
//		} else {
//			return null;
//		}
//	}
//}


















package com.wbteam.onesearch.app.module.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.scrollablelayout.ScrollableHelper.ScrollableContainer;
import com.wbteam.app.base.BaseFragmentV4;
import com.wbteam.app.base.adapter.ListBaseAdapter;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.http.MyTextHttpResponseHandler;
import com.wbteam.onesearch.app.model.RecommendModel;
import com.wbteam.onesearch.app.model.RecommendModelList;
import com.wbteam.onesearch.app.model.RespondBaseEntity;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.module.shop.adapter.ShopIndex02Adapter;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.Preferences;
import com.wbteam.onesearch.app.utils.ToastUtils;
import com.wbteam.onesearch.app.weight.LoadingLayout;

/**
 *  餐厅推荐
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-22  下午3:25:44
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class ShopFragmentIndex02 extends BaseFragmentV4 implements ScrollableContainer,OnScrollListener {//SwipeRefreshLayout.OnRefreshListener,
	
	private UserInfo mUserInfo = null;
	private String id = null;
	
//	protected SwipeRefreshLayout mSwipeRefreshLayout;
	private ListView mListView;
//	private RestaurantItemAdapter mAdapter;
	private ShopIndex02Adapter mAdapter;

	public ShopFragmentIndex02() {
		super();
	}

	@SuppressLint("ValidFragment")
	public ShopFragmentIndex02(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.shop_fragment_index_2, container, false);
	}

	@Override
	public void initView(View currentView) {
		mUserInfo = AppContext.getInstance().getUserInfo();
		mListView = (ListView) currentView.findViewById(R.id.listview);
		mErrorLayout = (LoadingLayout) currentView.findViewById(R.id.loading_layout);
		mListView.setOnScrollListener(this);
		
		mAdapter = new ShopIndex02Adapter(getActivity());
		mListView.setAdapter(mAdapter);
	}
	
	@Override
	public void initListener() {
		
	}

	@Override
	public void initData() {
		sendRequest();
	}
	
	private void sendRequest() {
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
			params.put("sort", "sort");// sort
			params.put("type", "2");
			params.put("rid", id);// 餐厅id
			params.put("page", mCurrentPage+"");
			FoodClientApi.post("News/lists", params, mHandler);
			
//			new JsonResponseCallback<BizResult>() {
//
//				@Override
//				public void onSuccess(int statusCode, BizResult body) {
//					Logger.e("TAG", "==餐厅推荐==" + JSON.toJSONString(body));
//					List<RecommendModel> recommendModels = JSON.parseArray(body.getData(), RecommendModel.class);
//					mAdapter = new RestaurantItemAdapter(getActivity(), recommendModels);
//					mListView.setAdapter(mAdapter);
//				}
//			}
			
		} else {
//			ToastUtils.showToast(getActivity(), "当前无网络链接");
			executeOnLoadFinish();
			mErrorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
		}
	}
	
	protected MyTextHttpResponseHandler mHandler = new MyTextHttpResponseHandler() {

		@Override
		public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
			super.onFailure(arg0, arg1, arg2, arg3);
			Log.d("TAG", "请求失败：" + arg2);
		}

		@Override
		public void onSuccess(int arg0, Header[] arg1, String arg2) {
			super.onSuccess(arg0, arg1, arg2);
			Log.d("TAG", "请求成功：" + arg2);
			RespondBaseEntity entity = JSON.parseObject(arg2, RespondBaseEntity.class);
			try {
				if (!entity.getData().equalsIgnoreCase("false")) {
					RecommendModelList data = JSON.parseObject(arg2, RecommendModelList.class);
					executeOnLoadFinish();
					executeOnLoadDataSuccess(data.getList());
				} else {
					if (mAdapter.getData().size() == 0 && mCurrentPage == 1) {
						mErrorLayout.setErrorType(LoadingLayout.NODATA);
						Logger.e("TAG", "暂无数据");
					} else {
						Logger.e("TAG", "没有更多");
						mAdapter.setState(ListBaseAdapter.STATE_NO_MORE);
						mAdapter.notifyDataSetChanged();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				Logger.e("TAG", "暂无数据");
				executeOnLoadFinish();
				mErrorLayout.setErrorType(LoadingLayout.NODATA);

			}
		}
	};

	private int mCurrentPage = 1;
	protected LoadingLayout mErrorLayout;
	public static final int STATE_NONE = 0;
	public static final int STATE_REFRESH = 1;
	public static final int STATE_LOADMORE = 2;
	public static final int STATE_NOMORE = 3;
	public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
	public static int mState = STATE_NONE;
	
	protected void executeOnLoadDataSuccess(List<RecommendModel> data) {
		if (data == null) {
			data = new ArrayList<RecommendModel>();
		}

		mErrorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);
		if (mCurrentPage == 1) {
			mAdapter.clear();
		}

		int adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;
		if (mCurrentPage == 1 && data.size() == 0) {
			adapterState = ListBaseAdapter.STATE_EMPTY_ITEM;
		} else if (data.size() == 0 || (data.size() < getPageSize())) {
			adapterState = ListBaseAdapter.STATE_NO_MORE;
			mAdapter.notifyDataSetChanged();
		} else {
			adapterState = ListBaseAdapter.STATE_LOAD_MORE;
		}
		mAdapter.setState(adapterState);
		mAdapter.addData(data);
		// 判断等于是因为最后有一项是listview的状态
		if (mAdapter.getCount() == 1) {
			if (needShowEmptyNoData()) {
				mErrorLayout.setErrorType(LoadingLayout.NODATA);
			} else {
				mAdapter.setState(ListBaseAdapter.STATE_EMPTY_ITEM);
				mAdapter.notifyDataSetChanged();
			}
		}
	}
	
	protected boolean needShowEmptyNoData() {
		return true;
	}

	protected int getPageSize() {
		return 10;
	}
	
	// 完成刷新
	protected void executeOnLoadFinish() {
		mState = STATE_NONE;
		mErrorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);

	}
	
	@Override
	public View getSlidableView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getScrollableView() {

		return mListView;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mAdapter == null || mAdapter.getCount() == 0) {
			return;
		}
		// 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
		if (mState == STATE_LOADMORE || mState == STATE_REFRESH) {
			return;
		}
		// 判断是否滚动到底部
		boolean scrollEnd = false;
		try {
			if (view.getPositionForView(mAdapter.getFooterView()) == view.getLastVisiblePosition())
				scrollEnd = true;
		} catch (Exception e) {
			scrollEnd = false;
		}

		if (mState == STATE_NONE && scrollEnd) {
			if (mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE || mAdapter.getState() == ListBaseAdapter.STATE_NETWORK_ERROR) {
				mState = STATE_LOADMORE;
				mCurrentPage++;
				sendRequest();
				mAdapter.setFooterViewLoading();
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		
	}
}
