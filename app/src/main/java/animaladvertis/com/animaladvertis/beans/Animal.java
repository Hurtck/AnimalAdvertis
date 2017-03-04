package animaladvertis.com.animaladvertis.beans;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 47321 on 2016/12/13 0013.
 */

public class Animal extends BmobObject{
    private String name;//传单名字
    private String score;//传单积分
    private String cLocation;//传单位置
    private String description;//传单描述
    private String homeLocation;//商家位置
    private String kind;//传单种类
    private String sell;//传单折扣
    private String title;//传单标题
    private BmobFile picture;
    private String userName;//传单所属商家
    private int picSrc;

    public int getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(int picSrc) {
        this.picSrc = picSrc;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }


    public  Animal(){

        name = "";
        score = "";
        cLocation = "";
        description = "";
        homeLocation = "";
        kind = "";
        sell = "";
        title = "";
        picture = null;
        userName = "";
    }

    public Animal(String name,String title,String score,String kind,
                  String cLocation,String description,String homeLocation,
                  String sell,BmobFile picture,String userName){
        this.name = name;
        this.title = title;
        this.score = score;
        this.kind = kind;
        this.cLocation = cLocation;
        this.description = description;
        this.homeLocation = homeLocation;
        this.sell =sell;
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getcLocation() {
        return cLocation;
    }

    public void setcLocation(String cLocation) {
        this.cLocation = cLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }



}
