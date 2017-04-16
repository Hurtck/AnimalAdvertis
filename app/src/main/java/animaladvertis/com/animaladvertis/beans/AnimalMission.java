package animaladvertis.com.animaladvertis.beans;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

import static android.R.attr.name;

/**
 * Created by 47321 on 2017/3/1 0001.
 */

public class AnimalMission extends BmobObject implements Serializable{
    private String missonName;//任务名称
    private int rank;//任务分数
    private String name;
    private boolean type;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMissonName() {
        return missonName;
    }

    public void setMissonName(String missonName) {
        this.missonName = missonName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public BmobFile getPicFile() {
        return picFile;
    }

    public void setPicFile(BmobFile picFile) {
        this.picFile = picFile;
    }

    private BmobFile picFile;//任务背景图
}
