package com.wbteam.onesearch.app.ui;

import java.net.URISyntaxException;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.wbteam.app.base.BaseActivity;
import com.wbteam.ioc.annotation.ContentView;
import com.wbteam.ioc.annotation.ViewInject;
import com.wbteam.onesearch.R;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.model.ShopDetailModel;
import com.wbteam.onesearch.app.model.UserInfo;
import com.wbteam.onesearch.app.utils.AMapUtils;
import com.wbteam.onesearch.app.utils.DeviceUtils;
import com.wbteam.onesearch.app.utils.DialogUtils;
import com.wbteam.onesearch.app.utils.ToastUtils;
import com.wbteam.onesearch.app.weight.HeaderLayout;
import com.wbteam.onesearch.app.weight.HeaderLayout.OnLeftClickListener;

/**
 * 导航
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-25 上午1:37:19
 * @contact:QQ-441293364 TEL-15105695563
 **/
@ContentView(R.layout.activity_map_location)
public class ShopAddressActivity extends BaseActivity implements OnClickListener {
	
	@ViewInject(R.id.header_title)
	private HeaderLayout headerLayout;
	
	@ViewInject(R.id.tv_map_navigation)
	private TextView tv_map_navigation;
	
	@ViewInject(R.id.mapView)
	private MapView mMapView;
	
	public BaiduMap mBaiduMap = null;  
	
	private UserInfo mUserInfo = null;
	private ShopDetailModel detailModel = null;

