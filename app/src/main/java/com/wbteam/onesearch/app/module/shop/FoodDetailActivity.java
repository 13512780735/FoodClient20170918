package com.wbteam.onesearch.app.module.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import uk.co.senab.photoview.PhotoView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.wbteam.app.base.BaseActivity;
import com.wbteam.ioc.annotation.ContentView;
import com.wbteam.ioc.annotation.ViewInject;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.http.FoodClientApi;
import com.wbteam.onesearch.app.http.JsonResponseCallback;
import com.wbteam.onesearch.app.model.BizResult;
import com.wbteam.onesearch.app.model.FoodModel;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.utils.Logger;
import com.wbteam.onesearch.app.utils.NetUtils;
import com.wbteam.onesearch.app.utils.ToastUtils;
import com.wbteam.onesearch.app.weight.HackyViewPager;
import com.wbteam.onesearch.app.weight.HeaderLayout;
import com.wbteam.onesearch.app.weight.HeaderLayout.OnLeftClickListener;
import com.wbteam.onesearch.app.weight.RecyclingPagerAdapter;

/**
 *  餐牌的详情
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-23  上午12:45:13
 * @contact:QQ-441293364 TEL-15105695563
 **/
@ContentView(R.layout.activity_food_detail)
public class FoodDetailActivity extends BaseActivity implements OnPageChangeListener {
	
	public static final String BUNDLE_KEY_IMAGES = "bundle_key_images";
	private static final String BUNDLE_KEY_INDEX = "bundle_key_index";
	
	private UserInfo mUserInfo = null;
	private String id = null;
	
	@ViewInject(R.id.header_title)
	private HeaderLayout headerLayout;
	
//	@ViewInject(R.id.photoview)
//	private PhotoView photoview;
//
//	private ProgressBar progress;
	
	@ViewInject(R.id.tv_viewpage_num)
	private TextView tv_viewpage_num;
	
	
	@ViewInject(R.id.view_pager)
	private HackyViewPager mViewPager;
	private SamplePagerAdapter mAdapter;
	private List<FoodModel> mImageUrls = null;
	
	private int index = 0;
	
