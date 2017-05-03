package animaladvertis.com.animaladvertis;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.platform.comapi.map.D;

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
import animaladvertis.com.animaladvertis.callback.OnMissionsFind;
import animaladvertis.com.animaladvertis.util.FindObjectUtil;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.name;
import static animaladvertis.com.animaladvertis.R.drawable.mission;
import static animaladvertis.com.animaladvertis.R.id.map;


public class UserActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private Button bt_mCollect;
    private Button bt_mLook;
    private Button bt_mRank, tv_userdate;
    private Button bt_merchant;
    private NestedScrollView nestedScrollView;
    private List<View> listViews = new ArrayList<View>();
    private CircleImageView userPhoto;
    private ViewPager vp_user;
    private CircleImageView fb_catch;
    private User userDate;
    private TextView userName;
    private TextView add;
    private TextView level;
    private TextView rank;
    private TextView loading;
    private ImageView ivMore;
    private List<Map<String, Object>> missionslistDate = new ArrayList<>();
    private List<Map<String, Object>> userslistDate = new ArrayList<>();
    private List<Animal> animalListData = new ArrayList<>();
    private PopupWindow popupWindow;

    private CollectAdapter collectAdapter;
    private LookAdpter lookAdpter;
    private RankAdapter rankAdapter;
    String TAG = "UserActivityMsg";
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //Bmob.initialize(this, "65749386b1ac27ecde1a176282d5f49b ");
        bt_mCollect = (Button) findViewById(R.id.bt_collection);
        bt_mLook = (Button) findViewById(R.id.bt_catch);
        bt_mRank = (Button) findViewById(R.id.bt_rank);
        tv_userdate = (Button) findViewById(R.id.tv_userDate);
        bt_merchant = (Button) findViewById(R.id.bt_merchant);
        userPhoto = (CircleImageView) findViewById(R.id.profile_image);
        userName = (TextView) findViewById(R.id.tv_username);
        level = (TextView) findViewById(R.id.tv_level);
        rank = (TextView) findViewById(R.id.tv_rank);
        loading = (TextView) findViewById(R.id.loading);
        vp_user = (ViewPager) findViewById(R.id.vp_user);
        add = (TextView) findViewById(R.id.add) ;
        fb_catch = (CircleImageView) findViewById(R.id.fb_chatch);
        ivMore = (ImageView)findViewById(R.id.iv_more);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        bt_mCollect.setOnClickListener(this);
        bt_mLook.setOnClickListener(this);
        bt_mRank.setOnClickListener(this);
        bt_merchant.setOnClickListener(this);
        vp_user.setOnPageChangeListener(this);
        fb_catch.setOnClickListener(this);
        tv_userdate.setOnClickListener(this);
        userPhoto.setOnClickListener(this);
        add.setOnClickListener(this);
        ivMore.setOnClickListener(this);


        View view = getLayoutInflater().inflate(R.layout.layout_popup,null);
        TextView exist = (TextView) view.findViewById(R.id.exist);
        test = (TextView) view.findViewById(R.id.test);
        exist.setOnClickListener(this);
        test.setOnClickListener(this);
        popupWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        userDate = BmobUser.getCurrentUser(User.class);
        init();
        // Example of a call to a native method
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void upDate() {
        if(lookAdpter!=null&&collectAdapter!=null&&rankAdapter!=null){
            lookAdpter.notifyDataSetChanged();
            collectAdapter.notifyDataSetChanged();
            rankAdapter.notifyDataSetChanged();
        }

    }


    private void init() {
        initUserDate();//初始化用户基本数据
        getDate();//获取数据
        setDefautButton();//初始化按键状态
    }

    private void initUserDate() {
        userDate = User.getCurrentUser(User.class);

        LoadImageUtil.loadIMage(getApplicationContext(), userPhoto, userDate.getUserPhoto().getFileUrl(), 1);//加载用户头像
        userName.setText(userDate.getUsername());
        level.setText(userDate.getLevel() + "");

        rank.setText(userDate.getRank() + "");
    }

    private void intListView() {
        LayoutInflater layoutInflater = getLayoutInflater();
        setMissionsData(layoutInflater);
        setCollectDate(layoutInflater);
        setRankDate(layoutInflater);

        upDate();
        vp_user.setAdapter(new UserViewPagerAdpter(listViews));
        vp_user.setCurrentItem(1);
    }

    private void setRankDate(LayoutInflater layoutInflater) {
        //设置排名界面列表数据
        rankAdapter = new RankAdapter(getApplicationContext(),userslistDate);
        ListView listView = (ListView) (layoutInflater.inflate(R.layout.user_fragment_rank, null)).findViewById(R.id.lv_rank);
        listView.setAdapter(rankAdapter);
        listViews.add(listView);
    }

    private void setCollectDate(LayoutInflater layoutInflater) {
        //设置收藏界面列表数据
        lookAdpter = new LookAdpter(animalListData, getApplicationContext());
        final ListView listView = (ListView) (layoutInflater.inflate(R.layout.user_fragment_look, null)).findViewById(R.id.lv_look);
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.grayUserTop)));
        listView.setDividerHeight(8);
        listView.setAdapter(lookAdpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("OnItemClick","position: "+position);
                Intent intent = new Intent(UserActivity.this, CollectdetailActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                UserAnimal animal = new UserAnimal();
                animal.setAnimalName(animalListData.get(position).getName());
                animal.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            getDate();
                            listView.setAdapter(new LookAdpter(animalListData, getApplicationContext()));
                            Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return true;
            }
        });
        listViews.add(listView);
    }

    private void setMissionsData(LayoutInflater layoutInflater) {
        //设置任务界面列表数据
        collectAdapter = new CollectAdapter(getApplicationContext(), missionslistDate);
        ListView listView = (ListView) (layoutInflater.inflate(R.layout.user_fragment_collect, null)).findViewById(R.id.lv_collect);
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.grayUserTop)));
        listView.setDividerHeight(8);
        listView.setAdapter(collectAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = missionslistDate.get(position);
                int number = (int) map.get("number");
                String name = (String) map.get("name");
                String missionName = (String) map.get("missionname");
                BmobFile src = (BmobFile) map.get("src");

                Intent intent = new Intent(UserActivity.this, CollectActivity.class);
                intent.putExtra("src", src);
                intent.putExtra("number", number);
                intent.putExtra("name", name);
                intent.putExtra("missionname", missionName);
                startActivity(intent);
            }
        });
        listViews.add(listView);
    }

    public void getDate() {
        missionslistDate.clear();
        userslistDate.clear();
        animalListData.clear();
        /*****************获取第一个列表(missionslistDate)的数据*****************/
        FindObjectUtil find = new FindObjectUtil(userDate);
        find.findAnimalMission(userDate.getUsername(), new OnMissionsFind() {
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
                                /*************获取第二个列表的数据(animals)**********************/
                                BmobQuery<UserAnimal> query = new BmobQuery<>();
                                query.addWhereEqualTo("userName", BmobUser.getCurrentUser().getUsername());
                                query.setLimit(10);
                                query.findObjects(new FindListener<UserAnimal>() {
                                    @Override
                                    public void done(final List<UserAnimal> list, BmobException e) {
                                        if (e == null) {
                                            if(list.size()!=0){
                                                for (final UserAnimal userAnimal : list) {
                                                    BmobQuery<Animal> aQuery = new BmobQuery<Animal>();
                                                    aQuery.addWhereEqualTo("name", userAnimal.getAnimalName());
                                                    aQuery.findObjects(new FindListener<Animal>() {
                                                        @Override
                                                        public void done(List<Animal> alist, BmobException e) {
                                                            if (e == null) {
                                                                animalListData.add(alist.get(0));
                                                            }else{
                                                                loading.setText("加载失败");
                                                            }
                                                            if(animalListData.size()==list.size()){
                                                                intListView();
                                                            }
                                                        }
                                                    });
                                                }
                                            }else{
                                                Animal animal = new Animal();
                                                animal.setName("null");
                                                animalListData.add(animal);
                                                intListView();
                                            }

                                        } else {
                                            loading.setText("加载失败");
                                        }
                                    }
                                });

                            } else {
                                Log.d("getMissionMessage", "1*" + e.getMessage() + e.getErrorCode());
                            }

                        }
                    });
                } else {
                    loading.setText("加载失败");
                }
            }
        });

    }


    private void setDefautButton() {
        bt_mCollect.setBackgroundResource(mission);
        bt_mLook.setBackgroundResource(R.drawable.collec);
        bt_mRank.setBackgroundResource(R.drawable.rank);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //if(popupWindow!=null&&popupWindow.isShowing()) popupWindow.dismiss();
        return super.onTouchEvent(event);
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
        if (v.getId() == R.id.bt_merchant) {
            startActivity(new Intent(UserActivity.this,UserSelectMissionActivity.class));
        }
        if(v.getId() == R.id.add){
            if (userDate.getType().equals("merchant"))
                startActivity(new Intent(UserActivity.this, MerchantActivity.class));
            if (userDate.getType().equals("normal"))
                startActivity(new Intent(UserActivity.this, MerchantRegistActivity.class));
        }
        if(v.getId()==R.id.iv_more){
            popupWindow.showAsDropDown(ivMore);
        }
        if(v.getId()==R.id.exist){
            BmobUser.logOut();
            startActivity(new Intent(UserActivity.this,LoginActivity.class));
            finish();
        }
        if(v.getId()==R.id.test){
            startActivity(new Intent(UserActivity.this,EditAdverticeActivity.class));
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

            final BmobFile userPhotoF = new BmobFile(new File(mPath));
            userPhotoF.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e != null)
                        Toast.makeText(getApplicationContext(), "数据修改失败" + e.getMessage() + " " + e.getErrorCode(), Toast.LENGTH_LONG).show();
                    else {
                        userDate.setUserPhoto(userPhotoF);
                        userDate.update(userDate.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "数据修改成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, e.getErrorCode() + e.getMessage());
                                }
                            }
                        });
                        LoadImageUtil.loadIMage(getApplicationContext(), userPhoto, userDate.getUserPhoto().getFileUrl(), 1);
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
