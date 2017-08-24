package com.wbteam.onesearch.app.model;

import java.util.ArrayList;
import java.util.List;

import com.wbteam.app.base.interf.ListEntity;

/**
 *  
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-10-8  下午11:02:21
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class RecommendModelList extends Entity implements ListEntity<RecommendModel>{
	private static final long serialVersionUID = 1L;

	private List<RecommendModel> data = new ArrayList<RecommendModel>();
	
	@Override
	public List<RecommendModel> getList() {
		// TODO Auto-generated method stub
		return data;
	}

	public List<RecommendModel> getData() {
		return data;
	}

	public void setData(List<RecommendModel> data) {
		this.data = data;
	}
}

