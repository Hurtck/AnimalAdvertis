package animaladvertis.com.animaladvertis.beans;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

import static android.R.attr.name;

/**
 * Created by 47321 on 2017/3/1 0001.
 */

public class AnimalMission extends BmobObject{
    private String missonName;//任务名称
    private int rank;//任务分数
    private String adNameTable;//任务动物集合
    private String name;

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

    public String getAdNameTable() {
        return adNameTable;
    }

    public void setAdNameTable(String adNameTable) {
        this.adNameTable = adNameTable;
    }

    public BmobFile getPicFile() {
        return picFile;
    }

    public void setPicFile(BmobFile picFile) {
        this.picFile = picFile;
    }

    public String getEvatille() {
        return evatille;
    }

    public void setEvatille(String evatille) {
        this.evatille = evatille;
    }

    private BmobFile picFile;//任务背景图
    private String evatille;//任务评价
}
