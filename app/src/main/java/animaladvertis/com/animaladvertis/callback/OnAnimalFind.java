package animaladvertis.com.animaladvertis.callback;

import java.util.List;

import animaladvertis.com.animaladvertis.beans.Animal;

/**
 * Created by 47321 on 2017/4/1 0001.
 */

public interface OnAnimalFind {
    void result(List<Animal> animals);
}