	/**
	 * 显示图片的预览
	 * 
	 * @param context
	 * @param index
	 * @param images
	 */
	public static void showImagePrivew(Context context, int index, List<FoodModel> images) {
		Intent intent = new Intent(context, FoodDetailActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		Bundle bundle = new Bundle();
		bundle.putInt(BUNDLE_KEY_INDEX, index);
		bundle.putSerializable(BUNDLE_KEY_IMAGES, (Serializable) images);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	
	
	@Override
	public void initIntent() {
//		id = getIntent().getStringExtra(ShopDetailActivity.BUNDLE_KEY_ID);
		mUserInfo = AppContext.getInstance().getUserInfo();
		
		mImageUrls = (List<FoodModel>) getIntent().getSerializableExtra(BUNDLE_KEY_IMAGES);
		index = getIntent().getIntExtra(BUNDLE_KEY_INDEX, 0);
		
		mAdapter = new SamplePagerAdapter(this, mImageUrls);
		mViewPager.setAdapter(mAdapter);
		mViewPager.addOnPageChangeListener(this);
		mViewPager.setCurrentItem(index);
		onPageSelected(index);
		tv_viewpage_num.setText((index + 1) + "/" + mImageUrls.size());
	}
	
	@Override
	public void initListener() {
		headerLayout.setOnLeftImageViewClickListener(new OnLeftClickListener() {
			
			@Override
			public void onClick() {
				finish();
			}
		});
	}
	
	@Override
	public void initData() {
//		sendRequest();
	}
	
	private String food_detai_url = null;
	
	private void sendRequest(){
		if(NetUtils.isOnline()){
			TreeMap<String, String> params = new TreeMap<String, String>();
			if (mUserInfo != null) {
				params.put("ukey", mUserInfo.getUkey());
			} else {
				params.put("ukey", "");
			}
			params.put("id", id);// 餐厅id
			FoodClientApi.post("Res/foods_detail", params, new JsonResponseCallback<BizResult>() {
				
				@Override
				public void onSuccess(int statusCode, BizResult body) {
					Logger.e("TAG", "==餐牌详情==" + JSON.toJSONString(body));
					FoodModel foodModel = JSON.parseObject(body.getData(), FoodModel.class);
					setFoodDesc(foodModel);
					food_detai_url = AppConfig.FOOD_DETAIL_URL + foodModel.getId();
				}
			});
		}else{
			ToastUtils.showToast(context, "当前无网络链接");
		}
	}
	
	
	private void setFoodDesc(FoodModel foodModel) {
//		KJBitmap kjbitmap = new KJBitmap();
//		kjbitmap.displayWithDefWH(photoview, AppConfig.IMAGE_URL_HOST + foodModel.getLogo(), new ColorDrawable(0x000000), new ColorDrawable(0x000000), new BitmapCallBack() {
//			@Override
//			public void onPreLoad() {
//				super.onPreLoad();
//				progress.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onFinish() {
//				super.onFinish();
//				progress.setVisibility(View.GONE);
//			}
//
//			@Override
//			public void onFailure(Exception arg0) {
//				ToastUtils.showToast(context, "加载图片失败!");
//			}
//		});
	}
	
	static class SamplePagerAdapter extends RecyclingPagerAdapter {

		private List<FoodModel> images = new ArrayList<FoodModel>();
		private DisplayImageOptions options;
		private Context context;


		SamplePagerAdapter(Context context, List<FoodModel> images) {
			this.context = context;
			this.images = images;
			options = new DisplayImageOptions.Builder().cacheInMemory(true).postProcessor(new BitmapProcessor() {

				@Override
				public Bitmap process(Bitmap arg0) {
					return arg0;
				}
			}).cacheOnDisk(true).build();
		}

		public FoodModel getItem(int position) {
			return images == null ? null : images.get(position);
		}

		@Override
		public int getCount() {
			return images == null ? 0 : images.size();
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup container) {
			ViewHolder vh = null;
			if (convertView == null || convertView.getTag(R.drawable.app_icon + position) == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.item_viewpage_layout, null);
				vh = new ViewHolder(convertView);
				convertView.setTag(R.drawable.app_icon + position);
			} else {
				vh = (ViewHolder) convertView.getTag(R.drawable.app_icon + position);
			}
			return bindData(position, convertView, vh);
		}

		private View bindData(final int position, View convertView, final ViewHolder vh) {
			final ProgressBar bar = vh.progress;
			bar.setVisibility(View.GONE);
			ImageLoader.getInstance().displayImage(AppConfig.IMAGE_URL_HOST + images.get(position).getBig_pic(), vh.image, options, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					// bar.show();
					bar.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					// bar.hide();
					bar.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
					bar.setVisibility(View.GONE);
				}
			});
			return convertView;
		}

		static class ViewHolder {
			private PhotoView image;
			private ProgressBar progress;

