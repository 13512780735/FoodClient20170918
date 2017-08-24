package com.wbteam.onesearch.app.module.shop.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.app.base.adapter.ListBaseAdapter;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.adapter.RecommendAdapter2.ViewHolder;
import com.wbteam.onesearch.app.file.cache.Imageloader;
import com.wbteam.onesearch.app.model.RecommendModel;
import com.wbteam.onesearch.app.utils.DeviceUtils;

/**
 *  @author 码农哥
 *	@date 2017-3-17上午9:13:54
 *	@email 441293364@qq.com
 *	@TODO
 *
 *  ** *** ━━━━━━神兽出没━━━━━━
 *  ** ***       ┏┓　　  ┏┓
 *  ** *** 	   ┏┛┻━━━┛┻┓
 *  ** *** 　  ┃　　　　　　　┃
 *  ** *** 　　┃　　　━　　　┃
 *  ** *** 　　┃　┳┛　┗┳　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┃　　　┻　　　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┗━┓　　　┏━┛
 *  ** *** 　　　　┃　　　┃ 神兽保佑,代码永无bug
 *  ** *** 　　　　┃　　　┃
 *  ** *** 　　　　┃　　　┗━━━┓
 *  ** *** 　　　　┃　　　　　　　┣┓
 *  ** *** 　　　　┃　　　　　　　┏┛
 *  ** *** 　　　　┗┓┓┏━┳┓┏┛
 *  ** *** 　　　　  ┃┫┫  ┃┫┫
 *  ** *** 　　　　  ┗┻┛　┗┻┛
 */

public class ShopIndex02Adapter extends ListBaseAdapter<RecommendModel>{

	private Context context;

	public ShopIndex02Adapter(Context context) {
		this.context = context;
		mImageloader = Imageloader.getInstance(context);
	}

	@Override
	protected View getRealView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder = null;
		if (convertView == null || convertView.getTag(R.drawable.ic_launcher + position) == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_restaurant_listview, null);
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
			Activity activity = (Activity) context;
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
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, DeviceUtils.dp2px(context, ViewGroup.LayoutParams.WRAP_CONTENT));
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
