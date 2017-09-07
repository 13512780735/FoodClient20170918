package com.wbteam.onesearch.app.fragment;

import java.util.List;
import java.util.TreeMap;

import org.apache.http.Header;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.service.LocationService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wbteam.app.base.BaseFragmentV4;
import com.wbteam.app.base.BaseListFragment;
import com.wbteam.app.base.adapter.ListBaseAdapter;
import com.wbteam.app.base.interf.ListEntity;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.adapter.DishAdapter;
import com.wbteam.onesearch.app.adapter.DishAdapter2;
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
import com.wbteam.onesearch.app.ui.SearchActivity;
import com.wbteam.onesearch.app.utils.AppUtils;
import com.wbteam.onesearch.app.utils.DialogUtils;
import com.wbteam.onesearch.app.utils.JsonParseUtils;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.Preferences;
import com.wbteam.onesearch.app.utils.StringUtils;
import com.wbteam.onesearch.app.utils.ToastUtils;
import com.wbteam.onesearch.app.utils.UtilPreference;
import com.wbteam.onesearch.app.utils.ViewUtils;
import com.wbteam.onesearch.app.weight.LoadingLayout;

/**
 * @author 码农哥
 * @date 2016-3-28下午3:43:33
 * @email 441293364@qq.com
 * @TODO 主界面(任务)
 */
