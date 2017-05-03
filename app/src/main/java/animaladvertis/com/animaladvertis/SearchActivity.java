package animaladvertis.com.animaladvertis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.callback.OnAnimalFind;
import animaladvertis.com.animaladvertis.callback.RadarListener;
import animaladvertis.com.animaladvertis.myview.RadarView;
import animaladvertis.com.animaladvertis.util.FindObjectUtil;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import animaladvertis.com.animaladvertis.util.StringUtil;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchActivity extends BaserActivity implements View.OnClickListener{

    private RadarView radar;
    private TextView tip;
    private User user;
    public static ArrayList<Animal> mAnimals = new ArrayList<>();
    private LocationClient locationClient;
    private String currentLocation;
    private String district;

    private LinearLayout animalContent;
    private int STATE_LOCATION_SUCCSE = 0;
    private int STATE_RADAR_MOVE = 0;
    private int[] idGroup = {R.id.animal0,R.id.animal1,R.id.animal2,R.id.animal3,R.id.animal4,R.id.animal5,
            R.id.animal6,R.id.animal7,R.id.animal8,R.id.animal9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationClient.stop();
    }

    @Override
    protected void onPause() {
        mAnimals.clear();
        radar.clear();
        super.onPause();
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,CatchActivity.class);
        for(int i =0;i<mAnimals.size();i++){
            if(v.getId()==idGroup[i]){
                Toast.makeText(getApplicationContext(),mAnimals.get(i).getName(),Toast.LENGTH_SHORT).show();
                intent.putExtra("animal",mAnimals.get(i));
                startActivity(intent);
                finish();
            }
        }
    }

    private void init() {
        radar = (RadarView) findViewById(R.id.radar);
        tip = (TextView) findViewById(R.id.tv_tip);
        animalContent =(LinearLayout) findViewById(R.id.ll_animalContent);

        getSupportActionBar().setTitle("搜索界面");

        user = BmobUser.getCurrentUser(User.class);
        locationClient = new LocationClient(getApplicationContext());
        LocationClientOption options = new LocationClientOption();
        options.setOpenGps(true);
        options.setCoorType("bd00911");
        options.setProdName("animalAdvertis");
        options.setScanSpan(2000);
        options.setIsNeedAddress(true);
        locationClient.setLocOption(options);
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation!= null) {
                    if(radar.isSearching()) {
                        tip.setVisibility(View.INVISIBLE);
                        district = bdLocation.getDistrict();
                        currentLocation = bdLocation.getAddrStr();
                        Toast.makeText(getApplicationContext(),currentLocation,Toast.LENGTH_SHORT).show();
                        getAniaml();
                    }
                    if(!radar.isSearching()){
                        tip.setVisibility(View.VISIBLE);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"location failed",Toast.LENGTH_LONG).show();
                }
            }
        });
        locationClient.start();

    }

    private void getAniaml() {
        if ((currentLocation = StringUtil.cutString(currentLocation, district)) != null) {
            Log.d("SeachActivitymsg", currentLocation+"");
            radar.clear();
            mAnimals.clear();
            animalContent.removeAllViews();
            new FindObjectUtil(BmobUser.getCurrentUser(User.class)).findAnimalByLocation(currentLocation, new OnAnimalFind() {
                @Override
                public void result(List<Animal> animals) {
                    if(animals!=null&&animals.size()!=0) {
                        Log.d("SeachActivitymsg", animals.size()+"");
                        int index = 0;
                        for (Animal animal:animals) {
                            mAnimals.add(animal);
                            radar.addPoint();
                            CircleImageView circleImageView = new CircleImageView(getApplicationContext());
                            circleImageView.setId(idGroup[index]);
                            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(100,100);
                            LoadImageUtil.loadIMage(getApplicationContext(),circleImageView,animal.getPicture().getFileUrl(),0);
                            circleImageView.setOnClickListener(SearchActivity.this);
                            animalContent.addView(circleImageView);
                            index++;
                        }
                    } else{
                        Toast.makeText(getApplicationContext(),"附近没有动物",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
