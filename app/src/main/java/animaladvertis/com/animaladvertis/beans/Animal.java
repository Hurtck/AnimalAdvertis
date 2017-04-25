package animaladvertis.com.animaladvertis.beans;


import java.io.Serializable;
import java.lang.annotation.Target;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 47321 on 2016/12/13 0013.
 */

public class Animal extends BmobObject implements Serializable{
    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    private String name;//传单名字
    private int score;//传单积分
    private String description;//传单描述
    private String kind;//传单种类
    private String sell;//传单折扣
    private BmobFile picture;//传单图片
    private BmobFile shop;//商家标题
    private BmobFile dataSrc;//描述图片

    private boolean tag=false;//收集标志

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public BmobFile getDataSrc() {
        return dataSrc;
    }

    public void setDataSrc(BmobFile dataSrc) {
        this.dataSrc = dataSrc;
    }

    private String missionName;//所属任务
    private String merchantName;//所属商家
    private String targetLocation;
    private String Locationname;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(String targetLocation) {
        this.targetLocation = targetLocation;
    }





    public String getLocationname() {
        return Locationname;
    }

    public void setLocationname(String locationname) {
        Locationname = locationname;
    }



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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
