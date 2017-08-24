package com.wbteam.onesearch.app.model;

import java.io.Serializable;

/**
 * 样式详情实体类
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-23 下午11:58:34
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class StyleInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String logo;
	private String title;

	public StyleInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
