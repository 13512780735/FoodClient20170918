package com.wbteam.onesearch.app.model;


/**
 * 区域实体类
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-26 上午1:42:42
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class AreaModel {
	private String id;
	private String title;
	private String pid;
	private boolean isChecked;

	@Override
	public String toString() {
		return "AreaModel{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", pid='" + pid + '\'' +
				", isChecked=" + isChecked +
				'}';
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	public AreaModel() {
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

}
