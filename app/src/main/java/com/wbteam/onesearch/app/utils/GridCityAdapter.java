package com.wbteam.onesearch.app.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.model.AreaModel;

/**
 *  城市选择适配器
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-26  下午1:54:00
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class GridCityAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	private List<AreaModel> mAreaList = null;

	public GridCityAdapter(Context context, List<AreaModel> mAreaList) {
		super();
		this.mAreaList = mAreaList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mAreaList == null ? 0 : mAreaList.size();

	}

	@Override
	public AreaModel getItem(int position) {
		return mAreaList == null ? null : mAreaList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder = null;
		if (convertView == null || convertView.getTag(R.drawable.ic_launcher + position) == null) {
			convertView = mInflater.inflate(R.layout.item_city_gridview, null);
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
		AreaModel areaModel = mAreaList.get(position);
		if (null != areaModel) {
			mHolder.tv_city_name.setText(areaModel.getTitle());
		}
		return convertView;
	}

	public class ViewHolder {

		private TextView tv_city_name;

		public ViewHolder(View view) {
			tv_city_name = (TextView) view.findViewById(R.id.tv_city_name);
		}
	}

}
