package com.wbteam.onesearch.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.app.base.adapter.ListBaseAdapter;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.adapter.DishAdapter.ViewHolder;
import com.wbteam.onesearch.app.model.DishModel;
import com.wbteam.onesearch.app.weight.TweetTextView;

/**
 *  餐厅适配器
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-10-8  下午10:18:48
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class DishAdapter2 extends ListBaseAdapter<DishModel>{
	
	private Context context;
	
	public DishAdapter2(Context context) {
		this.context = context;
	}
	
	@Override
	protected View getRealView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder = null;
		if (convertView == null || convertView.getTag(R.drawable.ic_launcher + position) == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_dish_styles, null);
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
		DishModel dishModel = mDatas.get(position);
		if (null != dishModel) {
			mHolder.tv_dish_name.setText(dishModel.getTitle());
			try {
				if (Long.valueOf(dishModel.getDistance()) > 1000) {
					mHolder.tv_dish_distance.setText(Long.valueOf(dishModel.getDistance()) / 1000 + "KM");
				} else {
					mHolder.tv_dish_distance.setText(dishModel.getDistance() + "M");
				}
			} catch (Exception e) {
				mHolder.tv_dish_distance.setText(dishModel.getDistance());
			}
			mHolder.tv_dish_address.setText("地址:"+ dishModel.getAddress());
			mHolder.tv_dish_introduce.setText(dishModel.getDesc());
			ImageLoader.getInstance().displayImage(AppConfig.IMAGE_URL_HOST + dishModel.getLogo(), mHolder.iv_dish_img);
//			mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + dishModel.getLogo(), mHolder.iv_dish_img);
		}
		return convertView;
	}

	public class ViewHolder {

		private ImageView iv_dish_img;
		private TextView tv_dish_name;
		private TextView tv_dish_distance;
		private TextView tv_dish_address;
		private TweetTextView tv_dish_introduce;

		public ViewHolder(View view) {
			iv_dish_img = (ImageView) view.findViewById(R.id.iv_dish_img);
			tv_dish_name = (TextView) view.findViewById(R.id.tv_dish_name);
			tv_dish_distance = (TextView) view.findViewById(R.id.tv_dish_distance);
			tv_dish_address = (TextView) view.findViewById(R.id.tv_dish_address);
			tv_dish_introduce = (TweetTextView) view.findViewById(R.id.tv_dish_introduce);
		}

	}

}
