package com.wbteam.onesearch.app.model;


/**
 * 餐厅实体类
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-14 下午5:57:10
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class DishModel extends Entity {

	private static final long serialVersionUID = 1L;

	private String id;
	private String rid;
	private String logo;// 图片
	private String rlogo;// 图片
	private String distance;// 编号
	private String title;// 标题
	private String rtitle;// 标题
	private String desc;// 详情
	private String content;// 详情
	private String address;// 地址
	private double lng;
	private double lat;

	public DishModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getRlogo() {
		return rlogo;
	}

	public void setRlogo(String rlogo) {
		this.rlogo = rlogo;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRtitle() {
		return rtitle;
	}

	public void setRtitle(String rtitle) {
		this.rtitle = rtitle;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

}
