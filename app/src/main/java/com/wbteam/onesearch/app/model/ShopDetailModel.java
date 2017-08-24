package com.wbteam.onesearch.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * 餐厅详情实体类
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-23 下午11:54:20
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class ShopDetailModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String b_time;
	private String e_time;
	private String b_time2;
	private String e_time2;
	private String logo;
	private String phone;
	private String lng;
	private String lat;
	private int is_park;
	private boolean is_collect;
	private long distance;
	private String title;
	private String address;
	private List<StyleInfo> style;
	private String backimg;
	
	private String cash;

	public ShopDetailModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public String getB_time() {
		return b_time;
	}

	public void setB_time(String b_time) {
		this.b_time = b_time;
	}

	public String getE_time() {
		return e_time;
	}

	public void setE_time(String e_time) {
		this.e_time = e_time;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public int getIs_park() {
		return is_park;
	}

	public void setIs_park(int is_park) {
		this.is_park = is_park;
	}

	public boolean isIs_collect() {
		return is_collect;
	}

	public void setIs_collect(boolean is_collect) {
		this.is_collect = is_collect;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<StyleInfo> getStyle() {
		return style;
	}

	public void setStyle(List<StyleInfo> style) {
		this.style = style;
	}

	public String getBackimg() {
		return backimg;
	}

	public void setBackimg(String backimg) {
		this.backimg = backimg;
	}

	public String getB_time2() {
		return b_time2;
	}

	public void setB_time2(String b_time2) {
		this.b_time2 = b_time2;
	}

	public String getE_time2() {
		return e_time2;
	}

	public void setE_time2(String e_time2) {
		this.e_time2 = e_time2;
	}
	
}
