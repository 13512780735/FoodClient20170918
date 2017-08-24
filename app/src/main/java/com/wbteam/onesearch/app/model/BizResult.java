package com.wbteam.onesearch.app.model;

import java.io.Serializable;

/**
 * @author 码农哥
 * @date 2016-8-6下午1:16:49
 * @email 441293364@qq.com
 * @TODO
 */

public class BizResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;

	private String errorMsg;

	private int code;

	private String data;

	public BizResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
