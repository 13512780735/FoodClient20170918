package com.wbteam.onesearch.app.model;


/**
 *  推荐Model
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-22  上午9:21:49
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class RecommendModel extends Entity{

	private static final long serialVersionUID = 1L;

	private String id;
	private String rtitle;
	private String logo;
	private String distance;
	private String rlogo;
	private String title;
	private String price;
	private String desc;
	private String address;
	private String lng;
	private String lat;

	public RecommendModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRtitle() {
		return rtitle;
	}

	public void setRtitle(String rtitle) {
		this.rtitle = rtitle;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getRlogo() {
		return rlogo;
	}

	public void setRlogo(String rlogo) {
		this.rlogo = rlogo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

}
