package com.wbteam.onesearch.app.model;

import java.util.List;

/**
 * 城市实体类
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-26 上午1:42:29
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class CityModel {
	private String id;
	private String title;
	private String pid;
	private List<AreaModel> child;
	private boolean nameIsChecked;

	@Override
	public String toString() {
		return "CityModel{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", pid='" + pid + '\'' +
				", child=" + child +
				", nameIsChecked=" + nameIsChecked +
				'}';
	}

	public boolean isNameIsChecked() {
		return nameIsChecked;
	}

	public void setNameIsChecked(boolean nameIsChecked) {
		this.nameIsChecked = nameIsChecked;
	}

	public CityModel() {
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

	public List<AreaModel> getChild() {
		return child;
	}

	public void setChild(List<AreaModel> child) {
		this.child = child;
	}

}