//public class Index01Fragment extends BaseListFragment<DishModel> implements OnClickListener {
//
//    private LocationService locationService;
//    private LinearLayout llscroll;
//
//    private RelativeLayout bottom_adt_view;
//    private LinearLayout ll_bottom_adt;
//    private ImageView iv_close_adt;
//
//    private LinearLayout view_search_layout;
//
//    private View mHeadView = null;
//    private String ukey = "";
//
//    private UserInfo mUserInfo = null;
//
//    @Override
//    public View getContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_index_01, container, false);
//    }
//
//    @Override
//    public void initView(View currentView) {
//        super.initView(currentView);
//        mUserInfo = AppContext.getInstance().getUserInfo();
//        if (mUserInfo != null) {
//            ukey = mUserInfo.getUkey();
//        }
//
//        view_search_layout = (LinearLayout) currentView.findViewById(R.id.view_search_layout);
//
//        llscroll = (LinearLayout) mHeadView.findViewById(R.id.llscroll);
//        llscroll.setLayoutParams(AppUtils.getParams(fatherActivity));
//
//        bottom_adt_view = (RelativeLayout) currentView.findViewById(R.id.bottom_adt_view);
//        iv_close_adt = (ImageView) currentView.findViewById(R.id.iv_close_adt);
//        ll_bottom_adt = (LinearLayout) currentView.findViewById(R.id.ll_bottom_adt);
//
//        InitViewPager();
//
//        if (Preferences.getBoolean("index_01", false, getActivity())) {
//            ViewUtils.setGone(bottom_adt_view);
//        } else {
//            InitBottomAdt();
//        }
//    }
//
//    @Override
//    public void initListener() {
//        super.initListener();
//        iv_close_adt.setOnClickListener(this);
//        view_search_layout.setOnClickListener(this);
//    }
//
//    @Override
//    public void initData() {
//        super.initData();
////		String lng = Preferences.getString("lng", "", getActivity());// 经度
////		String lat = Preferences.getString("lat", "", getActivity());// 纬度
////		if (mUserInfo != null) {
////			sendReuqest(mUserInfo.getLat(), mUserInfo.getLng());
////		} else if (StringUtils.notBlank(lat) && StringUtils.notBlank(lng)) {
////			sendReuqest(lat, lng);
////		} else {
//        locationCity();
////		}
//    }
//
//    @Override
//    protected void sendRequestData() {
//        if (NetUtils.isOnline()) {
//            //sendReuqest(lat, lng);
//            initData();
//        } else {
//            executeOnLoadFinish();
//            mErrorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (AppContext.getInstance().getUserInfo() != null) {
//            ukey = AppContext.getInstance().getUserInfo().getUkey();
//        }
//    }
//
//    private void locationCity() {
//        locationService = AppContext.getInstance().locationService;
//        // 获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
//        locationService.registerListener(mListener);
//        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//        locationService.start();
//    }
//
//    double lat = 0.0;
//    double lng = 0.0;
//
//    private BDLocationListener mListener = new BDLocationListener() {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
////				ToastUtils.showToast(getActivity(), "定位成功");
//                locationService.stop();
////				DialogUtils.dismiss();
//
//                String cityCode = location.getCityCode();
//                String city = location.getCity();
//                String district = location.getDistrict();
//                String street = location.getStreet();
//                lat = location.getLatitude();
//                lng = location.getLongitude();
//
//                Preferences.saveString("lat", lat + "", getActivity());
//                Preferences.saveString("lng", lng + "", getActivity());
//
//                UserInfo userInfo = AppContext.getInstance().getUserInfo();
//                if (userInfo != null) {
//                    userInfo.setLat(lat + "");
//                    userInfo.setLng(lng + "");
//                    AppContext.doLogin(userInfo);
//                }
//                Logger.e("TAG", "城市编码：" + cityCode);
//                Logger.e("TAG", "城市：" + city);
//                Logger.e("TAG", "区：" + district);//cityCode, city, district, street,
//                sendReuqest(lat + "", lng + "");
//            }
//        }
//
//    };
//
//    @Override
//    protected ListBaseAdapter<DishModel> getListAdapter() {
//        mHeadView = getActivity().getLayoutInflater().inflate(R.layout.public_header_index_content, null);
//        mListView.addHeaderView(mHeadView, null, false);
//        return new DishAdapter2(getActivity());
//    }
//
//
//    @Override
//    protected ListEntity<DishModel> parseList(String message) throws Exception {
//        DishModelList entityList = JsonParseUtils.fromJson(message, DishModelList.class);
//        if (null != entityList) {
//            return entityList;
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        super.onItemClick(parent, view, position, id);
//        try {
//            DishModel dishModel = mAdapter.getItem(position - 1);
//            if (dishModel != null) {
//                Intent mIntent = new Intent(getActivity(), ShopDetailActivity.class);
//                //mIntent.putExtra("dish_model", dishModel);
//                mIntent.putExtra("shop_id", dishModel.getId());
//                startActivity(mIntent);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_close_adt:
//                //Preferences.saveBoolean("index_01", true, getActivity());
//                //ViewUtils.setGone(bottom_adt_view);
//                bottom_adt_view.setVisibility(View.GONE);
//                break;
//
//            case R.id.view_search_layout:
//                startActivity(new Intent(getActivity(), SearchActivity.class));
//                break;
//
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 请求列表数据
//     *
//     * @param lat
//     * @param lng
//     */
//    private void sendReuqest(String lat, String lng) {
//        if (NetUtils.isOnline()) {
//            TreeMap<String, String> params = new TreeMap<String, String>();
//            params.put("ukey", ukey);
//            params.put("keyword", "");
//            params.put("city", "");// cityCode
//            params.put("area", "0");// 全市时传递0
//            params.put("area1", "0");// 镇id 全市、全区传递0
//            params.put("style", "");// 菜系id
//            params.put("lat", lat);// 纬度
//            params.put("lng", lng);// 经度
//            params.put("page", mCurrentPage + "");
//            FoodClientApi.post("Index/lists", params, mHandler);
//        } else {
//            executeOnLoadFinish();
//            mErrorLayout.setErrorType(LoadingLayout.NETWORK_ERROR);
//        }
//    }
//
//    /**
//     * 广告
//     */
//    private void InitViewPager() {
//        if (NetUtils.isOnline()) {
//            TreeMap<String, String> params = new TreeMap<String, String>();
//            params.put("cat_id", "2");
//            params.put("create_time", System.currentTimeMillis() / 1000 + "");
//            FoodClientApi.post("Ad/adList", params, new JsonResponseCallback<BizResult>() {
//
//                @Override
//                public void onSuccess(int statusCode, BizResult body) {
////					Logger.e("TAG", "广告信息：" + JSON.toJSONString(body));
//                    if (body.getCode() == 1) {
//                        List<Banner> bannerList = JSON.parseArray(body.getData(), Banner.class);
//                        if (bannerList != null) {
//                            BizResultOfBanner banner = new BizResultOfBanner();
//                            banner.setBannerList(bannerList);
//                            llscroll.addView(new Advertisements(fatherActivity, fatherActivity.getLayoutInflater(), 3000).initView(banner));
//                        } else {
//                            View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//                            llscroll.addView(view);
//                        }
//                    } else {
//                        View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//                        llscroll.addView(view);
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    super.onFailure(statusCode, headers, responseBody, error);
//                    View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//                    llscroll.addView(view);
//                }
//            });
//        } else {
//            View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//            llscroll.addView(view);
//        }
//    }
//
//    /**
//     * 底部广告
//     */
//    private void InitBottomAdt() {
//        if (NetUtils.isOnline()) {
//            TreeMap<String, String> params = new TreeMap<String, String>();
//            params.put("cat_id", "4");
//            params.put("create_time", System.currentTimeMillis() / 1000 + "");
//            FoodClientApi.post("Ad/adList", params, new JsonResponseCallback<BizResult>() {
//
//                @Override
//                public void onSuccess(int statusCode, BizResult body) {
//                    if (body.getCode() == 1) {
//                        List<Banner> bannerList = JSON.parseArray(body.getData(), Banner.class);
//                        if (bannerList != null) {
//                            BizResultOfBanner banner = new BizResultOfBanner();
//                            banner.setBannerList(bannerList);
//                            ll_bottom_adt.addView(new Advertisements(fatherActivity, fatherActivity.getLayoutInflater(), 3000).initView(banner));
//                        } else {
//                            View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//                            ll_bottom_adt.addView(view);
//                        }
//                    } else {
//                        View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//                        ll_bottom_adt.addView(view);
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    super.onFailure(statusCode, headers, responseBody, error);
//                    View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//                    ll_bottom_adt.addView(view);
//                }
//            });
//        } else {
//            View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
//            ll_bottom_adt.addView(view);
//        }
//
//
//        ViewUtils.setVisible(iv_close_adt);
//    }
//
//    @Override
//    public View getSlidableView() {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//}