			ViewHolder(View view) {
				image = (PhotoView) view.findViewById(R.id.photoview);
				progress = (ProgressBar) view.findViewById(R.id.progress);
			}
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onPageSelected(int arg0) {
		index = arg0;
		tv_viewpage_num.setText((index + 1) + "/" + mImageUrls.size());
	}
}





//package com.wbteam.onesearch.app.module.shop;
//
//import java.util.TreeMap;
//
//import android.content.Intent;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.alibaba.fastjson.JSON;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.wbteam.app.base.BaseActivity;
//import com.wbteam.ioc.annotation.ContentView;
//import com.wbteam.ioc.annotation.ViewInject;
//import com.wbteam.onesearch.R;
//import com.wbteam.onesearch.app.AppConfig;
//import com.wbteam.onesearch.app.AppContext;
//import com.wbteam.onesearch.app.http.FoodClientApi;
//import com.wbteam.onesearch.app.http.JsonResponseCallback;
//import com.wbteam.onesearch.app.model.BizResult;
//import com.wbteam.onesearch.app.model.FoodModel;
//import com.wbteam.onesearch.app.model.UserInfo;
//import com.wbteam.onesearch.app.module.webview.BannerWebView;
//import com.wbteam.onesearch.app.utils.Logger;
//import com.wbteam.onesearch.app.utils.NetUtils;
//import com.wbteam.onesearch.app.utils.StringUtils;
//import com.wbteam.onesearch.app.utils.ToastUtils;
//import com.wbteam.onesearch.app.weight.HeaderLayout;
//import com.wbteam.onesearch.app.weight.HeaderLayout.OnLeftClickListener;
//import com.wbteam.onesearch.app.weight.HeaderLayout.OnRightClickListener;
//
///**
// *  餐牌的详情
// * 
// * @autor:码农哥
// * @version:1.0
// * @created:2016-9-23  上午12:45:13
// * @contact:QQ-441293364 TEL-15105695563
// **/
//@ContentView(R.layout.activity_food_detail)
//public class FoodDetailActivity extends BaseActivity{
//		
//	private UserInfo mUserInfo = null;
//	private String id = null;
//	
//	@ViewInject(R.id.header_title)
//	private HeaderLayout headerLayout;
//	
//	@ViewInject(R.id.iv_food_logo)
//	private ImageView iv_food_logo;
//	
//	@ViewInject(R.id.tv_food_name)
//	private TextView tv_food_name;
//	
//	@ViewInject(R.id.tv_food_price)
//	private TextView tv_food_price;
//	
//	@ViewInject(R.id.tv_food_desc)
//	private TextView tv_food_desc;
//	
////	private Imageloader mImageloader = null;
//	
//	@Override
//	public void initIntent() {
//		id = getIntent().getStringExtra(ShopDetailActivity.BUNDLE_KEY_ID);
//		mUserInfo = AppContext.getInstance().getUserInfo();
//		
////		mImageloader = Imageloader.getInstance(context);
//	}
//	
//	@Override
//	public void initListener() {
//		headerLayout.setOnLeftImageViewClickListener(new OnLeftClickListener() {
//			
//			@Override
//			public void onClick() {
//				finish();
//			}
//		});
//		
//		headerLayout.setOnRightImageViewClickListener(new OnRightClickListener() {
//			
//			@Override
//			public void onClick() {
//				if(StringUtils.notBlank(food_detai_url)){
//					Intent mIntent = new Intent(context, BannerWebView.class);
//					mIntent.putExtra("bannerUrl", food_detai_url);
//					startActivity(mIntent);
//				}
//			}
//		});
//	}
//
//	@Override
//	public void initData() {
//		sendRequest();
//	}
//	
//	private String food_detai_url = null;
//	
//	private void sendRequest(){
//		if(NetUtils.isOnline()){
//			TreeMap<String, String> params = new TreeMap<String, String>();
//			if (mUserInfo != null) {
//				params.put("ukey", mUserInfo.getUkey());
//			} else {
//				params.put("ukey", "");
//			}
//			params.put("id", id);// 餐厅id
//			FoodClientApi.post("Res/foods_detail", params, new JsonResponseCallback<BizResult>() {
//
//				@Override
//				public void onSuccess(int statusCode, BizResult body) {
//					Logger.e("TAG", "==餐牌详情==" + JSON.toJSONString(body));
//					FoodModel foodModel = JSON.parseObject(body.getData(), FoodModel.class);
//					setFoodDesc(foodModel);
//					food_detai_url = AppConfig.FOOD_DETAIL_URL + foodModel.getId();
//				}
//			});
//		}else{
//			ToastUtils.showToast(context, "当前无网络链接");
//		}
//	}
//	
//
//	private void setFoodDesc(FoodModel foodModel) {
//		ImageLoader.getInstance().displayImage(AppConfig.IMAGE_URL_HOST + foodModel.getLogo(), iv_food_logo);
////		mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + foodModel.getLogo(), iv_food_logo);
//		tv_food_name.setText(foodModel.getTitle());
//		tv_food_price.setText("￥"+foodModel.getPrice());
//		tv_food_desc.setText(foodModel.getDesc());
//	}
//}
