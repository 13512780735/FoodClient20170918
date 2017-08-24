package com.wbteam.onesearch.app.module.shop;

import java.util.List;
import java.util.TreeMap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.scrollablelayout.ScrollableHelper.ScrollableContainer;
import com.wbteam.app.base.BaseFragmentV4;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.http.JsonResponseCallback;
import com.wbteam.onesearch.app.model.BizResult;
import com.wbteam.onesearch.app.model.FoodModel;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.module.shop.adapter.FoodAdapter;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.ToastUtils;

/**
 * 餐牌
 *
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-22 下午3:25:44
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class ShopFragmentIndex04 extends BaseFragmentV4 implements ScrollableContainer {

    private UserInfo mUserInfo = null;
    private String id = null;

    private GridView gridview;
    private FoodAdapter mAdapter;

    public ShopFragmentIndex04() {
        super();
    }

    @SuppressLint("ValidFragment")
    public ShopFragmentIndex04(String id) {
        super();
        this.id = id;
    }

    @Override
    public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shop_fragment_index_03, container, false);
    }

    @Override
    public void initView(View currentView) {
//		id = getArguments().getString(ShopDetailActivity.BUNDLE_KEY_ID);
        mUserInfo = AppContext.getInstance().getUserInfo();
        gridview = (GridView) currentView.findViewById(R.id.gridview);
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
        sendRequest();
    }

    List<FoodModel> foodModels = null;

    private void sendRequest() {
        if (NetUtils.isOnline()) {
            TreeMap<String, String> params = new TreeMap<String, String>();
            if (mUserInfo != null) {
                params.put("ukey", mUserInfo.getUkey());
            } else {
                params.put("ukey", "");
            }
            params.put("rid", id);// 餐厅id
            params.put("page", "1");// 餐厅id
            FoodClientApi.post("Res/foods", params, new JsonResponseCallback<BizResult>() {

                @Override
                public void onSuccess(int statusCode, BizResult body) {
                    try {
                        Logger.e("TAG", "==餐牌==" + JSON.toJSONString(body));
                        foodModels = JSON.parseArray(body.getData(), FoodModel.class);
                        mAdapter = new FoodAdapter(getActivity(), foodModels);
                        gridview.setAdapter(mAdapter);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        } else {
            ToastUtils.showToast(getActivity(), "当前无网络链接");
        }
    }

    @Override
    public View getSlidableView() {
        return null;
    }

    @Override
    public View getScrollableView() {
        return gridview;
    }

}
