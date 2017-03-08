package animaladvertis.com.animaladvertis.beans;

import cn.bmob.v3.BmobObject;

/**
 * Created by 47321 on 2017/3/6 0006.
 */

public class UserAnimal extends BmobObject {
    String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAnimalName() {
        return AnimalName;
    }

    public void setAnimalName(String AnimalName) {
        this.AnimalName = AnimalName;
    }

    String AnimalName;
}
