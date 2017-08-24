package com.wbteam.onesearch.app.module.shop;//package com.wbteam.onesearch.app.module.shop;
//
//import java.util.TreeMap;
//
//import org.apache.http.Header;
//
//import android.content.Intent;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSON;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.wbteam.app.base.BaseActivity;
//import com.wbteam.ioc.annotation.ContentView;
//import com.wbteam.ioc.annotation.OnClick;
//import com.wbteam.ioc.annotation.ViewInject;
//import com.wbteam.onesearch.R;
//import com.wbteam.onesearch.app.AppConfig;
//import com.wbteam.onesearch.app.AppContext;
//import com.wbteam.onesearch.app.adapter.ViewPageFragmentAdapter;
//import com.wbteam.onesearch.app.file.cache.Imageloader;
//import com.wbteam.onesearch.app.http.FoodClientApi;
//import com.wbteam.onesearch.app.http.JsonResponseCallback;
//import com.wbteam.onesearch.app.model.BizResult;
//import com.wbteam.onesearch.app.model.ShopDetailModel;
//import com.wbteam.onesearch.app.model.UserInfo;
//import com.wbteam.onesearch.app.module.shoptype.ShopStyleItem;
//import com.wbteam.onesearch.app.ui.ShopAddressActivity;
//import com.wbteam.onesearch.app.utils.DateUtils;
//import com.wbteam.onesearch.app.utils.DialogUtils;
//import com.wbteam.onesearch.app.utils.JumpUtils;
//import com.wbteam.onesearch.app.utils.Logger;
//import com.wbteam.onesearch.app.utils.NetUtils;
//import com.wbteam.onesearch.app.utils.Preferences;
//import com.wbteam.onesearch.app.utils.StringUtils;
//import com.wbteam.onesearch.app.utils.ToastUtils;
//import com.wbteam.onesearch.app.weight.CircleImageView;
//import com.wbteam.onesearch.app.weight.DoubleScrollView;
//import com.wbteam.onesearch.app.weight.DoubleScrollViewPager;
//import com.wbteam.onesearch.app.weight.TweetTextView;
//
///**
// * 店铺详情
// * 
// * @autor:码农哥
// * @version:1.0
// * @created:2016-9-15 上午12:12:22
// * @contact:QQ-441293364 TEL-15105695563
// **/
//@ContentView(R.layout.activity_shop_detail)//OnTabReselectListener,
//public class ShopDetailActivity2 extends BaseActivity implements  OnClickListener {
//
//	public static final String BUNDLE_KEY_TYPE = "BUNDLE_KEY_TYPE";
//	public static final String BUNDLE_KEY_ID = "BUNDLE_KEY_ID";
//
//	public final static int TYPE_INDEX_01 = 0;
//	public final static int TYPE_INDEX_02 = 1;
//	public final static int TYPE_INDEX_03 = 2;
//	public final static int TYPE_INDEX_04 = 3;
//
//	@ViewInject(R.id.titleLeftView)
//	private ImageView titleLeftView;
//
//	@ViewInject(R.id.titleCenterView)
//	private TextView titleCenterView;
//
//	@ViewInject(R.id.iv_share_restaurant)
//	private ImageView iv_share_restaurant;
//	
//	@ViewInject(R.id.iv_collection_restaurant)
//	private ImageView iv_collection_restaurant;
//
//	@ViewInject(R.id.iv_user_avatar)
//	private CircleImageView iv_user_avatar;
//
//	@ViewInject(R.id.tv_shop_distance)
//	private TextView tv_shop_distance;
//
//	@ViewInject(R.id.tv_shop_address)
//	private TweetTextView tv_shop_address;
//	
//	@ViewInject(R.id.tv_shop_phone)
//	private TextView tv_shop_phone;
//
//	@ViewInject(R.id.tv_business_time)
//	private TextView tv_business_time;
//
//	@ViewInject(R.id.iv_park_icon)
//	private ImageView iv_park_icon;
//
////	@ViewInject(R.id.pager_tabstrip)
////	protected PagerSlidingTabStrip mTabStrip;
//
//	@ViewInject(R.id.tabLayout)
//	protected TabLayout mTabLayout;
//	
//	@ViewInject(R.id.layoutContent)
//	private DoubleScrollView scrollView;
//	
//	@ViewInject(R.id.viewPager)
//	private DoubleScrollViewPager viewPager;
//
//	@ViewInject(R.id.layoutTabTitle)
//	private View layoutTabTitle;
//	
//	@ViewInject(R.id.ll_address_info)
//	protected LinearLayout ll_address_info;
//
//	@ViewInject(R.id.ll_style_info)
//	protected LinearLayout ll_style_info;
//	
//	@ViewInject(R.id.layout_payment)
//	protected LinearLayout layout_payment;
//	
//	@ViewInject(R.id.view_call_phone)
//	protected RelativeLayout view_call_phone;
//	
//	@ViewInject(R.id.tv_goto_here)
//	private TextView tv_goto_here;
//	
//	@ViewInject(R.id.iv_shop_bg)
//	private ImageView iv_shop_bg;
//	
//	protected ViewPageFragmentAdapter mTabsAdapter;
//
////	private DishModel mDishModel = null;
//	private UserInfo mUserInfo = null;
//
//	private Imageloader mImageloader = null;
//	private ShopDetailModel detailModel = null;
//	
//	private String shop_id = null;
//
//	@Override
//	public void initIntent() {
//		mUserInfo = AppContext.getInstance().getUserInfo();
////		mDishModel = (DishModel) getIntent().getSerializableExtra("dish_model");
//		
//		shop_id = getIntent().getStringExtra("shop_id");
//		
//		mImageloader = Imageloader.getInstance(context);
////		callPhone
//	}
//
//	private SAdapter adapter;
//	
//	@Override
//	public void initListener() {
//		
//		titleLeftView.setOnClickListener(this);
//		iv_collection_restaurant.setOnClickListener(this);
//		ll_address_info.setOnClickListener(this);
//		
//		scrollView.setupTitleView(findViewById(R.id.layoutTop));
//        scrollView.setContentView(findViewById(R.id.layoutContentBottom));
//			
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                scrollView.setContentInnerScrollableView(adapter.getSlidableView(position));
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        
//        adapter = new SAdapter(getSupportFragmentManager(),shop_id);
//        viewPager.setAdapter(adapter);
//		mTabLayout.setupWithViewPager(viewPager);
//		
//		scrollView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                scrollView.setContentInnerScrollableView(adapter.getSlidableView(0));
//                viewPager.setTagHeight(scrollView.getMeasuredHeight() - layoutTabTitle.getMeasuredHeight());
//            }
//        },100);
//		
//	}
//	
//	@Override
//	public void initData() {
//		getShopDetail(shop_id);
//	}
//	
//	
//
////	protected void setScreenPageLimit() {
////		mViewPager.setOffscreenPageLimit(2);
////	}
////
////	protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
////		String[] title = getResources().getStringArray(R.array.array_shop_detail);
////		adapter.addTab(title[0], "index_01", ShopFragmentIndex01.class, getBundle(TYPE_INDEX_01, shop_id));
////		adapter.addTab(title[1], "index_02", ShopFragmentIndex02.class, getBundle(TYPE_INDEX_02, shop_id));
////		adapter.addTab(title[2], "index_03", ShopFragmentIndex03.class, getBundle(TYPE_INDEX_03, shop_id));
////		adapter.addTab(title[3], "index_04", ShopFragmentIndex04.class, getBundle(TYPE_INDEX_04, shop_id));
////	}
//
////	private Bundle getBundle(int newType, String id) {
////		Bundle bundle = new Bundle();
////		bundle.putInt(BUNDLE_KEY_TYPE, newType);
////		bundle.putString(BUNDLE_KEY_ID, id);
////		return bundle;
////	}
//
////	@Override
////	public void onTabReselect() {
////		try {
////			int currentIndex = mViewPager.getCurrentItem();
////			Fragment currentFragment = getSupportFragmentManager().getFragments().get(currentIndex);
////			if (currentFragment != null && currentFragment instanceof OnTabReselectListener) {
////				OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
////				listener.onTabReselect();
////			}
////		} catch (NullPointerException e) {
////			e.printStackTrace();
////		}
////	}
//	
////	PlatformActionListener paListener = new PlatformActionListener() {
////		
////		@Override
////		public void onError(Platform arg0, int arg1, Throwable arg2) {
////			if (arg0.getName().equalsIgnoreCase(WechatFavorite.NAME)) {
////				ToastUtils.showToast(context, "朋友圈分享错误");
////				Logger.e("TAG", "错误信息："+arg2.getMessage());
////				Logger.e("TAG", "错误信息："+JSON.toJSONString(arg0));
////				Logger.e("TAG", "错误信息："+arg1);
////			} else if (arg0.getName().equalsIgnoreCase(Wechat.NAME)) {
////				ToastUtils.showToast(context, "微信分享错误");
////				Logger.e("TAG", "错误信息："+arg2.getMessage());
////			} else if (arg0.getName().equalsIgnoreCase(QQ.NAME)) {
////				ToastUtils.showToast(context, "QQ分享错误");
////			} else if (arg0.getName().equalsIgnoreCase(QZone.NAME)) {
////				ToastUtils.showToast(context, "QQ空间分享错误");
////			} else if (arg0.getName().equalsIgnoreCase(SinaWeibo.NAME)) {
////				ToastUtils.showToast(context, "新浪微博分享错误");
////			}
////		}
////		
////		@Override
////		public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
////			if (arg0.getName().equalsIgnoreCase(WechatFavorite.NAME)) {
////				ToastUtils.showToast(context, "朋友圈分享完成");
////			} else if (arg0.getName().equalsIgnoreCase(Wechat.NAME)) {
////				ToastUtils.showToast(context, "微信分享完成");
////			} else if (arg0.getName().equalsIgnoreCase(QQ.NAME)) {
////				ToastUtils.showToast(context, "QQ分享完成");
////			} else if (arg0.getName().equalsIgnoreCase(QZone.NAME)) {
////				ToastUtils.showToast(context, "QQ空间分享完成");
////			} else if (arg0.getName().equalsIgnoreCase(SinaWeibo.NAME)) {
////				ToastUtils.showToast(context, "新浪微博分享完成");
////			}
////		}
////		
////		@Override
////		public void onCancel(Platform arg0, int arg1) {
////			if (arg0.getName().equalsIgnoreCase(WechatFavorite.NAME)) {
////				ToastUtils.showToast(context, "取消朋友圈分享");
////			} else if (arg0.getName().equalsIgnoreCase(Wechat.NAME)) {
////				ToastUtils.showToast(context, "取消微信分享");
////			} else if (arg0.getName().equalsIgnoreCase(QQ.NAME)) {
////				ToastUtils.showToast(context, "取消QQ分享");
////			} else if (arg0.getName().equalsIgnoreCase(QZone.NAME)) {
////				ToastUtils.showToast(context, "取消QQ空间分享");
////			} else if (arg0.getName().equalsIgnoreCase(SinaWeibo.NAME)) {
////				ToastUtils.showToast(context, "取消新浪微博分享");
////			}
////		}
////	};
//
//	@OnClick({ R.id.view_call_phone, R.id.iv_collection_restaurant, R.id.iv_share_restaurant, R.id.titleLeftView, R.id.tv_goto_here })
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.view_call_phone:
//			if (null != detailModel) {
//				JumpUtils.callPhone(ShopDetailActivity2.this, detailModel.getPhone());
//			}
//			break;
//		
//		case R.id.titleLeftView:
//			finish();
//			break; 
//
//		case R.id.iv_collection_restaurant:
//			if (mUserInfo == null) {
//				ToastUtils.showToast(context, "未登录");
//				return;
//			}
//
//			if (detailModel != null) {
//				if (detailModel.isIs_collect()) {
//					userDelCollect(shop_id);
//				} else {
//					userCollect(shop_id);
//				}
//			}
//			break;
//			
//		case R.id.iv_share_restaurant:
//			if(detailModel!=null){
////				DialogUtils.chooseShareDialog(this, detailModel.getLogo(),detailModel.getTitle(), paListener);
//				String link = "http://onesearch.wbteam.cn/index.php?s=/Out/Content/res_detail/id/" + shop_id;
//				DialogUtils.showShare(ShopDetailActivity2.this,detailModel.getLogo(),detailModel.getTitle(),null,link);
//			}
////			DialogUtils.showShare(context, null, false);
//			break;
//			
////		case R.id.ll_address_info:
////			try {
////				if(detailModel!=null){
////					Intent intent = new Intent(context, ShopAddressActivity.class);
////					intent.putExtra("shop_detail", detailModel);
////					startActivity(intent);
////				}
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
////			break;
//			
//		case R.id.tv_goto_here:
//			try {
//				if(detailModel!=null){
//					Intent intent = new Intent(context, ShopAddressActivity.class);
//					intent.putExtra("shop_detail", detailModel);
//					startActivity(intent);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	private void getShopDetail(String id) {
//		if (NetUtils.isOnline()) {
//			TreeMap<String, String> params = new TreeMap<String, String>();
//			if(mUserInfo!=null){
//				params.put("ukey", mUserInfo.getUkey());
//			}else{
//				params.put("ukey", "");
//			}
//			params.put("id", id);// 餐厅id
//			params.put("ad_id", "");//
////			params.put("lng", "");//
////			params.put("lat", "");//
//			params.put("lng", Preferences.getString("lng", "", context));// 经度
//			params.put("lat", Preferences.getString("lat", "", context));// 纬度
//			
//			DialogUtils.showProgressDialogWithMessage(context, "正在获取商铺基本信息");
//			FoodClientApi.post("Res/detail", params, new JsonResponseCallback<BizResult>() {
//
//				@Override
//				public void onSuccess(int statusCode, BizResult body) {
//					Logger.e("TAG", "==getShopDetail餐厅详情==" + JSON.toJSONString(body));
//					try {
//						DialogUtils.dismiss();
//						if (body != null && body.getCode() == 1) {
//							detailModel = JSON.parseObject(body.getData(), ShopDetailModel.class);
//							setShopBaseInfo(detailModel); 
//							ll_style_info.addView(new ShopStyleItem(context, getLayoutInflater()).initView(detailModel.getStyle()));
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		} else {
//			ToastUtils.showToast(context, "当前无网络链接");
//		}
//	}
//
//	/**
//	 * 设置餐厅的基本信息
//	 * 
//	 * @param detailModel
//	 */
//	private void setShopBaseInfo(ShopDetailModel detailModel) {
//		if (detailModel != null) {
//			ImageLoader.getInstance().displayImage(AppConfig.IMAGE_URL_HOST + detailModel.getBackimg(), iv_shop_bg);
//			titleCenterView.setText(detailModel.getTitle());
//			mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + detailModel.getLogo(), iv_user_avatar);
//			tv_shop_distance.setText(detailModel.getDistance()/1000+"KM");
//			tv_shop_address.setText(detailModel.getAddress());
//			tv_shop_phone.setText(detailModel.getPhone());
//			String beginTime = detailModel.getB_time();
//			String endTime = detailModel.getE_time();
//					
//			if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
//				tv_business_time.setText("09:00-23:00");
//			} else {
//				tv_business_time.setText(DateUtils.parseHHmm(beginTime) + "-" + DateUtils.parseHHmm(endTime));
//			}
//
//			if (detailModel.getIs_park() == 0) {
//				iv_park_icon.setImageResource(R.drawable.icon_park_normal);
//			} else {
//				iv_park_icon.setImageResource(R.drawable.icon_park_pressed);
//			}
//			
//			if(detailModel.isIs_collect()){
//				iv_collection_restaurant.setImageResource(R.drawable.icon_collection_pressed);
//			}else{
//				iv_collection_restaurant.setImageResource(R.drawable.icon_collection_white);
//			}
//			try {
//				String[] cashStrs = detailModel.getCash().split(",");
//				for (int i = 0; i < cashStrs.length; i++) {
//					ImageView imageView = new ImageView(context);
//					imageView.setPadding(0, 2, 2, 2);
//					if(cashStrs[i].equals("现金")){
//						imageView.setImageResource(R.drawable.img_money_icon);
//					}else if(cashStrs[i].equals("微信")){
//						imageView.setImageResource(R.drawable.img_wxpay_icon);
//					}else if(cashStrs[i].equals("支付宝")){
//						imageView.setImageResource(R.drawable.img_alipay_icon);
//					}else if(cashStrs[i].equals("银联")){
//						imageView.setImageResource(R.drawable.img_ylpay_icon);
//					}
//					layout_payment.addView(imageView);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				TextView textView = new TextView(context);
//				textView.setText("未提供");
//				textView.setTextColor(getResources().getColor(R.color.white));
//				textView.setTextSize(13);
//				layout_payment.addView(textView);
//			}
//		}
//	}
//
//	/**
//	 * 用户收藏
//	 * 
//	 * @param id
//	 */
//	private void userCollect(String id) {
//		Logger.e("TAG", "userCollect餐厅Id:" + id);
//		if (NetUtils.isOnline()) {
//			TreeMap<String, String> params = new TreeMap<String, String>();
//			params.put("ukey", mUserInfo.getUkey());
//			params.put("content_id", id);// 餐厅id
//			DialogUtils.showProgressDialogWithMessage(context, "正在收藏中");
//			FoodClientApi.post("User/collect", params, new JsonResponseCallback<BizResult>() {
//
//				@Override
//				public void onSuccess(int statusCode, BizResult body) {
//					DialogUtils.dismiss();
//					Logger.e("TAG", "==用户收藏==" + JSON.toJSONString(body));
//					if(body!=null&&body.getCode()==1){
//						iv_collection_restaurant.setImageResource(R.drawable.icon_collection_pressed);
//						detailModel.setIs_collect(true);
//						ToastUtils.showToast(context, "收藏成功！");
//					}
//				}
//				
//				@Override
//				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
//					super.onFailure(arg0, arg1, arg2, arg3);
//					DialogUtils.dismiss();
//				}
//			});
//		} else {
//			ToastUtils.showToast(context, "当前无网络链接");
//		}
//	}
//
//	/**
//	 * 用户取消收藏
//	 * 
//	 * @param id
//	 */
//	private void userDelCollect(String id) {
//		Logger.e("TAG", "userDelCollect餐厅Id:" + id);
//		if (NetUtils.isOnline()) {
//			TreeMap<String, String> params = new TreeMap<String, String>();
//			params.put("ukey", mUserInfo.getUkey());
//			params.put("content_id", id);// 餐厅id
//			DialogUtils.showProgressDialogWithMessage(context, "正在取消收藏");
//			FoodClientApi.post("User/del_collect", params, new JsonResponseCallback<BizResult>() {
//
//				@Override
//				public void onSuccess(int statusCode, BizResult body) {
//					Logger.e("TAG", "==用户取消收藏==" + JSON.toJSONString(body));
//					DialogUtils.dismiss();
//					if(body!=null&&body.getCode()==1){
//						detailModel.setIs_collect(false);
//						iv_collection_restaurant.setImageResource(R.drawable.icon_collection_white);
//						ToastUtils.showToast(context, "取消收藏成功！");
//					}
//				}
//			});
//		} else {
//			ToastUtils.showToast(context, "当前无网络链接");
//		}
//	}
//
//}
//
//
//
//
////package com.wbteam.onesearch.app.module.shop;
////
////import java.util.ArrayList;
////import java.util.List;
////import java.util.TreeMap;
////
////import org.apache.http.Header;
////
////import android.content.Intent;
////import android.support.v4.app.Fragment;
////import android.support.v4.view.ViewPager;
////import android.view.View;
////import android.view.View.OnClickListener;
////import android.widget.ImageView;
////import android.widget.LinearLayout;
////import android.widget.RadioGroup;
////import android.widget.RelativeLayout;
////import android.widget.TextView;
////
////import com.alibaba.fastjson.JSON;
////import com.wbteam.app.base.BaseActivity;
////import com.wbteam.ioc.annotation.ContentView;
////import com.wbteam.ioc.annotation.OnClick;
////import com.wbteam.ioc.annotation.ViewInject;
////import com.wbteam.onesearch.R;
////import com.wbteam.onesearch.app.AppConfig;
////import com.wbteam.onesearch.app.AppContext;
////import com.wbteam.onesearch.app.adapter.BottomNavigationAdapter;
////import com.wbteam.onesearch.app.adapter.ViewPageFragmentAdapter;
////import com.wbteam.onesearch.app.file.cache.Imageloader;
////import com.wbteam.onesearch.app.http.FoodClientApi;
////import com.wbteam.onesearch.app.http.JsonResponseCallback;
////import com.wbteam.onesearch.app.model.BizResult;
////import com.wbteam.onesearch.app.model.ShopDetailModel;
////import com.wbteam.onesearch.app.model.UserInfo;
////import com.wbteam.onesearch.app.module.shoptype.ShopStyleItem;
////import com.wbteam.onesearch.app.ui.ShopAddressActivity;
////import com.wbteam.onesearch.app.utils.DateUtils;
////import com.wbteam.onesearch.app.utils.DialogUtils;
////import com.wbteam.onesearch.app.utils.JumpUtils;
////import com.wbteam.onesearch.app.utils.Logger;
////import com.wbteam.onesearch.app.utils.NetUtils;
////import com.wbteam.onesearch.app.utils.Preferences;
////import com.wbteam.onesearch.app.utils.StringUtils;
////import com.wbteam.onesearch.app.utils.ToastUtils;
////import com.wbteam.onesearch.app.weight.CircleImageView;
////import com.wbteam.onesearch.app.weight.MyScrollView;
////import com.wbteam.onesearch.app.weight.MyScrollView.OnScrollListener;
////import com.wbteam.onesearch.app.weight.TweetTextView;
////
/////**
//// * 店铺详情
//// * 
//// * @autor:码农哥
//// * @version:1.0
//// * @created:2016-9-15 上午12:12:22
//// * @contact:QQ-441293364 TEL-15105695563
//// **/
////@ContentView(R.layout.activity_shop_detail)
////public class ShopDetailActivity extends BaseActivity implements OnClickListener, OnScrollListener {
////
////	public static final String BUNDLE_KEY_TYPE = "BUNDLE_KEY_TYPE";
////	public static final String BUNDLE_KEY_ID = "BUNDLE_KEY_ID";
////
////	public final static int TYPE_INDEX_01 = 0;
////	public final static int TYPE_INDEX_02 = 1;
////	public final static int TYPE_INDEX_03 = 2;
////	public final static int TYPE_INDEX_04 = 3;
////
////	@ViewInject(R.id.titleLeftView)
////	private ImageView titleLeftView;
////
////	@ViewInject(R.id.titleCenterView)
////	private TextView titleCenterView;
////
////	@ViewInject(R.id.iv_share_restaurant)
////	private ImageView iv_share_restaurant;
////	
////	@ViewInject(R.id.iv_collection_restaurant)
////	private ImageView iv_collection_restaurant;
////
////	@ViewInject(R.id.iv_user_avatar)
////	private CircleImageView iv_user_avatar;
////
////	@ViewInject(R.id.tv_shop_distance)
////	private TextView tv_shop_distance;
////
////	@ViewInject(R.id.tv_shop_address)
////	private TweetTextView tv_shop_address;
////	
////
////	@ViewInject(R.id.tv_shop_phone)
////	private TextView tv_shop_phone;
////
////	@ViewInject(R.id.tv_business_time)
////	private TextView tv_business_time;
////
////	@ViewInject(R.id.iv_park_icon)
////	private ImageView iv_park_icon;
////
//////	@ViewInject(R.id.pager_tabstrip)
//////	protected PagerSlidingTabStrip mTabStrip;
////
////	@ViewInject(R.id.ll_tabLayout_01)
////	private LinearLayout ll_tabLayout_01;
////
////	@ViewInject(R.id.ll_tabLayout_02)
////	private LinearLayout ll_tabLayout_02;
////	
////	@ViewInject(R.id.tab_menu)
////	private RadioGroup tab_menu;
////	
////	@ViewInject(R.id.pager)
////	protected ViewPager mViewPager;
////	
////	@ViewInject(R.id.ll_address_info)
////	protected LinearLayout ll_address_info;
////
////	@ViewInject(R.id.ll_style_info)
////	protected LinearLayout ll_style_info;
////	
////	@ViewInject(R.id.layout_payment)
////	protected LinearLayout layout_payment;
////	
////	@ViewInject(R.id.view_call_phone)
////	protected RelativeLayout view_call_phone;
////	
////	@ViewInject(R.id.tv_goto_here)
////	private TextView tv_goto_here;
////	
////	@ViewInject(R.id.myScrollView)
////	private MyScrollView myScrollView;
////	
////	@ViewInject(R.id.ll_headView)
////	private RelativeLayout ll_headView;
////	
////	protected ViewPageFragmentAdapter mTabsAdapter;
////
//////	private DishModel mDishModel = null;
////	private UserInfo mUserInfo = null;
////
////	private Imageloader mImageloader = null;
////	private ShopDetailModel detailModel = null;
////	
////	private String shop_id = null;
////
////	private BottomNavigationAdapter adapter;
////	
////	@Override
////	public void initIntent() {
////		mUserInfo = AppContext.getInstance().getUserInfo();
//////		mDishModel = (DishModel) getIntent().getSerializableExtra("dish_model");
////		
////		shop_id = getIntent().getStringExtra("shop_id");
////		
////		mImageloader = Imageloader.getInstance(context);
//////		callPhone
////	}
////
////	 private int searchLayoutTop;
////	 
////	 private List<Fragment> fragmengList;
////	@Override
////	public void initListener() {
//////		mTabsAdapter = new ViewPageFragmentAdapter(getSupportFragmentManager(), mTabStrip, mViewPager);
//////		setScreenPageLimit();
//////		onSetupTabAdapter(mTabsAdapter);
////		myScrollView.setOnScrollListener(this);  
////		
////		fragmengList = new ArrayList<Fragment>();
////		
////		ShopFragmentIndex01 index01 = new ShopFragmentIndex01(shop_id);
////		ShopFragmentIndex02 index02 = new ShopFragmentIndex02(shop_id);
////		ShopFragmentIndex03 index03 = new ShopFragmentIndex03(shop_id);
////		ShopFragmentIndex04 index04 = new ShopFragmentIndex04(shop_id);
////		fragmengList.add(index01);
////		fragmengList.add(index02);
////		fragmengList.add(index03);
////		fragmengList.add(index04);
////		
////		adapter = new BottomNavigationAdapter(getSupportFragmentManager(), fragmengList);
////		mViewPager.setAdapter(adapter);
////		mViewPager.setCurrentItem(0);
////		
////
////		titleLeftView.setOnClickListener(this);
////		iv_collection_restaurant.setOnClickListener(this);
////		ll_address_info.setOnClickListener(this);
////		
//////		myScrollView.smoothScrollTo(0, 0);
////		
////	}
////	
////	@Override
////	public void onWindowFocusChanged(boolean hasFocus) {
////		super.onWindowFocusChanged(hasFocus);    
////        if(hasFocus){  
////        	searchLayoutTop = ll_headView.getBottom();//获取searchLayout的顶部位置
////        }
////	}
////
////	// 监听滚动Y值变化，通过addView和removeView来实现悬停效果
////	@Override
////	public void onScroll(int scrollY) {
////		if (scrollY >= searchLayoutTop) {
////			if (tab_menu.getParent() != ll_tabLayout_01) {
////				ll_tabLayout_02.removeView(tab_menu);
////				ll_tabLayout_01.addView(tab_menu);
////			}
////		} else {
////			if (tab_menu.getParent() != ll_tabLayout_02) {
////				ll_tabLayout_01.removeView(tab_menu);	
////				ll_tabLayout_02.addView(tab_menu);
////			}
////		}
////	}
////
////	@Override
////	public void initData() {
////		getShopDetail(shop_id);
////	}
////
////
////	@OnClick({ R.id.view_call_phone, R.id.iv_collection_restaurant, R.id.iv_share_restaurant, R.id.titleLeftView, R.id.tv_goto_here })
////	@Override
////	public void onClick(View v) {
////		switch (v.getId()) {
////		case R.id.view_call_phone:
////			if (null != detailModel) {
////				JumpUtils.callPhone(ShopDetailActivity.this, detailModel.getPhone());
////			}
////			break;
////		
////		case R.id.titleLeftView:
////			finish();
////			break; 
////
////		case R.id.iv_collection_restaurant:
////			if (mUserInfo == null) {
////				ToastUtils.showToast(context, "未登录");
////				return;
////			}
////
////			if (detailModel != null) {
////				if (detailModel.isIs_collect()) {
////					userDelCollect(shop_id);
////				} else {
////					userCollect(shop_id);
////				}
////			}
////			break;
////			
////		case R.id.iv_share_restaurant:
////			if(detailModel!=null){
//////				DialogUtils.chooseShareDialog(this, detailModel.getLogo(),detailModel.getTitle(), paListener);
////				DialogUtils.showShare(ShopDetailActivity.this,detailModel.getLogo(),detailModel.getTitle());
////			}
//////			DialogUtils.showShare(context, null, false);
////			break;
////			
//////		case R.id.ll_address_info:
//////			try {
//////				if(detailModel!=null){
//////					Intent intent = new Intent(context, ShopAddressActivity.class);
//////					intent.putExtra("shop_detail", detailModel);
//////					startActivity(intent);
//////				}
//////			} catch (Exception e) {
//////				e.printStackTrace();
//////			}
//////			break;
////			
////		case R.id.tv_goto_here:
////			try {
////				if(detailModel!=null){
////					Intent intent = new Intent(context, ShopAddressActivity.class);
////					intent.putExtra("shop_detail", detailModel);
////					startActivity(intent);
////				}
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
////			break;
////
////		default:
////			break;
////		}
////	}
////
////	private void getShopDetail(String id) {
////		if (NetUtils.isOnline()) {
////			TreeMap<String, String> params = new TreeMap<String, String>();
////			if(mUserInfo!=null){
////				params.put("ukey", mUserInfo.getUkey());
////			}else{
////				params.put("ukey", "");
////			}
////			params.put("id", id);// 餐厅id
////			params.put("ad_id", "");//
//////			params.put("lng", "");//
//////			params.put("lat", "");//
////			params.put("lng", Preferences.getString("lng", "", context));// 经度
////			params.put("lat", Preferences.getString("lat", "", context));// 纬度
////			
////			DialogUtils.showProgressDialogWithMessage(context, "正在获取商铺基本信息");
////			FoodClientApi.post("Res/detail", params, new JsonResponseCallback<BizResult>() {
////
////				@Override
////				public void onSuccess(int statusCode, BizResult body) {
////					Logger.e("TAG", "==getShopDetail餐厅详情==" + JSON.toJSONString(body));
////					try {
////						DialogUtils.dismiss();
////						if (body != null && body.getCode() == 1) {
////							detailModel = JSON.parseObject(body.getData(), ShopDetailModel.class);
////							setShopBaseInfo(detailModel); 
////							ll_style_info.addView(new ShopStyleItem(context, getLayoutInflater()).initView(detailModel.getStyle()));
////						}
////					} catch (Exception e) {
////						e.printStackTrace();
////					}
////				}
////			});
////		} else {
////			ToastUtils.showToast(context, "当前无网络链接");
////		}
////	}
////
////	/**
////	 * 设置餐厅的基本信息
////	 * 
////	 * @param detailModel
////	 */
////	private void setShopBaseInfo(ShopDetailModel detailModel) {
////		if (detailModel != null) {
////			titleCenterView.setText(detailModel.getTitle());
////			mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + detailModel.getLogo(), iv_user_avatar);
////			tv_shop_distance.setText(detailModel.getDistance()/1000+"KM");
////			tv_shop_address.setText(detailModel.getAddress());
////			tv_shop_phone.setText(detailModel.getPhone());
////			String beginTime = detailModel.getB_time();
////			String endTime = detailModel.getE_time();
////					
////			if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
////				tv_business_time.setText("09:00-23:00");
////			} else {
////				tv_business_time.setText(DateUtils.parseHHmm(beginTime) + "-" + DateUtils.parseHHmm(endTime));
////			}
////
////			if (detailModel.getIs_park() == 0) {
////				iv_park_icon.setImageResource(R.drawable.icon_park_normal);
////			} else {
////				iv_park_icon.setImageResource(R.drawable.icon_park_pressed);
////			}
////			
////			if(detailModel.isIs_collect()){
////				iv_collection_restaurant.setImageResource(R.drawable.icon_collection_pressed);
////			}else{
////				iv_collection_restaurant.setImageResource(R.drawable.icon_collection_white);
////			}
////			try {
////				String[] cashStrs = detailModel.getCash().split(",");
////				for (int i = 0; i < cashStrs.length; i++) {
////					ImageView imageView = new ImageView(context);
////					imageView.setPadding(0, 2, 2, 2);
////					if(cashStrs[i].equals("现金")){
////						imageView.setImageResource(R.drawable.img_money_icon);
////					}else if(cashStrs[i].equals("微信")){
////						imageView.setImageResource(R.drawable.img_wxpay_icon);
////					}else if(cashStrs[i].equals("支付宝")){
////						imageView.setImageResource(R.drawable.img_alipay_icon);
////					}else if(cashStrs[i].equals("银联")){
////						imageView.setImageResource(R.drawable.img_ylpay_icon);
////					}
////					layout_payment.addView(imageView);
////				}
////			} catch (Exception e) {
////				e.printStackTrace();
////				TextView textView = new TextView(context);
////				textView.setText("未提供");
////				textView.setTextColor(getResources().getColor(R.color.white));
////				textView.setTextSize(13);
////				layout_payment.addView(textView);
////			}
////		}
////	}
////
////	/**
////	 * 用户收藏
////	 * 
////	 * @param id
////	 */
////	private void userCollect(String id) {
////		Logger.e("TAG", "userCollect餐厅Id:" + id);
////		if (NetUtils.isOnline()) {
////			TreeMap<String, String> params = new TreeMap<String, String>();
////			params.put("ukey", mUserInfo.getUkey());
////			params.put("content_id", id);// 餐厅id
////			DialogUtils.showProgressDialogWithMessage(context, "正在收藏中");
////			FoodClientApi.post("User/collect", params, new JsonResponseCallback<BizResult>() {
////
////				@Override
////				public void onSuccess(int statusCode, BizResult body) {
////					DialogUtils.dismiss();
////					Logger.e("TAG", "==用户收藏==" + JSON.toJSONString(body));
////					if(body!=null&&body.getCode()==1){
////						iv_collection_restaurant.setImageResource(R.drawable.icon_collection_pressed);
////						detailModel.setIs_collect(true);
////						ToastUtils.showToast(context, "收藏成功！");
////					}
////				}
////				
////				@Override
////				public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
////					super.onFailure(arg0, arg1, arg2, arg3);
////					DialogUtils.dismiss();
////				}
////			});
////		} else {
////			ToastUtils.showToast(context, "当前无网络链接");
////		}
////	}
////
////	/**
////	 * 用户取消收藏
////	 * 
////	 * @param id
////	 */
////	private void userDelCollect(String id) {
////		Logger.e("TAG", "userDelCollect餐厅Id:" + id);
////		if (NetUtils.isOnline()) {
////			TreeMap<String, String> params = new TreeMap<String, String>();
////			params.put("ukey", mUserInfo.getUkey());
////			params.put("content_id", id);// 餐厅id
////			DialogUtils.showProgressDialogWithMessage(context, "正在取消收藏");
////			FoodClientApi.post("User/del_collect", params, new JsonResponseCallback<BizResult>() {
////
////				@Override
////				public void onSuccess(int statusCode, BizResult body) {
////					Logger.e("TAG", "==用户取消收藏==" + JSON.toJSONString(body));
////					DialogUtils.dismiss();
////					if(body!=null&&body.getCode()==1){
////						detailModel.setIs_collect(false);
////						iv_collection_restaurant.setImageResource(R.drawable.icon_collection_white);
////						ToastUtils.showToast(context, "取消收藏成功！");
////					}
////				}
////			});
////		} else {
////			ToastUtils.showToast(context, "当前无网络链接");
////		}
////	}
////
////}