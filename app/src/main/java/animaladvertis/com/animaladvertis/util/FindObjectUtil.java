package animaladvertis.com.animaladvertis.util;

import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.AnimalMission;
import animaladvertis.com.animaladvertis.beans.AnimalModel;
import animaladvertis.com.animaladvertis.beans.MissionAnimal;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.beans.UserMission;
import animaladvertis.com.animaladvertis.callback.FindRusultListener;
import animaladvertis.com.animaladvertis.callback.OnAnimalAdd;
import animaladvertis.com.animaladvertis.callback.OnAnimalFind;
import animaladvertis.com.animaladvertis.callback.OnMissionsFind;
import animaladvertis.com.animaladvertis.callback.OnMissionAdd;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 47321 on 2017/3/31 0031.
 */

public class FindObjectUtil {

    private User currentUser;
    private String TAG = "findObjectUtilmsg";

    public FindObjectUtil(User user) {
        currentUser = user;
    }

    /**
     * 用于查询用户已拥有的的任务
     *
     * @return List<AnimalMission>
     */
    public void findAnimalMission(String userName,final OnMissionsFind onMissionsFind) {
        final List<AnimalMission> missions = new ArrayList<>();
        final BmobQuery<UserMission> query = new BmobQuery();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.addWhereEqualTo("userName", userName);
        query.findObjects(new FindListener<UserMission>() {
            @Override
            public void done(List<UserMission> list, BmobException e) {//查询到用户和任务的关系映射表
                final int size = list.size();
                if (e != null) Log.d(TAG, "" + e.getMessage() + e.getErrorCode());
                else if (list.size() == 0) {Log.d(TAG, "no date found");onMissionsFind.result(missions,-1);}
                else {
                    for (UserMission userMission : list) {
                        Log.d(TAG, ""+userMission.getMissionName()+" "+userMission.getUserName()+" "+userMission.getProgress());
                        final int progress = userMission.getProgress();
                        BmobQuery<AnimalMission> amQuery = new BmobQuery();
                        //amQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                        amQuery.addWhereEqualTo("missonName", userMission.getMissionName());
                        amQuery.findObjects(new FindListener<AnimalMission>() {//根据查询到的任务名去查询任务
                            @Override
                            public void done(final List<AnimalMission> list, BmobException e) {
                                if (e != null) Log.d(TAG, "" + e.getMessage() + e.getErrorCode());
                                else if (list.size() == 0) {Log.d(TAG, "no date found+1");}
                                else {
                                    missions.add(list.get(0));
                                    if(missions.size()==size) onMissionsFind.result(missions, progress);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public void findAnimalMission(final OnMissionsFind onMissionsFind){
        BmobQuery<AnimalMission> amQuery = new BmobQuery();
        amQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        amQuery.addWhereEqualTo("type", true);
        amQuery.findObjects(new FindListener<AnimalMission>() {//根据查询到的任务名去查询任务
            @Override
            public void done(final List<AnimalMission> list, BmobException e) {
                if (e != null) Log.d(TAG, "" + e.getMessage() + e.getErrorCode());
                else {
                    onMissionsFind.result(list,0);
                }
            }
        });
    }

    /**
     * 用于查询任务下的动物
     *
     * @return List<Animal>
     */
    public void findAniamlByMission(String missionName, final OnAnimalFind onAnimalFind) {
        final List<Animal> animals = new ArrayList<>();
        BmobQuery<Animal> aQuery = new BmobQuery<Animal>();
        aQuery.addWhereEqualTo("missionName", missionName);
        aQuery.findObjects(new FindListener<Animal>() {
            @Override
            public void done(List<Animal> list, BmobException e) {
                if (e == null && list.size() != 0) {
                    for (Animal animal : list) {
                        animals.add(animal);
                    }
                    onAnimalFind.result(animals);
                }else{
                    Log.d(TAG,"findAniamlByMission: nodata found");
                    onAnimalFind.result(animals);
                }}
        });
    }

    /**
     * 查询商家已发布的动物
     * @param merchantName
     * @param onAnimalFind
     */
    public void findAnimalForMerchant(String merchantName,final OnAnimalFind onAnimalFind){
        BmobQuery<Animal> query = new BmobQuery();
        query.addWhereEqualTo("merchantName",merchantName);
        query.findObjects(new FindListener<Animal>() {
            @Override
            public void done(List<Animal> list, BmobException e) {
                if(e==null&&list.size()!=0)
                onAnimalFind.result(list);
            }
        });
    }

    /**
     * 根据地理位置查询数据
     *
     * @param currentLocation
     * @return
     */
    public void findAnimalByLocation(String currentLocation, final OnAnimalFind onAnimalFind) {
        final List<Animal> animals = new ArrayList<>();
        BmobQuery<Animal> query = new BmobQuery<>();
        query.addWhereEqualTo("targetLocation", currentLocation);
        query.setLimit(20);
        query.findObjects(new FindListener<Animal>() {
            @Override
            public void done(List<Animal> list, BmobException e) {
                if (e == null) {
                    for (Animal animal:list) {
                        animals.add(animal);
                    }
                    onAnimalFind.result(animals);
                }
            }
        });
    }

    /**
     * 根据用户名寻找动物模板
     * @param missionName
     * @param findRusultListener
     */
    public void findAnimalModelByMissoion(String missionName,final FindRusultListener<AnimalModel> findRusultListener){
        BmobQuery<AnimalModel> query = new BmobQuery();
        query.addWhereEqualTo("missionName",missionName);
        query.findObjects(new FindListener<AnimalModel>() {
            @Override
            public void done(List<AnimalModel> list, BmobException e) {
                if(e==null) findRusultListener.result(list);
                else Log.d(TAG,""+e.getErrorCode()+e.getMessage());
            }
        });
    }

    /**
     * 发布收集任务
     * @param name
     * @param picPath
     */
    public void addMission(String name,String missionName, String picPath, final OnMissionAdd onMissionAdd){
        final AnimalMission mission = new AnimalMission();
        mission.setName(name);
        mission.setMissonName(missionName);
        mission.setPicFile(new BmobFile(new File(picPath)));
        mission.setRank(0);
        mission.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    MissionAnimal animal = new MissionAnimal();
                    onMissionAdd.result(true);
                }else onMissionAdd.result(false);

            }
        });


    }

    /**
     * 根据任务名添加动物
     * @param missionName
     * @param animal
     */
    public void addAniamlByMission(final String missionName, Animal animal, final OnAnimalAdd onAnimalAdd){
        final String aName = animal.getName();
        animal.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                BmobQuery<AnimalMission> query = new BmobQuery<AnimalMission>();
                query.addWhereEqualTo("missonName",missionName);
                query.findObjects(new FindListener<AnimalMission>() {
                    @Override
                    public void done(List<AnimalMission> list, BmobException e){
                        if (e==null&&list.size()!=0){
                            MissionAnimal missionAnimal = new MissionAnimal();
                            missionAnimal.setAnimalName(aName);
                            missionAnimal.setMissionName(missionName);
                            missionAnimal.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e==null) onAnimalAdd.result("");
                                    else onAnimalAdd.result(e.getMessage());
                                }
                            });
                        }else onAnimalAdd.result("missionNameError");
                    }
                });
            }
        });
    }

    /**
     * 根据用户名添加任务
     * @param missionName
     */
    public void addMissionForUser(String missionName,final OnAnimalAdd onAnimalAdd){
        UserMission userMission = new UserMission();
        userMission.setUserName(currentUser.getUsername());
        userMission.setMissionName(missionName);
        userMission.setProgress(0);
        Log.d(TAG,""+missionName+currentUser.getUsername());
        userMission.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                String msg = "";
                if(e!=null){
                    msg = e.getMessage();
                }
                onAnimalAdd.result(msg);
            }
        });
    }


}
