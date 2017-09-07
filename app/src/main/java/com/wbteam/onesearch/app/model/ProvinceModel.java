package com.wbteam.onesearch.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * 省份实体类
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-26 上午1:43:56
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class ProvinceModel implements Serializable{
	private String id;
	private String title;
	private String pid;
	private List<CityModel> child;

	public ProvinceModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<CityModel> getChild() {
		return child;
	}

	public void setChild(List<CityModel> child) {
		this.child = child;
	}

}
