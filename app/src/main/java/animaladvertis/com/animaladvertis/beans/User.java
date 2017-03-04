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
    private String type;
    private boolean isDefault;
    private BmobFile userPhoto;
    private int rank;
    private int level;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    private String pwd;

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

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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
