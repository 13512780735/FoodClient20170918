package com.wbteam.onesearch.app.module.shop.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.model.RecommendModel;
import com.wbteam.onesearch.app.utils.DeviceUtils;

/**
 *  餐厅推荐和优惠的适配器
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-22  下午11:29:12
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class RestaurantItemAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private List<RecommendModel> mDishList = null;

	public RestaurantItemAdapter(Context context, List<RecommendModel> mDishList) {
		super();
		this.context = context;
		this.mDishList = mDishList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mDishList == null ? 0 : mDishList.size();

	}

	@Override
	public RecommendModel getItem(int position) {
		return mDishList == null ? null : mDishList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder = null;
		if (convertView == null || convertView.getTag(R.drawable.ic_launcher + position) == null) {
			convertView = mInflater.inflate(R.layout.item_restaurant_listview, null);
			
			mHolder = new ViewHolder(convertView);
			convertView.setTag(R.drawable.ic_launcher + position);
		} else {
			mHolder = (ViewHolder) convertView.getTag(R.drawable.ic_launcher + position);
		}

		return bindData(position, convertView, mHolder);
	}

	/**
	 * 绑定数据源
	 * 
	 * @param position
	 * @param convertView
	 * @param mHolder
	 * @return
	 */
	private View bindData(int position, View convertView, ViewHolder mHolder) {
		RecommendModel recommendModel = mDishList.get(position);
		if (null != recommendModel) {
			Activity activity = (Activity) context;
//			int height = DeviceUtils.getWidth(activity) * 533 / 800;
//			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, DeviceUtils.px2dp(context, height));
//			mHolder.view_recommend_bg.setLayoutParams(params);
			
			
			
			int screenWidth = DeviceUtils.getWidth(activity);  
			  
            ViewGroup.LayoutParams lp = mHolder.iv_recommend_bg.getLayoutParams();  
            lp.width = screenWidth;  
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;  
  
            mHolder.iv_recommend_bg.setLayoutParams(lp);  
  
            mHolder.iv_recommend_bg.setMaxWidth(screenWidth);  
            mHolder.iv_recommend_bg.setMaxHeight((int) (screenWidth * 5));// 这里其实可以根据需求而定，我这里测试为最大宽度的1.5倍  
			
			ImageLoader.getInstance().displayImage(AppConfig.IMAGE_URL_HOST + recommendModel.getLogo(), mHolder.iv_recommend_bg);
			mHolder.tv_recommend_desc.setText(recommendModel.getTitle());
		}
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, DeviceUtils.dp2px(context, 200));
		mHolder.view_recommend_bg.setLayoutParams(params);
		
		return convertView;
	}

	public class ViewHolder {
		private RelativeLayout view_recommend_bg;
		private ImageView iv_recommend_bg;
		private TextView tv_recommend_desc;

		public ViewHolder(View view) {
			view_recommend_bg = (RelativeLayout) view.findViewById(R.id.view_recommend_bg);
			iv_recommend_bg = (ImageView) view.findViewById(R.id.iv_recommend_bg);
			tv_recommend_desc = (TextView) view.findViewById(R.id.tv_recommend_desc);
		}

	}

}
