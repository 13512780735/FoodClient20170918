package com.wbteam.onesearch.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.file.cache.Imageloader;
import com.wbteam.onesearch.app.model.RecommendModel;

/**
 *  优惠列表适配器
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-22  下午2:47:16
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class FavorableAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater mInflater;
	private List<RecommendModel> mDishList = null;
	private Imageloader mImageloader = null;

	public FavorableAdapter(Context context, List<RecommendModel> mDishList) {
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
			convertView = mInflater.inflate(R.layout.item_favorable_listview, null);
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
			mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + recommendModel.getLogo(), mHolder.iv_recommend_bg);
			mHolder.tv_recommend_desc.setText(recommendModel.getDesc());
		}
		return convertView;
	}

	public class ViewHolder {

		private ImageView iv_recommend_bg;
		private TextView tv_recommend_desc;

		public ViewHolder(View view) {
			iv_recommend_bg = (ImageView) view.findViewById(R.id.iv_recommend_bg);
			tv_recommend_desc = (TextView) view.findViewById(R.id.tv_recommend_desc);

		}

	}
}

