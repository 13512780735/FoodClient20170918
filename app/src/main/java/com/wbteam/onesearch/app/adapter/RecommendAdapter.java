package com.wbteam.onesearch.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.file.cache.Imageloader;
import com.wbteam.onesearch.app.model.RecommendModel;

/**
 * 菜系列表适配器
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-14 下午6:04:57
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class RecommendAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private List<RecommendModel> mDishList = null;
	private Imageloader mImageloader = null;

	public RecommendAdapter(Context context, List<RecommendModel> mDishList) {
		super();
		this.context = context;
		this.mDishList = mDishList;
		mInflater = LayoutInflater.from(context);
		mImageloader = Imageloader.getInstance(context);
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
			convertView = mInflater.inflate(R.layout.item_recommend_listview, null);
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
			ImageLoader.getInstance().displayImage(AppConfig.IMAGE_URL_HOST + recommendModel.getLogo(), mHolder.iv_recommend_bg);
//			mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + recommendModel.getLogo(), mHolder.iv_recommend_bg);
			mHolder.tv_recommend_desc.setText(recommendModel.getDesc());
			mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + recommendModel.getRlogo(), mHolder.iv_user_avatar);
			mHolder.tv_recommend_address.setText("地址:" + recommendModel.getAddress());
			
			try {
				if(Long.valueOf(recommendModel.getDistance())>1000){
					mHolder.tv_recommend_distance.setText(Long.valueOf(recommendModel.getDistance()) / 1000 + "KM");
				}else{
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

		private ImageView iv_recommend_bg;
		private TextView tv_recommend_desc;
		private ImageView iv_user_avatar;
		private TextView tv_recommend_name;
		private TextView tv_recommend_distance;
		private TextView tv_recommend_address;

		public ViewHolder(View view) {
			iv_recommend_bg = (ImageView) view.findViewById(R.id.iv_recommend_bg);
			tv_recommend_desc = (TextView) view.findViewById(R.id.tv_recommend_desc);
			iv_user_avatar = (ImageView) view.findViewById(R.id.iv_user_avatar);
			tv_recommend_name = (TextView) view.findViewById(R.id.tv_recommend_name);
			tv_recommend_distance = (TextView) view.findViewById(R.id.tv_recommend_distance);
			tv_recommend_address = (TextView) view.findViewById(R.id.tv_recommend_address);

		}

	}

}
