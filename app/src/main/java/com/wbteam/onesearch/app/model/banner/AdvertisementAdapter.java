package com.wbteam.onesearch.app.model.banner;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.file.cache.Imageloader;
import com.wbteam.onesearch.app.module.shop.ShopDetailActivity;
import com.wbteam.onesearch.app.module.webview.BannerWebView;
import com.wbteam.onesearch.app.utils.StringUtils;
import com.wbteam.onesearch.app.utils.ToastUtils;

/**
 * 广告轮播adapter
 * 
 * @author dong
 * @data 2015年3月8日下午3:46:35
 * @contance dong854163@163.com
 */
public class AdvertisementAdapter extends PagerAdapter {
	private Context context;
	private List<View> views;
	BizResultOfBanner advertiseArray;

	private Imageloader mImageloader;

	public AdvertisementAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdvertisementAdapter(Context context, List<View> views, BizResultOfBanner advertiseArray) {
		this.context = context;
		this.views = views;
		this.advertiseArray = advertiseArray;
		mImageloader = Imageloader.getInstance(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}

	@Override
	public Object instantiateItem(final View container, final int position) {
		((ViewPager) container).addView(views.get(position), 0);
		final int POSITION = position;
		View view = views.get(position);
		try {
			ImageView ivAdvertise = (ImageView) view.findViewById(R.id.ivAdvertise);
			mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + advertiseArray.getBannerList().get(position).getPic(), ivAdvertise);
			ivAdvertise.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//if (null != advertiseArray.getBannerList().get(position).getSlide_url() && StringUtils.notBlank(advertiseArray.getBannerList().get(position).getSlide_url())) {
						int type = advertiseArray.getBannerList().get(position).getType();
						//ToastUtils.showToast(context, type+"");
						switch (type) {
						case 1:
							Intent mIntent01 = new Intent(context, ShopDetailActivity.class);
							mIntent01.putExtra("shop_id", advertiseArray.getBannerList().get(position).getContent_id());
							context.startActivity(mIntent01);
							break;
							
						case 2:
							String url = AppConfig.Detail_Url + advertiseArray.getBannerList().get(position).getContent_id();
							Intent mIntent = new Intent(context, BannerWebView.class);
							mIntent.putExtra("bannerUrl", url);
							context.startActivity(mIntent);
							break;

						case 3:
							Intent intent = new Intent(context, BannerWebView.class);
							intent.putExtra("bannerUrl", advertiseArray.getBannerList().get(position).getSlide_url());
							context.startActivity(intent);
							break;

						default:
							break;
						}
								
								
					}
				//}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

}
