package com.wbteam.onesearch.app.module.shop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.scrollablelayout.ScrollableHelper;
import com.wbteam.app.base.BaseFragmentV4;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.http.MyTextHttpResponseHandler;
import com.wbteam.onesearch.app.model.FoodModel;
import com.wbteam.onesearch.app.model.RespondBaseEntity;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.module.shop.adapter.FoodAdapter;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.ToastUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


/**
 * 餐牌
 *
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-22 下午3:25:44
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class ShopFragmentIndex04 extends BaseFragmentV4 implements ScrollableHelper.ScrollableContainer {

    private UserInfo mUserInfo = null;
    private String id = null;
    List<FoodModel> foodModels;
    private PullToRefreshGridView gridview;
    private FoodAdapter mAdapter;
    int page = 1;
    private String count;

    public ShopFragmentIndex04() {
        super();
    }

    @SuppressLint("ValidFragment")
    public ShopFragmentIndex04(String id) {
        super();
        this.id = id;
    }

    @Override
    public View getSlidableView() {
        return null;
    }

    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shop_fragment_index_03, container, false);
    }

    private void refresh1() {
        foodModels.clear();
        page=1;
        sendRequest(1);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initView(View currentView) {
//		id = getArguments().getString(ShopDetailActivity.BUNDLE_KEY_ID);
        mUserInfo = AppContext.getInstance().getUserInfo();
        foodModels = new ArrayList<FoodModel>();

        gridview = (PullToRefreshGridView) currentView.findViewById(R.id.gridview);
        gridview.setMode(PullToRefreshBase.Mode.BOTH);
        gridview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                // 获取当前时间并格式化
                String label = DateUtils.formatDateTime(
                        getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 设置刷新文本说明(刷新过程中)
                gridview.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
                gridview.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                gridview.getLoadingLayoutProxy().setReleaseLabel("松开开始刷新数据");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
                        "最后更新时间:" + label);
                refresh1();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                page++;
                sendRequest1(page);
                int totalPage = Integer.valueOf(count) % 10;
                if (totalPage == 0) {
                    totalPage = Integer.valueOf(count) / 10;
                } else {
                    totalPage = Integer.valueOf(count) / 10 + 1;
                }
                if (page <= totalPage) {// 上一次请求有数据
                    // 自定义上拉header内容
                    gridview.getLoadingLayoutProxy().setRefreshingLabel(
                            "正在加载更多数据");
                    gridview.getLoadingLayoutProxy().setPullLabel(
                            "上拉可以加载更多");
                    gridview.getLoadingLayoutProxy().setReleaseLabel(
                            "松开立即加载更多");

                } else {
                    // 上一次请求已经没有数据了
                    gridview.getLoadingLayoutProxy().setPullLabel(
                            "已经全部数据加载完毕...");
                    gridview.getLoadingLayoutProxy().setReleaseLabel(
                            "已经全部数据加载完毕...");
                }

            }
        });
    }

    private void sendRequest1(int page) {
        if (NetUtils.isOnline()) {
            TreeMap<String, String> params = new TreeMap<String, String>();
            if (mUserInfo != null) {
                params.put("ukey", mUserInfo.getUkey());
            } else {
                params.put("ukey", "");
            }
            params.put("rid", id);// 餐厅id
            params.put("page", String.valueOf(page));// 餐厅id
            FoodClientApi.post("Res/foods", params, mHandler);
        } else {
            ToastUtils.showToast(getActivity(), "当前无网络链接");
        }
    }

    @Override
    public void initListener() {
        gridview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (foodModels != null && foodModels.size() > 0) {
                    FoodDetailActivity.showImagePrivew(getActivity(), position, foodModels);
                }
            }
        });
    }

    @Override
    public void initData() {
        sendRequest(1);
    }


    private void sendRequest(int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        if (NetUtils.isOnline()) {
            TreeMap<String, String> params = new TreeMap<String, String>();
            if (mUserInfo != null) {
                params.put("ukey", mUserInfo.getUkey());
            } else {
                params.put("ukey", "");
            }
            params.put("rid", id);// 餐厅id
            params.put("page", String.valueOf(page));// 餐厅id
            FoodClientApi.post("Res/foods", params, mHandler);
        } else {
            ToastUtils.showToast(getActivity(), "当前无网络链接");
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
                Log.d("TAG","page-->"+page);
                JSONObject object = new JSONObject(arg2);
                String code = object.optString("code");
                String status = object.optString("status");
                Log.d("TAG", object.toString());
                if ("success".equals(status)) {
                    count = object.optString("count");
                    JSONArray array = object.optJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.optJSONObject(i);
                        FoodModel foodModel = new FoodModel();
                        foodModel.setId(obj.optString("id"));
                        foodModel.setTitle(obj.optString("title"));
                        foodModel.setLogo(obj.optString("logo"));
                        foodModel.setPrice(obj.optString("price"));
                        foodModel.setBig_pic(obj.optString("big_pic"));
                        foodModels.add(foodModel);
                    }
                    mAdapter = new FoodAdapter(getActivity(), foodModels);
                    gridview.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFinish() {
            super.onFinish();
            gridview.onRefreshComplete();
        }
    };


    @Override
    public View getScrollableView() {
        return null;
    }
}
