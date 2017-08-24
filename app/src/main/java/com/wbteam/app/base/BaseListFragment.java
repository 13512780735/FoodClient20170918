package com.wbteam.app.base;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wbteam.app.base.adapter.ListBaseAdapter;
import com.wbteam.app.base.interf.ListEntity;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.http.MyTextHttpResponseHandler;
import com.wbteam.onesearch.app.model.Entity;
import com.wbteam.onesearch.app.model.RespondBaseEntity;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.weight.LoadingLayout;

public abstract class BaseListFragment<T extends Entity> extends BaseFragmentV4 implements SwipeRefreshLayout.OnRefreshListener, OnScrollListener, OnItemClickListener {
	public static final int STATE_NONE = 0;
	public static final int STATE_REFRESH = 1;
	public static final int STATE_LOADMORE = 2;
	public static final int STATE_NOMORE = 3;
	public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
	public static int mState = STATE_NONE;

	protected SwipeRefreshLayout mSwipeRefreshLayout;

	protected ListView mListView;

	protected ListBaseAdapter<T> mAdapter;

	protected LoadingLayout mErrorLayout;

	protected int mStoreEmptyState = -1;

	protected int mCurrentPage = 1;

	protected int mCatalog = 1;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onRefresh() {
		if (mState == STATE_REFRESH) {
			return;
		}
		// 设置顶部正在刷新
		mListView.setSelection(0);
		setSwipeRefreshLoadingState();
		mCurrentPage = 1;
		mState = STATE_REFRESH;
		sendRequestData();
	}

	@Override
	public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return getContentView(inflater, container, savedInstanceState);
		// return inflater.inflate(R.layout.fragment_pullrefresh_swipe_listview,
		// container, false);
	}

	public abstract View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	@Override
	public void initView(View currentView) {
		mSwipeRefreshLayout = (SwipeRefreshLayout) currentView.findViewById(R.id.swiperefreshlayout);
		mListView = (ListView) currentView.findViewById(R.id.listview);
		mErrorLayout = (LoadingLayout) currentView.findViewById(R.id.loading_layout);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);

		mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
				mCurrentPage = 1;
				mState = STATE_REFRESH;
		mErrorLayout.setErrorType(LoadingLayout.NETWORK_LOADING);
		sendRequestData();
	}
});

		mListView.setOnItemClickListener(this);
		mListView.setOnScrollListener(this);
		
		if (mAdapter != null) {
			mListView.setAdapter(mAdapter);
			mErrorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);
		} else {
			mAdapter = getListAdapter();
			mListView.setAdapter(mAdapter);

			if (requestDataIfViewCreated()) {
				mErrorLayout.setErrorType(LoadingLayout.NETWORK_LOADING);
				mState = STATE_NONE;
				sendRequestData();
			} else {
				mErrorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);
			}

		}
		if (mStoreEmptyState != -1) {
			mErrorLayout.setErrorType(mStoreEmptyState);
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
//				if (!entity.getData().equalsIgnoreCase("false")) {
//					ListEntity<T> data = parseList(arg2);
//					if (data != null) {
//						List<T> list = data.getList();
//						if (mCurrentPage == 1 && list.size() == 0) {
//							mErrorLayout.setErrorType(LoadingLayout.NODATA);
//						} else {
//							executeOnLoadDataSuccess(list);
//							executeOnLoadFinish();
//							if (data.getList().size() < getPageSize()) {
//								int adapterState = ListBaseAdapter.STATE_NO_MORE;
//								mAdapter.setState(adapterState);
//								mAdapter.notifyDataSetChanged();
//							}
//						}
//					} else {
//						mAdapter.setState(ListBaseAdapter.STATE_EMPTY_ITEM);
//						executeOnLoadFinish();
//					}
//				} else {
//					mErrorLayout.setErrorType(LoadingLayout.NODATA);
//				}
				if (!entity.getData().equalsIgnoreCase("false")) {
					ListEntity<T> data = parseList(arg2);
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
//				mAdapter.setState(ListBaseAdapter.STATE_EMPTY_ITEM);
//				executeOnLoadFinish();
				Logger.e("TAG", "暂无数据");
				executeOnLoadFinish();
				mErrorLayout.setErrorType(LoadingLayout.NODATA);

			}
		}
	};

	@Override
	public void initData() {
		
	}

	protected void sendRequestData() {
	}

	protected abstract ListBaseAdapter<T> getListAdapter();

	protected boolean requestDataIfViewCreated() {
		return true;
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	protected ListEntity<T> parseList(String message) throws Exception {
		return null;
	}

	protected void executeOnLoadDataSuccess(List<T> data) {
		if (data == null) {
			data = new ArrayList<T>();
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
				onRefresh();
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
		setSwipeRefreshLoadedState();
		mState = STATE_NONE;
		mErrorLayout.setErrorType(LoadingLayout.HIDE_LAYOUT);

	}

	/** 设置顶部正在加载的状态 */
	private void setSwipeRefreshLoadingState() {
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setRefreshing(true);
			// 防止多次重复刷新
			mSwipeRefreshLayout.setEnabled(false);
		}
	}

	/** 设置顶部加载完毕的状态 */
	private void setSwipeRefreshLoadedState() {
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setRefreshing(false);
			mSwipeRefreshLayout.setEnabled(true);
		}
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
				sendRequestData();
				mAdapter.setFooterViewLoading();
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
//		 if (mState == STATE_NOMORE || mState == STATE_LOADMORE
//		 || mState == STATE_REFRESH) {
//		 return;
//		 }
//		 if (mAdapter != null
//		 && mAdapter.getDataSize() > 0
//		 && mListView.getLastVisiblePosition() == (mListView.getCount() - 1))
//		 {
//		 if (mState == STATE_NONE
//		 && mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE) {
//		 mState = STATE_LOADMORE;
//		 mCurrentPage++;
//		 requestData(true);
//		 }
//		 }
	}

	@Override
	public void onDestroyView() {
//		mStoreEmptyState = mErrorLayout.getErrorState();
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
