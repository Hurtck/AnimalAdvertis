package animaladvertis.com.animaladvertis.beans;

import cn.bmob.v3.BmobObject;

/**
 * Created by 47321 on 2017/3/13 0013.
 */

public class merchantAnimal extends BmobObject{
    private String merchantName;

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(String targetLocation) {
        this.targetLocation = targetLocation;
    }

    private String animalName;
    private String targetLocation;
}
