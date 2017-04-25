package animaladvertis.com.animaladvertis.beans;

import java.io.Serializable;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 47321 on 2017/4/11 0011.
 */

public class AnimalModel implements Serializable{
    private int score;
    private int start;
    private double price;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public BmobFile getSrc() {
        return src;
    }

    public void setSrc(BmobFile src) {
        this.src = src;
    }

    private BmobFile src;

}
