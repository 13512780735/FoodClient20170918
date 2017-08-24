package com.wbteam.onesearch.app.module.shoptype;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.file.cache.Imageloader;
import com.wbteam.onesearch.app.model.StyleInfo;
import com.wbteam.onesearch.app.module.webview.BannerWebView;
import com.wbteam.onesearch.app.utils.StringUtils;

/**
 * 广告轮播adapter
 * 
 * @author dong
 * @data 2015年3月8日下午3:46:35
 * @contance dong854163@163.com
 */
public class ShopStyleItemAdapter extends PagerAdapter {
	private Context context;
	private List<View> views;
	List<StyleInfo> styleInfos;

	private Imageloader mImageloader;

	public ShopStyleItemAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShopStyleItemAdapter(Context context, List<View> views, List<StyleInfo> styleInfos) {
		this.context = context;
		this.views = views;
		this.styleInfos = styleInfos;
		mImageloader = Imageloader.getInstance(context);
	}

	@Override
	public int getCount() {
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
			RelativeLayout view_01 = (RelativeLayout) view.findViewById(R.id.view_01);
			RelativeLayout view_02 = (RelativeLayout) view.findViewById(R.id.view_02);
			RelativeLayout view_03 = (RelativeLayout) view.findViewById(R.id.view_03);
			RelativeLayout view_04 = (RelativeLayout) view.findViewById(R.id.view_04);
			RelativeLayout view_05 = (RelativeLayout) view.findViewById(R.id.view_05);
			
			TextView tv_icon_01 = (TextView) view.findViewById(R.id.tv_icon_01);
			TextView tv_icon_02 = (TextView) view.findViewById(R.id.tv_icon_02);
			TextView tv_icon_03 = (TextView) view.findViewById(R.id.tv_icon_03);
			TextView tv_icon_04 = (TextView) view.findViewById(R.id.tv_icon_04);
			TextView tv_icon_05 = (TextView) view.findViewById(R.id.tv_icon_05);
			
			ImageView iv_icon_01 = (ImageView) view.findViewById(R.id.iv_icon_01);
			ImageView iv_icon_02 = (ImageView) view.findViewById(R.id.iv_icon_02);
			ImageView iv_icon_03 = (ImageView) view.findViewById(R.id.iv_icon_03);
			ImageView iv_icon_04 = (ImageView) view.findViewById(R.id.iv_icon_04);
			ImageView iv_icon_05 = (ImageView) view.findViewById(R.id.iv_icon_05);
			
			StyleInfo styleInfo01 = styleInfos.get(position * 5);
			if (styleInfo01 != null) {
				tv_icon_01.setText(styleInfo01.getTitle());
				mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + styleInfo01.getLogo(), iv_icon_01);
			} else {
				view_01.setVisibility(View.GONE);
			}

			StyleInfo styleInfo02 = styleInfos.get(position * 5 + 1);
			if (styleInfo02 != null) {
				tv_icon_02.setText(styleInfo02.getTitle());
				mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + styleInfo02.getLogo(), iv_icon_02);
			} else {
				view_02.setVisibility(View.GONE);
			}

			StyleInfo styleInfo03 = styleInfos.get(position * 5 + 2);
			if (styleInfo03 != null) {
				tv_icon_03.setText(styleInfo03.getTitle());
				mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + styleInfo03.getLogo(), iv_icon_03);
			} else {
				view_03.setVisibility(View.GONE);
			}

			StyleInfo styleInfo04 = styleInfos.get(position * 5 + 3);
			if (styleInfo04 != null) {
				tv_icon_04.setText(styleInfo04.getTitle());
				mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + styleInfo04.getLogo(), iv_icon_04);
			} else {
				view_04.setVisibility(View.GONE);
			}

			StyleInfo styleInfo05 = styleInfos.get(position * 5 + 4);
			if (tv_icon_05 != null) {
				tv_icon_05.setText(styleInfo05.getTitle());
				mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + styleInfo05.getLogo(), iv_icon_05);
			} else {
				view_05.setVisibility(View.GONE);
			}

			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			ImageView ivAdvertise = (ImageView) view.findViewById(R.id.ivAdvertise);
//			mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + advertiseArray.getBannerList().get(position).getPic(), ivAdvertise);
//			ivAdvertise.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					if (null != advertiseArray.getBannerList().get(position).getSlide_url() && StringUtils.notBlank(advertiseArray.getBannerList().get(position).getSlide_url())) {
//						Intent intent = new Intent(context, BannerWebView.class);
//						intent.putExtra("bannerUrl", advertiseArray.getBannerList().get(position).getSlide_url());
//						context.startActivity(intent);
//					}
//				}
//			});
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return view;
	}

}
