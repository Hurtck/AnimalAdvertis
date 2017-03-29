package animaladvertis.com.animaladvertis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;

import animaladvertis.com.animaladvertis.beans.AnimalOnMap;
import animaladvertis.com.animaladvertis.beans.Merchant;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class GuideActivity extends AppCompatActivity{

    private TextureMapView map;
    private PoiSearch poiSearch;
    private LatLng currentLocation;
    private LocationClient locationClient1;
    private String adress;
    private BaiduMap mBaidumap;
    private List<AnimalOnMap> animas;
    private LinearLayout ll;
    private List<Merchant> merchants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_guide);
        map = (TextureMapView)findViewById(R.id.map);
        ll = (LinearLayout) findViewById(R.id.ll_window);

        mBaidumap = map.getMap();
        map.removeViewAt(1);
        getCurrentPosition();
        mBaidumap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(),"maeker",Toast.LENGTH_SHORT).show();
                Bundle bundle = marker.getExtraInfo();
                AnimalOnMap animalOnMap = (AnimalOnMap) bundle.getSerializable("animalOnMap");
                ImageView img = (ImageView) findViewById(R.id.iv_animalinfo);
                TextView name = (TextView) findViewById(R.id.tv_animalinfo_title);
                TextView descript = (TextView) findViewById(R.id.tv_animalinfo_descrip);
                img.setImageResource(animalOnMap.getImgId());
                name.setText(animalOnMap.getName());
                descript.setText(animalOnMap.getDescription());
                ll.setVisibility(View.VISIBLE);
                return true;
            }
        });
        Intent intent = getIntent();
        String add = intent.getStringExtra("adress");
        String addDate[] = add.split("|");
        for(int i=0;i<addDate.length;i++){
            BmobQuery<Merchant> query= new BmobQuery<>();
            query.addWhereEqualTo("name",addDate[i]);
            query.findObjects(new FindListener<Merchant>() {
                @Override
                public void done(List<Merchant> list, BmobException e) {
                    if(e==null){
                        Merchant merchant = list.get(0);
                        merchants.add(merchant);
                        Log.d("guideactivity","lat: "+merchant.getLat()
                        +"lon: "+merchant.getLon());
                    }
                }
            });
        }
    }

    private void setAnimalPosition() {
        setAnimalInfo();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.animalloc);
        LatLng lat = null;
        Marker marker;
        OverlayOptions option;
        for(AnimalOnMap animal : animas){
            lat = new LatLng(animal.getLatitude(),animal.getLongitude());
            option = new MarkerOptions()
                    .position(lat)
                    .icon(bitmap)
                    .zIndex(9);
            marker = (Marker) mBaidumap.addOverlay(option);
            Bundle bundle = new Bundle();
            bundle.putSerializable("animalOnMap",animal);
            marker.setExtraInfo(bundle);
        }
    }

    private void setAnimalInfo() {
        animas = new ArrayList<AnimalOnMap>();
        for (Merchant mc:merchants) {
            Double lat = mc.getLat();
            Double lon = mc.getLon();
            animas.add(new AnimalOnMap(lat,lon,mc.getName(),R.drawable.move,"出现概率高"));
        }
    }

    private void getCurrentPosition() {
        locationClient1 = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd0911");
        option.setOpenGps(false);
        option.setScanSpan(5000);
        option.setNeedDeviceDirect(true);
        option.setIsNeedAddress(true);
        mBaidumap.setMapStatus(MapStatusUpdateFactory.zoomTo(20));
        locationClient1.setLocOption(option);
        locationClient1.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if(bdLocation==null){
                    Toast.makeText(getApplicationContext(),"定位失败",Toast.LENGTH_SHORT);
                }else{
                    double latitude = bdLocation.getLatitude();
                    double longitude = bdLocation.getLongitude();
                    Log.d("getLatitude",latitude+"|"+longitude);
                    adress = bdLocation.getAddrStr();
                    currentLocation = new LatLng(latitude,longitude);
                    setMyPosition(bdLocation);
                    setAnimalPosition();
                    /*BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.weizhi);
                    mBaidumap.setMyLocationEnabled(true);
                    MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS,true,icon);
                    mBaidumap.setMyLocationConfigeration(myLocationConfiguration);*/

                    MapStatusUpdate mapUpdate = MapStatusUpdateFactory.newLatLng(currentLocation);
                    mBaidumap.setMapStatus(mapUpdate);
                    if(locationClient1.isStarted()){
                        locationClient1.stop();
                    }
                }
            }
        });
        locationClient1.start();
    }

    private void setMyPosition(BDLocation bdLocation) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.weizhi);
        mBaidumap.setMyLocationEnabled(true);
        MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS,true,icon);
        mBaidumap.setMyLocationConfigeration(myLocationConfiguration);

        MyLocationData locDate = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                .direction(100).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
                mBaidumap.setMyLocationData(locDate);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }


}