public class Index01Fragment extends BaseFragmentV4 implements OnClickListener{

	private LocationService locationService;
	private LinearLayout llscroll;

	private RelativeLayout bottom_adt_view;
	private LinearLayout ll_bottom_adt;
	private ImageView iv_close_adt;

	private LinearLayout view_search_layout;

	private PullToRefreshListView mPullToRefreshListView;
	private DishAdapter mAdapter;

	private View mHeadView = null;
	private String ukey = "";
    private ListView mListView;

	@Override
    public View getSlidableView() {
        return null;
    }

    @Override
	public View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_index_01, container, false);
	}

	@Override
	public void initView(View currentView) {
		if(AppContext.getInstance().getUserInfo()!=null){
			ukey = AppContext.getInstance().getUserInfo().getUkey();
		}

		locationCity();

		//mSwipeRefreshLayout = (SwipeRefreshLayout) currentView.findViewById(R.id.swiperefreshlayout);
		mPullToRefreshListView = (PullToRefreshListView) currentView.findViewById(R.id.listview);

//		mSwipeRefreshLayout.setOnRefreshListener(this);
//		mSwipeRefreshLayout.setColorSchemeResources(R.color.swiperefresh_color1, R.color.swiperefresh_color2, R.color.swiperefresh_color3, R.color.swiperefresh_color4);

        mListView = mPullToRefreshListView.getRefreshableView();
        mHeadView = getActivity().getLayoutInflater().inflate(R.layout.public_header_index_content, null);
		mListView.addHeaderView(mHeadView, null, false);

		view_search_layout = (LinearLayout) currentView.findViewById(R.id.view_search_layout);

		llscroll = (LinearLayout) mHeadView.findViewById(R.id.llscroll);
		llscroll.setLayoutParams(AppUtils.getParams(fatherActivity));

		bottom_adt_view = (RelativeLayout) currentView.findViewById(R.id.bottom_adt_view);
		iv_close_adt = (ImageView) currentView.findViewById(R.id.iv_close_adt);
		ll_bottom_adt = (LinearLayout) currentView.findViewById(R.id.ll_bottom_adt);

		InitViewPager();

		if (Preferences.getBoolean("index_01", false, getActivity())) {
			ViewUtils.setGone(bottom_adt_view);
		} else {
			InitBottomAdt();
		}
        mPullToRefreshListView.setMode(mPullToRefreshListView.getMode() == PullToRefreshBase.Mode.BOTH ? PullToRefreshBase.Mode.PULL_FROM_START
                : PullToRefreshBase.Mode.BOTH);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(
                    PullToRefreshBase<ListView> refreshView) {
                // 获取当前时间并格式化
                String label = DateUtils.formatDateTime(
                        getActivity(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 设置刷新文本说明(刷新过程中)
                mPullToRefreshListView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
                mPullToRefreshListView.getLoadingLayoutProxy().setPullLabel("下拉刷新");
                mPullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel("松开开始刷新数据");
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
                        "最后更新时间:" + label);
				sendReuqest(lat, lng);
				new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(
                    PullToRefreshBase<ListView> refreshView) {

                // 上一次请求已经没有数据了
                mPullToRefreshListView.getLoadingLayoutProxy().setPullLabel(
                        "已经全部数据加载完毕...");
                mPullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel(
                        "已经全部数据加载完毕...");
				sendReuqest(lat, lng);
				new GetDataTask().execute();
//				}

            }
        });
        mPullToRefreshListView.getLoadingLayoutProxy().setLastUpdatedLabel(
                "上次刷新时间");
        mPullToRefreshListView.getLoadingLayoutProxy()
                .setPullLabel("下拉刷新");
//          mPullRefreshScrollView.getLoadingLayoutProxy().setRefreshingLabel(
//                      "refreshingLabel");
        mPullToRefreshListView.getLoadingLayoutProxy().setReleaseLabel(
                "松开即可刷新");
	}

	@Override
	public void onResume() {
		super.onResume();
		if(AppContext.getInstance().getUserInfo()!=null){
			ukey = AppContext.getInstance().getUserInfo().getUkey();
		}
	}

	private void locationCity() {
		if (NetUtils.isOnline()) {
			locationService = AppContext.getInstance().locationService;
			// 获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
			locationService.registerListener(mListener);
			locationService.setLocationOption(locationService.getDefaultLocationClientOption());
			DialogUtils.showProgressDialogWithMessage(getActivity(), "正在定位中");
			locationService.start();
		} else {
			ToastUtils.showToast(getActivity(), "当前无网络连接");
		}

	}

	double lat = 0.0;
	double lng = 0.0;

	private BDLocationListener mListener = new BDLocationListener() {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (null != location && location.getLocType() != BDLocation.TypeServerError) {
				//ToastUtils.showToast(getActivity(), "定位成功");
				locationService.stop();
				DialogUtils.dismiss();

				String cityCode = location.getCityCode();
				String city = location.getCity();
				String district = location.getDistrict();
				String street = location.getStreet();
				lat = location.getLatitude();
				lng = location.getLongitude();
				UserInfo userInfo = AppContext.getInstance().getUserInfo();
				if(userInfo!=null){
					userInfo.setLat(lat + "");
					userInfo.setLng(lng + "");
					AppContext.doLogin(userInfo);
				}else{
					Preferences.saveString("lat", lat+"", getActivity());
					Preferences.saveString("lng", lng+"", getActivity());
				}
				Logger.e("TAG", "城市编码：" + cityCode);
				Logger.e("TAG", "城市：" + city);
				Logger.e("TAG", "区：" + district);//cityCode, city, district, street,
				UtilPreference.saveString(getActivity(),"lat1",String.valueOf(lat));
				UtilPreference.saveString(getActivity(),"lng1",String.valueOf(lng));
				sendReuqest(lat, lng);
			}
		}

	};

	@Override
	public void initData() {
        //locationCity();

	}

	@Override
	public void initListener() {
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {


					DishModel dishModel = mAdapter.getItem(position - 2);
					if (dishModel != null) {
						Intent mIntent = new Intent(getActivity(), ShopDetailActivity.class);
						//mIntent.putExtra("dish_model", dishModel);
						mIntent.putExtra("shop_id", dishModel.getId());
						mIntent.putExtra("distance",dishModel.getDistance());
						startActivity(mIntent);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		iv_close_adt.setOnClickListener(this);
		view_search_layout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_close_adt:
			Preferences.saveBoolean("index_01", true, getActivity());
			ViewUtils.setGone(bottom_adt_view);
			break;

		case R.id.view_search_layout:
			startActivity(new Intent(getActivity(), SearchActivity.class));
			break;

		default:
			break;
		}
	}

	/**
	 * 请求列表数据
	 *
	 * @param lat
	 * @param lng
	 */
	private void sendReuqest(double lat, double lng) {
		if (NetUtils.isOnline()) {
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("ukey", ukey);
			params.put("keyword", "");
			params.put("city", "");// cityCode
			params.put("area", "0");// 全市时传递0
			params.put("area1", "0");// 镇id 全市、全区传递0
			params.put("style", "");// 菜系id
			params.put("lng", lng + "");// 经度
			params.put("lat", lat + "");// 纬度
			params.put("is_index","1");
			FoodClientApi.post("Index/lists", params, new JsonResponseCallback<BizResult>() {

				@Override
				public void onSuccess(int statusCode, BizResult body) {
					// TODO Auto-generated method stub
					Logger.e("TAG", "" + JSON.toJSONString(body));

					if (body != null && body.getCode() == 1) {
						List<DishModel> dishList = JSON.parseArray(body.getData(), DishModel.class);
						mAdapter = new DishAdapter(getActivity(), dishList);
						mPullToRefreshListView.setAdapter(mAdapter);
					}

					//setSwipeRefreshLoadedState();
				}

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
					super.onFailure(arg0, arg1, arg2, arg3);

				}

                @Override
                public void onFinish() {
                    super.onFinish();
                    mPullToRefreshListView.onRefreshComplete();
                }
            });
		} else {
			ToastUtils.showToast(getActivity(), "当前无网络连接");
		}

	}

	/**
	 * 广告
	 */
	private void InitViewPager() {
		if (NetUtils.isOnline()) {
			TreeMap<String, String> params = new TreeMap<String, String>();
			params.put("cat_id", "2");
			params.put("create_time", System.currentTimeMillis() / 1000 + "");
			FoodClientApi.post("Ad/adList", params, new JsonResponseCallback<BizResult>() {

				@Override
				public void onSuccess(int statusCode, BizResult body) {
//					Logger.e("TAG", "广告信息：" + JSON.toJSONString(body));
					if (body.getCode() == 1) {
						List<Banner> bannerList = JSON.parseArray(body.getData(), Banner.class);
						if (bannerList != null) {
							BizResultOfBanner banner = new BizResultOfBanner();
							banner.setBannerList(bannerList);
							llscroll.addView(new Advertisements(fatherActivity, fatherActivity.getLayoutInflater(), 3000).initView(banner));
						} else {
							View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
							llscroll.addView(view);
						}
					} else {
						View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
						llscroll.addView(view);
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					super.onFailure(statusCode, headers, responseBody, error);
					View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
					llscroll.addView(view);
				}
			});
		} else {
			View view = fatherActivity.getLayoutInflater().inflate(R.layout.advertisement_item_fitcenter, null);
			llscroll.addView(view);
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

//	@Override
//	public void onRefresh() {
//		// 设置顶部正在刷新
//		mListView.setSelection(0);
//		setSwipeRefreshLoadingState();
//		InitViewPager();
//		if (Preferences.getBoolean("index_01", false, getActivity())) {
//			ViewUtils.setGone(bottom_adt_view);
//		} else {
//			InitBottomAdt();
//		}
//		sendReuqest(lat, lng);
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
private class GetDataTask extends AsyncTask<Void, Void, String[]> {
	@Override
	protected String[] doInBackground(Void... params) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new String[0];
	}

	@Override
	protected void onPostExecute(String[] result) {
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshListView.onRefreshComplete();
		super.onPostExecute(result);
	}
}
}
