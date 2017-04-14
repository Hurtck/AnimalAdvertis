package animaladvertis.com.animaladvertis.callback;

import java.util.List;

import animaladvertis.com.animaladvertis.beans.AnimalMission;

/**
 * Created by 47321 on 2017/4/1 0001.
 */

public interface OnMissionsFind {
    void result( List<AnimalMission> missions,int progress);
}
