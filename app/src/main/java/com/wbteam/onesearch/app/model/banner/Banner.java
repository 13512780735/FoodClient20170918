package com.wbteam.onesearch.app.model.banner;


/**
 * Created by user on 2015/10/19.
 */
public class Banner  {

	private String id;
	private String pic;
	private String content_id;
	private int type;
	private String create_time;
	private String slide_url;

	public Banner() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getContent_id() {
		return content_id;
	}

	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getSlide_url() {
		return slide_url;
	}

	public void setSlide_url(String slide_url) {
		this.slide_url = slide_url;
	}

}
