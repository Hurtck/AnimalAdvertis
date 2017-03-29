package animaladvertis.com.animaladvertis;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.adapter.CollectAdapter;
import animaladvertis.com.animaladvertis.adapter.LookAdpter;
import animaladvertis.com.animaladvertis.adapter.RankAdapter;
import animaladvertis.com.animaladvertis.adapter.UserViewPagerAdpter;
import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.AnimalMission;
import animaladvertis.com.animaladvertis.beans.ApplicationDate;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.beans.UserAnimal;
import animaladvertis.com.animaladvertis.beans.UserMission;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private Button bt_mCollect;
    private Button bt_mLook;
    private Button bt_mRank, tv_userdate;
    private NestedScrollView nestedScrollView;
    private AppBarLayout appBarLayout;
    private List<View> listViews = new ArrayList<View>();
    private CircleImageView userPhoto;
    List<Map<String, Object>> list;
    private ViewPager vp_user;
    private CircleImageView fb_catch;
    private User userDate;
    private TextView userName;
    private TextView locattion;
    private TextView level;
    private TextView rank;
    private List<Animal> animals;
    private List<User> users;
    private TextView merchantRigest;
    private List<Map<String,Object>> missionslistDate = new ArrayList<>();
    private List<Map<String,Object>> userslistDate = new ArrayList<>();
    String TAG = "UserActivityMsg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Bmob.initialize(this, "65749386b1ac27ecde1a176282d5f49b ");
        bt_mCollect = (Button) findViewById(R.id.bt_collection);
        bt_mLook = (Button) findViewById(R.id.bt_catch);
        bt_mRank = (Button) findViewById(R.id.bt_rank);
        tv_userdate = (Button) findViewById(R.id.tv_userDate);
        userPhoto = (CircleImageView) findViewById(R.id.profile_image);
        userName = (TextView) findViewById(R.id.tv_username);
        level = (TextView) findViewById(R.id.tv_level);
        rank = (TextView) findViewById(R.id.tv_rank);
        vp_user = (ViewPager) findViewById(R.id.vp_user);
        fb_catch = (CircleImageView) findViewById(R.id.fb_chatch);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        merchantRigest = (TextView) findViewById(R.id.tv_registToMerchant);

        bt_mCollect.setOnClickListener(this);
        bt_mLook.setOnClickListener(this);
        bt_mRank.setOnClickListener(this);
        vp_user.setOnPageChangeListener(this);
        fb_catch.setOnClickListener(this);
        tv_userdate.setOnClickListener(this);
        userPhoto.setOnClickListener(this);
        merchantRigest.setOnClickListener(this);

        init();
        // Example of a call to a native method
    }

    private void init() {
        initUserDate();//初始化用户基本数据
        getDate();//获取数据
        setDefautButton();//初始化按键状态
        initTestDate();//初始化测试数据
    }

    private void initUserDate() {
        userDate = User.getCurrentUser(User.class);
        ApplicationDate app = (ApplicationDate)getApplication();
        app.setUsername(userDate.getUsername());
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("username", userDate.getUsername());
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    userDate = list.get(0);
                    final String userPhotoAd;
                    if (userDate.getUserPhoto() != null) {
                        userPhotoAd = userDate.getUserPhoto().getFileUrl();
                        Log.d("user",userPhotoAd);
                        LoadImageUtil.loadIMage(getApplicationContext(),userPhoto,userPhotoAd, 1);//加载用户头像
                    }
                    userName.setText(userDate.getUsername());
                    level.setText(userDate.getLevel()+"");
                    rank.setText(userDate.getRank()+"");
                }else  Toast.makeText(getApplicationContext(), ""+e.getErrorCode()+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initTestDate() {
        UserMission usermission =  new UserMission();
        usermission.setUserName(userDate.getUsername());
        usermission.setMissionName("basemission");
        usermission.setProgress(0);
        usermission.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });

        UserAnimal userAnimal = new UserAnimal();
        userAnimal.setUserName(userDate.getUsername());
        userAnimal.setAnimalName("雷神");
        userAnimal.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });

        Animal animal1 = new Animal();
        animal1.setName("哈哈");
        animal1.setScore(10);
        animal1.setPicture(userDate.getUserPhoto());
        animal1.setShop(userDate.getUserPhoto());
        animal1.setLocationname("南昌市南昌县红谷滩");
        animal1.setShopName("串串香");
        animal1.setTargetLocation("中国江西省南昌市青山湖区");
        animal1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e!=null) Log.d("UserActivitymsg",e.getErrorCode()+e.getMessage());
            }
        });


    }

    private void intListView(int size) {
        if(missionslistDate.size()==size&&userslistDate.size()==size){
            LayoutInflater layoutInflater = getLayoutInflater();
            setMissionsData(layoutInflater);
            setCollectDate(layoutInflater);
            setRankDate(layoutInflater);
            vp_user.setAdapter(new UserViewPagerAdpter(listViews));
            vp_user.setCurrentItem(1);
        }
    }

    private void setRankDate(LayoutInflater layoutInflater) {
        //设置排名界面列表数据
        ListView listView = (ListView) (layoutInflater.inflate(R.layout.user_fragment_rank, null)).findViewById(R.id.lv_rank);
        listView.setAdapter(new RankAdapter(getApplicationContext(), userslistDate));
        listViews.add(listView);
    }

    private void setCollectDate(LayoutInflater layoutInflater) {
        //设置收藏界面列表数据
        ListView listView = (ListView) (layoutInflater.inflate(R.layout.user_fragment_look, null)).findViewById(R.id.lv_look);
        listView.setAdapter(new LookAdpter(getDate(1), getApplicationContext()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UserActivity.this, CollectdetailActivity.class);
                startActivity(intent);
            }
        });
        listViews.add(listView);
    }

    private void setMissionsData(LayoutInflater layoutInflater) {
        //设置任务界面列表数据
        ListView listView = (ListView) (layoutInflater.inflate(R.layout.user_fragment_collect, null)).findViewById(R.id.lv_collect);
        listView.setAdapter(new CollectAdapter(getApplicationContext(), missionslistDate));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UserActivity.this, CollectActivity.class);
                Map<String, Object> map = missionslistDate.get(position);
                BmobFile src = (BmobFile) map.get("src");
                int number = (int) map.get("number");
                String kind = (String) map.get("kind");
                intent.putExtra("src", src);
                intent.putExtra("number", number);
                intent.putExtra("kind", kind);
                startActivity(intent);
            }
        });
        listViews.add(listView);
    }

    public void getDate(){
        /*****************获取第一个列表(missionslistDate)的数据*****************/
        BmobQuery<UserMission> bmobQuery = new BmobQuery<UserMission>();
        bmobQuery.addWhereEqualTo("username",userDate.getUsername());
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        bmobQuery.findObjects(new FindListener<UserMission>() {
            @Override
            public void done(List<UserMission> list, BmobException e) {
                if(e==null&&list!=null){
                    final int size = list.size();
                    for (UserMission missionName : list) {
                        final Map<String ,Object> map = new HashMap<>();
                        map.put("number",missionName.getProgress());
                        Log.d("getMissionMessage",missionName.getMissionName());
                        BmobQuery<AnimalMission> amQuery = new BmobQuery<AnimalMission>();
                        amQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                        amQuery.addWhereEqualTo("missonName",missionName.getMissionName());
                        amQuery.findObjects(new FindListener<AnimalMission>() {
                            @Override
                            public void done(List<AnimalMission> list, BmobException e) {
                                if(e==null){
                                    if(list.size()!=0) {
                                        map.put("kind",list.get(0).getName());
                                        map.put("src",list.get(0).getPicFile());
                                        missionslistDate.add(map);
                                        intListView(size);
                                    }
                                    else Log.d("getMissionMessage","no date found");
                                }else{
                                    Log.d("getMissionMessage","1"+e.getMessage()+e.getErrorCode());
                                }
                            }
                        });
                    }
                }else{
                    Log.d("getMissionMessage","2"+e.getMessage()+e.getErrorCode());
                }
            }
        });
        /*************获取第二个列表的数据(animals)**********************/

        /*************获取第三个列表的数据(users)**********************/
        BmobQuery<User> userQuery = new BmobQuery<User>();
        userQuery.addWhereExists("username").order("-rank").setLimit(10);
        userQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        userQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    if(list!=null) {
                        final int size = list.size();
                        for(User user:list){
                            Log.d("getMissionMessage","1"+user.getRank());
                            Map<String ,Object> map = new HashMap<>();
                            map.put("rankName",user.getUsername());
                            map.put("rankScore",user.getRank());
                            map.put("rankPhoto",user.getUserPhoto());
                            map.put("progress",user.getLevel());
                            userslistDate.add(map);
                            intListView(size);
                        }
                    }else Log.d("getMissionMessage","1");
                }else{
                    Log.d("getMissionMessage","1"+e.getMessage()+e.getErrorCode());
                }
            }
        });
    }

    public List<Map<String, Object>> getDate(int index) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (index == 1) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", R.drawable.rabbit);
            map.put("title", "火锅兔子");
            map.put("info", "一只超级爱吃火锅的兔子，喂！你的胡萝卜掉火锅里了");
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("img", R.drawable.tiger);
            map.put("title", "老虎队长");
            map.put("info", "美国队长这个名字挺炫，我也来当一回队长吧。");
            list.add(map);

        }
        return list;
    }

    private void setDefautButton() {
        bt_mCollect.setBackgroundResource(R.drawable.mission);
        bt_mLook.setBackgroundResource(R.drawable.collec);
        bt_mRank.setBackgroundResource(R.drawable.rank);
    }

    @Override
    public void onClick(View v) {
        setDefautButton();
        bt_mLook.setBackgroundResource(R.drawable.collec);
        if (v.getId() == R.id.bt_catch) {
            bt_mLook.setBackgroundResource(R.drawable.collec_down);
            vp_user.setCurrentItem(1);
        }
        if (v.getId() == R.id.bt_collection) {
            bt_mCollect.setBackgroundResource(R.drawable.mission_down);
            vp_user.setCurrentItem(0);
        }
        if (v.getId() == R.id.bt_rank) {
            bt_mRank.setBackgroundResource(R.drawable.rank_down);
            vp_user.setCurrentItem(2);
        }
        if (v.getId() == R.id.fb_chatch) {
            //Intent intent = new Intent(UserActivity.this,CatchActivity.class);
            Intent intent = new Intent(UserActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.tv_userDate) {
            Intent intent = new Intent(UserActivity.this, UserSelectActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.profile_image) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }
        if(v.getId() == R.id.tv_registToMerchant){
            Intent intent = new Intent(UserActivity.this,MerchantRegistActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1 && data != null) {
            Uri uri = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, filePathColumns, null, null, null);
            cursor.moveToFirst();
            String mPath = cursor.getString(cursor.getColumnIndex(filePathColumns[0]));
            cursor.close();

          /* 缩放图片的逻辑（最后产生的Flie有问题）
           BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            int BIL = (int) (opts.outHeight / (float) 200);
            if (BIL < 1) BIL = 1;
            opts.inSampleSize = BIL;
            opts.inDensity = DisplayMetrics.DENSITY_LOW;
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            opts.inJustDecodeBounds = false;
            File file = null;
            try {
                Bitmap newPhoto = BitmapFactory.decodeFileDescriptor(new FileInputStream(mPath).getFD(), null, opts);
                File appDir = new File(Environment.getExternalStorageDirectory(), "userPhoto");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = "userPhoto.jpg";
                file = new File(appDir, fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                newPhoto.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Exception e) {

            }*/

            final BmobFile userPhotoF = new BmobFile(new File(mPath));
            userPhotoF.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e != null)
                        Toast.makeText(getApplicationContext(), "数据修改失败" + e.getMessage() + " " + e.getErrorCode(), Toast.LENGTH_LONG).show();
                    else {
                        userDate.setUserPhoto(userPhotoF);
                        userDate.setDefault(false);
                        userDate.update(userDate.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "数据修改成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG,e.getErrorCode()+e.getMessage());
                                }
                            }
                        });
                        LoadImageUtil.loadIMage(getApplicationContext(),userPhoto,userDate.getUserPhoto().getFileUrl(), 1);
                    }
                }
            });

        }
    }

    @Override
    public void onPageSelected(int position) {//左右切换时调用
        nestedScrollView.scrollTo(0, 0);
        setDefautButton();
        if (position == 0) {
            bt_mCollect.setBackgroundResource(R.drawable.mission_down);
        }
        if (position == 1) {
            bt_mLook.setBackgroundResource(R.drawable.collec_down);
        }
        if (position == 2) {
            bt_mRank.setBackgroundResource(R.drawable.rank_down);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
