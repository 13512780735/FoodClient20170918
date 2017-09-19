package com.wbteam.onesearch.app.model;

import java.io.Serializable;

/**
 * 餐牌实体类
 *
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-22 下午11:44:44
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class FoodModel extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String logo;
    private String price;
    private String desc;
    private String big_pic;

    public FoodModel() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBig_pic() {
        return big_pic;
    }

    public void setBig_pic(String big_pic) {
        this.big_pic = big_pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
