package com.wbteam.onesearch.app.model;

/**
 * 菜系实体类
 *
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-26 上午1:37:36
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class StyleModel {

    private String id;
    private String logo;
    private String title;
    private int sort;
    private boolean isChecked;

    @Override
    public String toString() {
        return "StyleModel{" +
                "id='" + id + '\'' +
                ", logo='" + logo + '\'' +
                ", title='" + title + '\'' +
                ", sort=" + sort +
                ", isChecked=" + isChecked +
                '}';
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public StyleModel() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
