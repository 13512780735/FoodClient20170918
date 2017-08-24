package com.wbteam.onesearch.app.model;

import java.util.ArrayList;
import java.util.List;

import com.wbteam.app.base.interf.ListEntity;

/**
 *  @author 码农哥
 *	@date 2016-8-24上午2:13:43
 *	@email 441293364@qq.com
 *	@TODO
 *
 *  ** *** ━━━━━━神兽出没━━━━━━
 *  ** ***       ┏┓　　  ┏┓
 *  ** *** 	   ┏┛┻━━━┛┻┓
 *  ** *** 　  ┃　　　　　　　┃
 *  ** *** 　　┃　　　━　　　┃
 *  ** *** 　　┃　┳┛　┗┳　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┃　　　┻　　　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┗━┓　　　┏━┛
 *  ** *** 　　　　┃　　　┃ 神兽保佑,代码永无bug
 *  ** *** 　　　　┃　　　┃
 *  ** *** 　　　　┃　　　┗━━━┓
 *  ** *** 　　　　┃　　　　　　　┣┓
 *  ** *** 　　　　┃　　　　　　　┏┛
 *  ** *** 　　　　┗┓┓┏━┳┓┏┛
 *  ** *** 　　　　  ┃┫┫  ┃┫┫
 *  ** *** 　　　　  ┗┻┛　┗┻┛
 */

public class DishModelList extends Entity implements ListEntity<DishModel>{
	private static final long serialVersionUID = 1L;

	private List<DishModel> data = new ArrayList<DishModel>();
	
	@Override
	public List<DishModel> getList() {
		// TODO Auto-generated method stub
		return data;
	}

	public List<DishModel> getData() {
		return data;
	}

	public void setData(List<DishModel> data) {
		this.data = data;
	}
}

