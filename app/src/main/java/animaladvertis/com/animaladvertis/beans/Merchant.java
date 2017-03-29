package animaladvertis.com.animaladvertis.beans;

import cn.bmob.v3.BmobObject;

/**
 * Created by 47321 on 2017/3/13 0013.
 */

public class Merchant extends BmobObject {
    private String name;
    private Boolean isIdentifild;
    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String phone;

    public Boolean getIdentifild() {
        return isIdentifild;
    }

    public void setIdentifild(Boolean identifild) {
        isIdentifild = identifild;
    }

    private double lat,lon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    private String Location;
}
