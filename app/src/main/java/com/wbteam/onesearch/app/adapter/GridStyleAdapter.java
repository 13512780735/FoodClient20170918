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
import com.wbteam.onesearch.app.model.StyleModel;
import com.wbteam.onesearch.app.utils.StringUtils;

/**
 * 菜系网格适配器
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-26 上午2:25:09
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class GridStyleAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private List<StyleModel> mStyleModels = null;
	private Imageloader mImageloader = null;

	public GridStyleAdapter(Context context, List<StyleModel> mStyleModels) {
		super();
		this.context = context;
		this.mStyleModels = mStyleModels;
		mInflater = LayoutInflater.from(context);
		mImageloader = Imageloader.getInstance(context);
	}

	@Override
	public int getCount() {
		return mStyleModels == null ? 0 : mStyleModels.size();

	}

	@Override
	public StyleModel getItem(int position) {
		return mStyleModels == null ? null : mStyleModels.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder = null;
		if (convertView == null || convertView.getTag(R.drawable.ic_launcher + position) == null) {
			convertView = mInflater.inflate(R.layout.item_filter_style_gridview, null);
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
		StyleModel styleModel = mStyleModels.get(position);
		if (null != styleModel) {
			mHolder.tv_style_title.setText(styleModel.getTitle());
			
			if(StringUtils.notBlank(styleModel.getLogo())){
				ImageLoader.getInstance().displayImage(AppConfig.IMAGE_URL_HOST + styleModel.getLogo(), mHolder.iv_style_icon);
			}else{
				mHolder.iv_style_icon.setImageResource(R.drawable.all);
			}
			
			
//			mImageloader.DisplayImage(AppConfig.IMAGE_URL_HOST + styleModel.getLogo(), mHolder.iv_style_icon);
		}
		return convertView;
	}

	public class ViewHolder {

		private ImageView iv_style_icon;
		private TextView tv_style_title;

		public ViewHolder(View view) {
			iv_style_icon = (ImageView) view.findViewById(R.id.iv_style_icon);
			tv_style_title = (TextView) view.findViewById(R.id.tv_style_title);
		}

	}

}
