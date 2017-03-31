package animaladvertis.com.animaladvertis.beans;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 47321 on 2017/3/31 0031.
 */

public class MissionAnimal extends BmobFile{
    private String missionName;
    private String animalName;

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }
}
