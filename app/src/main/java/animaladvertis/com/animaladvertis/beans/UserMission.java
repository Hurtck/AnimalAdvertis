package animaladvertis.com.animaladvertis.beans;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 47321 on 2017/3/2 0002.
 */

public class UserMission extends BmobObject{

    public UserMission(){
    }

    private String userName;

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String missionName;
}
