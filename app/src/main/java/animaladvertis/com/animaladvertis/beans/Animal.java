package animaladvertis.com.animaladvertis.beans;


import java.lang.annotation.Target;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 47321 on 2016/12/13 0013.
 */

public class Animal extends BmobObject{
    private String name;//传单名字
    private int score;//传单积分
    private String lon,lat;//传单位置
    private String description;//传单描述
    private String hlon,hlat;//商家位置
    private String kind;//传单种类
    private String sell;//传单折扣
    private BmobFile picture;//传单图片
    private BmobFile shop;//商家标题

    public String getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(String targetLocation) {
        this.targetLocation = targetLocation;
    }

    private String targetLocation;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    private String shopName;//商家名称

    public String getLocationname() {
        return Locationname;
    }

    public void setLocationname(String locationname) {
        Locationname = locationname;
    }

    private String Locationname;//位置名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHlon() {
        return hlon;
    }

    public void setHlon(String hlon) {
        this.hlon = hlon;
    }

    public String getHlat() {
        return hlat;
    }

    public void setHlat(String hlat) {
        this.hlat = hlat;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public BmobFile getShop() {
        return shop;
    }

    public void setShop(BmobFile shop) {
        this.shop = shop;
    }
}
