package animaladvertis.com.animaladvertis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.callback.OnAnimalFind;
import animaladvertis.com.animaladvertis.callback.RadarListener;
import animaladvertis.com.animaladvertis.myview.RadarView;
import animaladvertis.com.animaladvertis.util.FindObjectUtil;
import animaladvertis.com.animaladvertis.util.StringUtil;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SearchActivity extends BaserActivity implements View.OnClickListener{

    private RadarView radar;
    private TextView tvSearchNumber,tip;
    private Button brSearchMap;
    private User user;
    public static ArrayList<Animal> mAnimals = new ArrayList<>();
    private LocationClient locationClient;
    private String currentLocation;
    private String district;


    private int STATE_LOCATION_SUCCSE = 0;
    private int STATE_RADAR_MOVE = 0;

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
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        radar.clear();
        mAnimals.clear();
        tvSearchNumber.setText(mAnimals.size()+"");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_search_map){
            if(mAnimals.size()==0){
                Toast.makeText(getApplicationContext(),"你还没有找到动物",Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(this,CatchActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }

    private void init() {
        radar = (RadarView) findViewById(R.id.radar);
        tvSearchNumber = (TextView) findViewById(R.id.tv_search_number);
        brSearchMap = (Button) findViewById(R.id.bt_search_map);
        tip = (TextView) findViewById(R.id.tv_tip);
        brSearchMap.setOnClickListener(this);
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
            radar.clear();
            mAnimals.clear();
            new FindObjectUtil(BmobUser.getCurrentUser(User.class)).findAnimalByLocation(currentLocation, new OnAnimalFind() {
                @Override
                public void result(List<Animal> animals) {
                    if(animals!=null) {
                        tvSearchNumber.setText(animals.size()+"");
                        Log.d("SeachActivitymsg", animals.size()+"");
                        for (Animal animal:animals) {
                            mAnimals.add(animal);
                            radar.addPoint();
                            Toast.makeText(getApplicationContext(),""+animal.getName(),Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Log.d("SeachActivitymsg", "no data found");
                    }
                }
            });
        }
    }
}
