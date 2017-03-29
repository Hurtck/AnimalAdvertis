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
import animaladvertis.com.animaladvertis.callback.RadarListener;
import animaladvertis.com.animaladvertis.myview.RadarView;
import animaladvertis.com.animaladvertis.util.StringUtil;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private RadarView radar;
    private TextView tvSearchNumber;
    private Button brSearchMap;
    private ImageView ivSearchBack;
    private User user;
    private ArrayList<Animal> animals = new ArrayList<>();
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
        locationClient = null;
    }

    @Override
    protected void onPause() {
        super.onPause();

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
            Intent intent = new Intent(this,CatchActivity.class);
            intent.putExtra("adress",sb.toString());
            startActivity(intent);
            finish();
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
        locationClient = new LocationClient(getApplicationContext());
        LocationClientOption options = new LocationClientOption();
        options.setOpenGps(true);
        options.setCoorType("bd00911");
        options.setProdName("animalAdvertis");
        options.setScanSpan(2000);
        //options.setOpenAutoNotifyMode();
        //options.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        options.setIsNeedAddress(true);
        locationClient.setLocOption(options);
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation!= null) {
                    if(radar.isSearching()) {
                        Log.d("SeachActivitymsg",
                                "1:" + bdLocation.getCity()
                                        + "2:" + bdLocation.getLatitude()
                                        +"2.1:"+bdLocation.getLongitude()
                                        + "3:" + bdLocation.getDistrict()
                                        + "4:" + bdLocation.getStreet()
                                        + "5:" + bdLocation.getAddrStr());
                        district = bdLocation.getDistrict();
                        currentLocation = bdLocation.getAddrStr();
                        getAniaml();
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
            Toast.makeText(getApplicationContext(),currentLocation,Toast.LENGTH_LONG).show();
            BmobQuery<Animal> query = new BmobQuery<>();
            query.addWhereEqualTo("targetLocation", currentLocation);
            query.setLimit(20);
            query.findObjects(new FindListener<Animal>() {
                @Override
                public void done(List<Animal> list, BmobException e) {
                    if (e == null) {
                        int random = (int)(Math.random()*(list.size()/2));
                        animals.clear();
                        for(int i=0;i<random;i++){
                            int randomA = (int)(Math.random()*(list.size()-1));
                            animals.add(list.get(randomA));
                            Log.d("SeachActivitymsg",list.get(randomA).getName());
                            radar.addPoint();
                        }
                        Log.d("SeachActivitymsg", animals.size()+"");
                        tvSearchNumber.setText(radar.getPointCount() + "");
                    }
                }
            });
        }
    }
}
