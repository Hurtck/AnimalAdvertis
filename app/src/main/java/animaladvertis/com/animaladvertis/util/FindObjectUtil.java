package animaladvertis.com.animaladvertis.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.AnimalMission;
import animaladvertis.com.animaladvertis.beans.ApplicationDate;
import animaladvertis.com.animaladvertis.beans.MissionAnimal;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.beans.UserAnimal;
import animaladvertis.com.animaladvertis.beans.UserMission;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 47321 on 2017/3/31 0031.
 */

public class FindObjectUtil {

    private User currentUser;
    private String TAG = "findObjectUtilmsg";

    public FindObjectUtil(User user){
        currentUser = user;
    }

    /**
     * 用于查询用户已拥有的的任务
     * @return List<AnimalMission>
     */
    public List<AnimalMission> findAnimalMission(){
        final List<AnimalMission> missions = new ArrayList<>();
        final BmobQuery<UserMission> query = new BmobQuery();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.addWhereEqualTo("userName",currentUser.getUsername());
        query.findObjects(new FindListener<UserMission>() {
            @Override
            public void done(List<UserMission> list, BmobException e) {//查询到用户和任务的关系映射表
                if(e!=null) Log.d(TAG,""+e.getMessage()+e.getErrorCode());
                else if(list.size()==0) Log.d(TAG,"no date found");
                else{
                    for(UserMission userMission: list){
                        final int size = list.size();
                        BmobQuery<AnimalMission> amQuery = new BmobQuery();
                        amQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);

                        amQuery.addWhereEqualTo("name",userMission.getMissionName());
                        amQuery.findObjects(new FindListener<AnimalMission>() {//根据查询到的任务名去查询任务
                            @Override
                            public void done(final List<AnimalMission> list, BmobException e) {
                                if(e!=null) Log.d(TAG,""+e.getMessage()+e.getErrorCode());
                                else if(list.size()==0) Log.d(TAG,"no date found+1");
                                else{
                                    for (AnimalMission mission:list) {
                                        missions.add(mission);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });

        return missions;
    }

    /**
     * 用于查询任务下的动物
     * @return List<Animal>
     */
    public List<Animal> findAniamlByMission(AnimalMission mission){

        final List<Animal> animals = new ArrayList<>();
        BmobQuery<MissionAnimal> query = new BmobQuery();
        query.addWhereEqualTo("missionName",mission.getName());
        query.findObjects(new FindListener<MissionAnimal>() {
            @Override
            public void done(List<MissionAnimal> list, BmobException e) {
                if(e==null&&list.size()!=0){
                    for (MissionAnimal missionAnimal : list) {
                        BmobQuery<Animal> aQuery = new BmobQuery<Animal>();
                        aQuery.addWhereEqualTo("name",missionAnimal.getAnimalName());
                        aQuery.findObjects(new FindListener<Animal>() {
                            @Override
                            public void done(List<Animal> list, BmobException e) {
                                if(e==null&&list.size()!=0){
                                    for(Animal animal:list){
                                        animals.add(animal);
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
        return animals;
    }

    /**
     * 根据地理位置查询数据
     * @param currentLocation
     * @return
     */
    public List<Animal> findAnimalByLocation(String currentLocation){
        final List<Animal> animals = new ArrayList<>();
        BmobQuery<Animal> query = new BmobQuery<>();
        query.addWhereEqualTo("targetLocation", currentLocation);
        query.setLimit(20);
        query.findObjects(new FindListener<Animal>() {
            @Override
            public void done(List<Animal> list, BmobException e) {
                if (e == null) {
                    int random = (int)(Math.random()*(list.size()/2));

                    for(int i=0;i<random;i++){
                        int randomA = (int)(Math.random()*(list.size()-1));
                        animals.add(list.get(randomA));
                        Log.d("findAniamlByMission",list.get(randomA).getName());
                    }
                    Log.d("findAniamlByMission", animals.size()+"");
                }
            }
        });
        return animals;
    }

}
