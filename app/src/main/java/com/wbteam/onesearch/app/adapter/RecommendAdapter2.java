package com.wbteam.onesearch.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.app.base.adapter.ListBaseAdapter;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.file.cache.Imageloader;
import com.wbteam.onesearch.app.model.RecommendModel;
import com.wbteam.onesearch.app.utils.DeviceUtils;

/**
 * 菜系列表适配器
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-14 下午6:04:57
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class RecommendAdapter2 extends ListBaseAdapter<RecommendModel> {

	private Context context;

	public RecommendAdapter2(Context context) {
		this.context = context;
		mImageloader = Imageloader.getInstance(context);
	}

	@Override
	protected View getRealView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder = null;
		if (convertView == null || convertView.getTag(R.drawable.ic_launcher + position) == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_recommend_listview, null);
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
		RecommendModel recommendModel = mDatas.get(position);
		if (null != recommendModel) {
//			Activity activity = (Activity) context;
//			int height = DeviceUtils.getWidth(activity) * 533 / 800;
//			LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, DeviceUtils.px2dp(context, height));
//			mHolder.layout_container.setLayoutParams(params);
			// mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + recommendModel.getLogo(), mHolder.iv_recommend_bg);
			Activity activity = (Activity) context;
			int screenWidth = DeviceUtils.getWidth(activity);  
			  
            ViewGroup.LayoutParams lp = mHolder.iv_recommend_bg.getLayoutParams();  
            lp.width = screenWidth;  
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;  
  
            mHolder.iv_recommend_bg.setLayoutParams(lp);  
  
            mHolder.iv_recommend_bg.setMaxWidth(screenWidth);  
            mHolder.iv_recommend_bg.setMaxHeight((int) (screenWidth * 5));
			
			mHolder.tv_recommend_desc.setText(recommendModel.getTitle());
			ImageLoader.getInstance().displayImage(AppConfig.IMAGE_URL_HOST + recommendModel.getLogo(), mHolder.iv_recommend_bg);
			mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + recommendModel.getRlogo(), mHolder.iv_user_avatar);
			mHolder.tv_recommend_address.setText("地址:" + recommendModel.getAddress());

			try {
				if (Long.valueOf(recommendModel.getDistance()) > 1000) {
					mHolder.tv_recommend_distance.setText(Long.valueOf(recommendModel.getDistance()) / 1000 + "KM");
				} else {
					mHolder.tv_recommend_distance.setText(recommendModel.getDistance() + "M");

				}
			} catch (Exception e) {
				mHolder.tv_recommend_distance.setText(recommendModel.getDistance() + "M");
				e.printStackTrace();
			}
			mHolder.tv_recommend_name.setText(recommendModel.getRtitle());
		}
		return convertView;
	}

	public class ViewHolder {

		private RelativeLayout layout_container;
		private ImageView iv_recommend_bg;
		private TextView tv_recommend_desc;
		private ImageView iv_user_avatar;
		private TextView tv_recommend_name;
		private TextView tv_recommend_distance;
		private TextView tv_recommend_address;

		public ViewHolder(View view) {
			layout_container = (RelativeLayout) view.findViewById(R.id.layout_container);
			iv_recommend_bg = (ImageView) view.findViewById(R.id.iv_recommend_bg);
			tv_recommend_desc = (TextView) view.findViewById(R.id.tv_recommend_desc);
			iv_user_avatar = (ImageView) view.findViewById(R.id.iv_user_avatar);
			tv_recommend_name = (TextView) view.findViewById(R.id.tv_recommend_name);
			tv_recommend_distance = (TextView) view.findViewById(R.id.tv_recommend_distance);
			tv_recommend_address = (TextView) view.findViewById(R.id.tv_recommend_address);

		}

	}

}
