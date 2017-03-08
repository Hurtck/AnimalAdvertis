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

import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.callback.RadarListener;
import animaladvertis.com.animaladvertis.myview.RadarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.radar)
    RadarView radar;
    @BindView(R.id.tv_search_number)
    TextView tvSearchNumber;
    @BindView(R.id.br_search_map)
    Button brSearchMap;
    @BindView(R.id.iv_search_back)
    ImageView ivSearchBack;
    @BindView(R.id.activity_search)
    RelativeLayout activitySearch;

    private User user;
    private ArrayList<Animal> animals = new ArrayList<>();
    int i=0;
    private LocationClient locationClient;
    private String currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        init();
    }


    @OnClick({R.id.br_search_map, R.id.iv_search_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.br_search_map:
                Intent intent = new Intent(SearchActivity.this,GuideActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_search_back:
                finish();
                break;
        }
    }

    private void init() {
        locationClient = new LocationClient(this);
        LocationClientOption options = new LocationClientOption();
        options.setOpenGps(true);
        options.setCoorType("bd00911");
        options.setPriority(LocationClientOption.NetWorkFirst);
        options.setProdName("animalAdvertis");
        options.setScanSpan(5000);
        options.setIsNeedAddress(true);
        locationClient.setLocOption(options);
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if(bdLocation.getCity()!=null) {
                    Log.d("SeachActivitymsg","1:"+bdLocation.getCity());
                    if(locationClient.isStarted()) locationClient.stop();
                }


            }
        });
        radar.setSearchingListener(new RadarListener() {
            @Override
            public void onChangeListener() {
                user = BmobUser.getCurrentUser(User.class);
                Log.d("SeachActivitymsg","1");
                if(currentLocation!=null){
                    Log.d("SeachActivitymsg","1"+currentLocation);
                }
            }
        });
        locationClient.start();


    }
}
