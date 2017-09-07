package com.wbteam.onesearch.app.utils;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.model.AreaModel;
import com.wbteam.onesearch.app.model.CityModel;
import com.wbteam.onesearch.app.model.ProvinceModel;
import com.wbteam.onesearch.app.weight.MyGridView;

/**
 * 区域对话框
 *
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-26 上午3:29:10
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class AreaDialog extends Dialog {

    private Context mContext;
    private LinearLayout view_province_layout;

    private String current_city_info = "";

    private Handler mHandler;

    public AreaDialog(Context context, Handler mHandler) {
        super(context, R.style.Theme_Light_FullScreenDialogAct);
        this.mContext = context;
        this.mHandler = mHandler;
        View mainView = LayoutInflater.from(context).inflate(R.layout.dialog_area_layout, null);
        setContentView(mainView);
        initView(mainView);
        Window window = getWindow();
        window.setGravity(Gravity.TOP);
        // 设置显示动画
        window.setWindowAnimations(R.style.dialog_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = DeviceUtils.dp2px(context, 102);
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        onWindowAttributesChanged(wl);
        // 设置点击外围取消对话框
        setCanceledOnTouchOutside(true);

    }

    private String cityId = "";
    private String areaId = "";
    private String area1Id = "";

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea1Id() {
        return area1Id;
    }

    public void setArea1Id(String area1Id) {
        this.area1Id = area1Id;
    }

    private TextView current_city = null;

    public void setProvinceList(final List<ProvinceModel> provinceList) {
        if (null != provinceList && provinceList.size() > 0) {
            ProvinceModel mProvince = provinceList.get(0);
            if (null != mProvince) {
                final List<CityModel> cityModels = provinceList.get(0).getChild();
                view_province_layout.removeAllViews();
                View headerView = LayoutInflater.from(mContext).inflate(R.layout.view_current_city, null, false);
                current_city = (TextView) headerView.findViewById(R.id.tv_current_city);
                TextView tv_city_name = (TextView) headerView.findViewById(R.id.tv_city_name);
//				current_city.setText(provinceList.get(0).getTitle());
                tv_city_name.setText(provinceList.get(0).getTitle());

                tv_city_name.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        current_city_info = provinceList.get(0).getTitle();
                        setCityId(provinceList.get(0).getPid());
                        if (current_city != null)
                            current_city.setText(current_city_info);
                        dismiss();
                        mHandler.sendEmptyMessage(0);
                    }
                });
                view_province_layout.addView(headerView);

                for (int i = 0; i < cityModels.size(); i++) {
                    final int Position = i;
                    View view = LayoutInflater.from(mContext).inflate(R.layout.item_province_layout, null, false);
                    TextView tv_current_hint = (TextView) view.findViewById(R.id.tv_current_hint);
                    tv_current_hint.setVisibility(View.GONE);
                    TextView tv_current_city = (TextView) view.findViewById(R.id.tv_current_city);
                    MyGridView gridView = (MyGridView) view.findViewById(R.id.gridview);
                    tv_current_city.setText(cityModels.get(i).getTitle());
                    List<AreaModel> areaList = cityModels.get(i).getChild();
                    AreaModel areaModel = new AreaModel();
                    areaModel.setId("0");
                    areaModel.setTitle("全区");
                    areaList.add(0, areaModel);

                    final GridCityAdapter mAdapter = new GridCityAdapter(mContext, areaList);
                    gridView.setAdapter(mAdapter);
                    gridView.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (current_city_info.contains(provinceList.get(0).getTitle())) {
                                if (mAdapter.getItem(position).getId().equals("0")) {
                                    current_city_info = provinceList.get(0).getTitle() + cityModels.get(Position).getTitle();
                                    setAreaId("");
                                    setArea1Id("");
                                } else {
                                    current_city_info = provinceList.get(0).getTitle() + cityModels.get(Position).getTitle() + mAdapter.getItem(position).getTitle();
                                    setAreaId(provinceList.get(0).getPid());
                                    setArea1Id(mAdapter.getItem(position).getPid());
                                }
                            } else {
                                if (mAdapter.getItem(position).getId().equals("0")) {
                                    current_city_info = provinceList.get(0).getTitle() + cityModels.get(Position).getTitle();
                                    setAreaId("");
                                    setArea1Id("");
                                } else {
                                    current_city_info = cityModels.get(Position).getTitle() + mAdapter.getItem(position).getTitle();
                                    setAreaId(provinceList.get(0).getPid());
                                    setArea1Id(mAdapter.getItem(position).getPid());
                                }
                            }
                            if (current_city != null)
                                current_city.setText(current_city_info);

                            dismiss();
                            mHandler.sendEmptyMessage(0);
                        }
                    });
                    view_province_layout.addView(view);
                }
            }
        }
    }

    private void initView(View view) {
        view_province_layout = (LinearLayout) view.findViewById(R.id.view_province);
    }

    public String getAreaInfo() {
        return current_city_info;
    }

}
