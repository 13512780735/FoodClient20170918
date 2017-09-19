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
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wbteam.app.base.adapter.ListBaseAdapter;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppConfig;
import com.wbteam.onesearch.app.file.cache.Imageloader;
import com.wbteam.onesearch.app.model.FoodModel;
import com.wbteam.onesearch.app.model.RecommendModel;
import com.wbteam.onesearch.app.utils.DeviceUtils;

/**
 * 餐牌适配器
 *
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-22 下午11:44:09
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class FoodAdapter extends ListBaseAdapter<FoodModel> {

    private Context context;
    private LayoutInflater mInflater;
    private List<FoodModel> mFoodModels = null;
    private Imageloader mImageloader = null;

    public FoodAdapter(Context context, List<FoodModel> mFoodModels) {
        super();
        this.context = context;
        this.mFoodModels = mFoodModels;
        mInflater = LayoutInflater.from(context);
        mImageloader = Imageloader.getInstance(context);
    }

    @Override
    public int getCount() {
        return mFoodModels == null ? 0 : mFoodModels.size();

    }

    @Override
    public FoodModel getItem(int position) {
        return mFoodModels == null ? null : mFoodModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView == null || convertView.getTag(R.drawable.ic_launcher + position) == null) {
            convertView = mInflater.inflate(R.layout.item_food_gridview, null);
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
        FoodModel foodModel = mFoodModels.get(position);
        if (null != foodModel) {
            ImageLoader.getInstance().displayImage(AppConfig.IMAGE_URL_HOST + foodModel.getLogo(), mHolder.iv_recommend_bg);
            mHolder.tv_food_name.setText(foodModel.getTitle());
            mHolder.tv_food_price.setText("￥" + foodModel.getPrice());
        }
        return convertView;
    }

    public class ViewHolder {

        private ImageView iv_recommend_bg;
        private TextView tv_food_name;
        private TextView tv_food_price;

        public ViewHolder(View view) {
            iv_recommend_bg = (ImageView) view.findViewById(R.id.iv_recommend_bg);
            tv_food_name = (TextView) view.findViewById(R.id.tv_food_name);
            tv_food_price = (TextView) view.findViewById(R.id.tv_food_price);
        }

    }

}
