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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.Merchant;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.callback.RadarListener;
import animaladvertis.com.animaladvertis.myview.RadarView;
import animaladvertis.com.animaladvertis.util.StringUtil;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.a.a.This;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    RadarView radar;
    TextView tvSearchNumber;
    Button brSearchMap;
    ImageView ivSearchBack;
    private User user;
    private ArrayList<Animal> animals = new ArrayList<>();
    private LocationClient locationClient;
    private String currentLocation;
    private String district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_search_map){
            StringBuilder sb = new StringBuilder();
            for(Animal am:animals){
                String merchantName = am.getShopName();
                sb.append(merchantName);
                sb.append("|");
            }
            Intent intent = new Intent(this,GuideActivity.class);
            intent.putExtra("adress",sb.toString());
            startActivity(intent);
        }
        if(v.getId() == R.id.iv_search_back) finish();
    }

    private void init() {
        radar = (RadarView) findViewById(R.id.radar);
        tvSearchNumber = (TextView) findViewById(R.id.tv_search_number);
        brSearchMap = (Button) findViewById(R.id.bt_search_map);
        ivSearchBack = (ImageView) findViewById(R.id.iv_search_back);

        ivSearchBack.setOnClickListener(this);
        brSearchMap.setOnClickListener(this);



        tvSearchNumber.setText("0");
        user = BmobUser.getCurrentUser(User.class);
        locationClient = new LocationClient(this);
        LocationClientOption options = new LocationClientOption();
        options.setOpenGps(true);
        options.setCoorType("bd00911");
        options.setProdName("animalAdvertis");
        options.setOpenAutoNotifyMode();
        options.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        options.setIsNeedAddress(true);
        locationClient.setLocOption(options);
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation.getCity() != null) {
                    Log.d("SeachActivitymsg", "1:" + bdLocation.getCity()
                            + "3:" + bdLocation.getDistrict()
                            + "4:" + bdLocation.getStreet()
                            + "5" + bdLocation.getAddrStr());
                    district = bdLocation.getDistrict();
                    currentLocation = bdLocation.getAddrStr();
                }
            }
        });
        radar.setSearchingListener(new RadarListener() {
            @Override
            public void onChangeListener() {
                if (currentLocation != null) {
                    getAniaml();
                }
            }
        });
        locationClient.start();
    }

    private void getAniaml() {
        if ((currentLocation = StringUtil.cutString(currentLocation, district)) != null) {
            BmobQuery<Animal> query = new BmobQuery<>();
            query.addWhereEqualTo("targetLocation", currentLocation);
            query.setLimit(20);
            query.findObjects(new FindListener<Animal>() {
                @Override
                public void done(List<Animal> list, BmobException e) {
                    if (e == null) {
                        for (Animal animal : list) {
                            radar.addPoint();
                            animals.add(animal);
                            Log.d("SeachActivitymsg", animal.getName());
                        }
                        Log.d("SeachActivitymsg", radar.getPointCount() + "");
                        tvSearchNumber.setText(radar.getPointCount() + "");
                    }
                }
            });
        }
    }
}
