package animaladvertis.com.animaladvertis.beans;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

import static android.R.attr.isDefault;
import static android.R.attr.level;
import static android.R.attr.type;
import static animaladvertis.com.animaladvertis.R.drawable.rank;

/**
 * Created by 47321 on 2016/12/22 0022.
 */

public class User extends BmobUser {
    /**************************用户通用属性*******************/
    private BmobFile userPhoto;
    private int rank;
    private int level;
    private String merchantID;
    private String type;
    private String pwd;

    /******************商户属性************************/
    private String merChantName;
    private Boolean isIdentifild;
    private BmobFile merChantPhoto;
    private double lon;
    private double lat;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMerChantName() {
        return merChantName;
    }

    public void setMerChantName(String merChantName) {
        this.merChantName = merChantName;
    }

    public Boolean getIdentifild() {
        return isIdentifild;
    }

    public void setIdentifild(Boolean identifild) {
        isIdentifild = identifild;
    }

    public BmobFile getMerChantPhoto() {
        return merChantPhoto;
    }

    public void setMerChantPhoto(BmobFile merChantPhoto) {
        this.merChantPhoto = merChantPhoto;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


    public BmobFile getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(BmobFile userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
