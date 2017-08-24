package com.wbteam.onesearch.app.model.banner;


import java.util.List;

import com.wbteam.onesearch.app.model.BizResult;

/**
 * Created by user on 2015/10/19.
 */
public class BizResultOfBanner extends BizResult {
	
	private static final long serialVersionUID = 1L;
	private List<Banner> data;//活动图片，内容如下

    public List<Banner> getBannerList() {
        return data;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.data = bannerList;
    }

    @Override
    public String toString() {
        return "BizResultOfBanner{" +
                "bannerList=" + data +
                '}';
    }
}