	@Override
	public void initIntent() {
		try {
			mUserInfo = AppContext.getInstance().getUserInfo();
			detailModel = (ShopDetailModel) getIntent().getSerializableExtra("shop_detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initListener() {
		setView();
		
		headerLayout.setOnLeftImageViewClickListener(new OnLeftClickListener() {
			
			@Override
			public void onClick() {
				finish();
			}
		});
		
		tv_map_navigation.setOnClickListener(this);
	}
	
	private void initMyLocation() {
		mBaiduMap.setMyLocationEnabled(true);
		MyLocationData locData = new MyLocationData.Builder().accuracy(100)
		// 此处设置开发者获取到的方向信息，顺时针0-360
	    .direction(90.0f).latitude(Double.valueOf(detailModel.getLat())).longitude(Double.valueOf(detailModel.getLng())).build();

		float f = mBaiduMap.getMaxZoomLevel();// 19.0 最小比例尺
		// float m = mBaiduMap.getMinZoomLevel();//3.0 最大比例尺
		mBaiduMap.setMyLocationData(locData);
		mBaiduMap.setMyLocationEnabled(true);
		LatLng ll = new LatLng(Double.valueOf(detailModel.getLat()), Double.valueOf(detailModel.getLng()));
		// MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll,f);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, f - 2);// 设置缩放比例
		mBaiduMap.animateMapStatus(u);
	} 
	
	private void setView() {
		SDKInitializer.initialize(getApplicationContext());    
          
        mBaiduMap = mMapView.getMap();    
          
        initMyLocation();  
        
      //普通地图    
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);   
      //开启交通图     
        mBaiduMap.setTrafficEnabled(true);  
          
       // 开发者可根据自己实际的业务需求，利用标注覆盖物，在地图指定的位置上添加标注信息。具体实现方法如下：  
      //定义Maker坐标点    
      double lat = Double.valueOf(detailModel.getLat());
      double lng = Double.valueOf(detailModel.getLng());
        
      LatLng point = new LatLng(AMapUtils.bd_decrypt_lat(lat, lng), AMapUtils.bd_decrypt_lng(lat, lng));
    //构建Marker图标    
      BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_openmap_mark);    
      //构建MarkerOption，用于在地图上添加Marker    
      OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);    
      //在地图上添加Marker，并显示    
      mBaiduMap.addOverlay(option);  
//      //文字，在地图中也是一种覆盖物，开发者可利用相关的接口，快速实现在地图上书写文字的需求。实现方式如下：  
//      //定义文字所显示的坐标点    
//      LatLng llText = new LatLng(Double.valueOf(detailModel.getLat()), Double.valueOf(detailModel.getLng()));    
//      //构建文字Option对象，用于在地图上添加文字    
//      OverlayOptions textOption = new TextOptions()    
//          .bgColor(0xAAFFFF00)    
//          .fontSize(28)    
//          .fontColor(0xFFFF00FF)    
//          .text("餐厅在这")    
//          .rotate(0)    
//          .position(llText);    
//      //在地图上添加该文字对象并显示    
//      mBaiduMap.addOverlay(textOption);  
	}

	@Override
	public void initData() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_map_navigation:
			DialogUtils.chooseMapDialog(this, new OnClickListener() {

				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.ll_baidu_map:
						openBaiduMap();
						break;

					case R.id.ll_gaode_map:
						openGaodeMap();
						break;

					case R.id.ll_tengxun_map:
						openTencentMap();
						break;
					}
					DialogUtils.dismiss();
				}
			});
			break;

		default:
			break;
		}
	}
	
	/**
	 * 打开百度地图
	 */
	private void openBaiduMap() {
		Intent intent = null;
		if (DeviceUtils.isAvilible(context, "com.baidu.BaiduMap")) {// 传入指定应用包名
			try {
				StringBuffer buffer = new StringBuffer();
				buffer.append("intent://map/direction?destination=latlng:");
				buffer.append(detailModel.getLat() +",").append(detailModel.getLng()+"|");
				buffer.append("name:"+detailModel.getAddress());
				buffer.append("&mode=driving&region="+"开始的位置");
				buffer.append("&src=慧医#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
				intent = Intent.getIntent(buffer.toString());
				context.startActivity(intent); // 启动调用
			} catch (URISyntaxException e) {
				Log.e("intent", e.getMessage());
			}
		} else {// 未安装 // market为路径，id为包名 显示手机上所有的market商店
			ToastUtils.showToast(context, "您尚未安装百度地图");
			Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
			intent = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(intent);
		}
	}
	
	/**
	 * 打开高德地图
	 */
	private void openGaodeMap() {
		Intent intent = null;
		if (DeviceUtils.isAvilible(context, "com.autonavi.minimap")) {
			try {
				
				double lat = Double.valueOf(detailModel.getLat());
			    double lng = Double.valueOf(detailModel.getLng());
			    
				StringBuffer buffer = new StringBuffer("androidamap://navi?sourceApplication=慧医&poiname=");
				buffer.append(detailModel.getAddress()).append("&lat=" + AMapUtils.bd_decrypt_lat(lat, lng)).append("&lon=").append(AMapUtils.bd_decrypt_lng(lat, lng)).append("&dev=0");
				intent = Intent.getIntent(buffer.toString());
				context.startActivity(intent);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			ToastUtils.showToast(context, "您尚未安装高德地图");
			Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
			intent = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(intent);
		}
	}
	
	/**
	 * 打开腾讯地图
	 */
	private void openTencentMap() {
		Intent intent = null;
		if (DeviceUtils.isAvilible(context, "com.tencent.map")) {
			try {
				double lat = Double.valueOf(detailModel.getLat());
			    double lng = Double.valueOf(detailModel.getLng());
				
				StringBuffer stringBuffer = new StringBuffer("qqmap://map/routeplan?type=drive&from=")
				.append("").append("&fromcoord=").append(mUserInfo.getLat()).append(",").append(mUserInfo.getLng())
				.append("&to=").append(detailModel.getAddress()).append("&tocoord=").append(AMapUtils.bd_decrypt_lat(lat, lng))
				.append(",").append(AMapUtils.bd_decrypt_lng(lat, lng)).append("&policy=2")// 0：较快捷1：无高速
				.append("&referer=trydriver");
				intent = Intent.getIntent(stringBuffer.toString());
				context.startActivity(intent);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			ToastUtils.showToast(context, "您尚未安装腾讯地图");
			Uri uri = Uri.parse("market://details?id=com.tencent.map");
			intent = new Intent(Intent.ACTION_VIEW, uri);
			context.startActivity(intent);
		}
	}
	
	@Override  
    protected void onPause() {  
        mMapView.onPause();  
        super.onPause();  
    }  
  
    @Override  
    protected void onResume() {  
        mMapView.onResume();  
        super.onResume();  
    }  
  
    @Override  
    protected void onDestroy() {  
        // 退出时销毁定位  
        // 关闭定位图层  
        mBaiduMap.setMyLocationEnabled(false);  
        mMapView.onDestroy();  
        mMapView = null;  
        super.onDestroy();  
    }  

}
