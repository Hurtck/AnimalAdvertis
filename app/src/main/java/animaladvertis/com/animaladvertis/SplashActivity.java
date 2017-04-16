package animaladvertis.com.animaladvertis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.beans.AnimalMission;
import animaladvertis.com.animaladvertis.beans.ApplicationDate;
import animaladvertis.com.animaladvertis.beans.User;

import animaladvertis.com.animaladvertis.callback.OnMissionsFind;
import animaladvertis.com.animaladvertis.util.FindObjectUtil;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import static com.baidu.location.h.j.s;


public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private User user;
    private List<Map<String, Object>> missionslistDate = new ArrayList<>();
    private List<Map<String, Object>> userslistDate = new ArrayList<>();
    private ActionBar actionbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        actionbar = getSupportActionBar();
        actionbar.hide();

        Bmob.initialize(this,"65749386b1ac27ecde1a176282d5f49b");
        user = User.getCurrentUser(User.class);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(4000);
                }catch (Exception e){

                }

                if(user!=null){//加载登陆界面
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    if(connectivityManager.getActiveNetworkInfo()!=null){
                        user.setPassword(user.getPwd());
                        user.login(new SaveListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                if (e==null) {
                                    Intent intent = new Intent(SplashActivity.this,UserActivity.class);
                                    startActivity(intent);
                                }else{
                                    Log.d("AUOIJJD",e.getErrorCode()+e.getMessage()+user.getPwd());
                                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }else{
                        Intent intent = new Intent(SplashActivity.this,UserActivity.class);
                        startActivity(intent);
                    }
                }else{//加载用户界面
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void getData() {
        /*****************获取第一个列表(missionslistDate)的数据*****************/
        FindObjectUtil find = new FindObjectUtil(user);
        find.findAnimalMission(user.getUsername(), new OnMissionsFind() {
            @Override
            public void result(List<AnimalMission> missions, int progress) {
                if (missions.size() != 0) {
                    for (AnimalMission mission : missions) {
                        Log.d("UserActivityMSG", mission.getName());
                        final Map<String, Object> map = new HashMap<>();
                        map.put("number", progress);
                        map.put("name", mission.getName());
                        map.put("src", mission.getPicFile());
                        map.put("missionname", mission.getMissonName());
                        missionslistDate.add(map);
                    }
                }
                /*************获取第三个列表的数据(users)**********************/
                BmobQuery<User> userQuery = new BmobQuery<User>();
                userQuery.addWhereExists("username").order("-rank").setLimit(10);
                userQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                userQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {
                            if (list != null) {
                                final int size = list.size();
                                for (User user : list) {
                                    Log.d("getMissionMessage", "1+" + user.getUsername());
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("rankName", user.getUsername());
                                    map.put("rankScore", user.getRank());
                                    map.put("rankPhoto", user.getUserPhoto());
                                    map.put("progress", user.getLevel());
                                    userslistDate.add(map);
                                }

                            } else Log.d("getMissionMessage", "1/");
                        } else {
                            Log.d("getMissionMessage", "1*" + e.getMessage() + e.getErrorCode());
                        }

                    }
                });
            }
        });
    }
}
