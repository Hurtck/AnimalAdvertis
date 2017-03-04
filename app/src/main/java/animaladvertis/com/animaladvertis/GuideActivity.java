package animaladvertis.com.animaladvertis;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.TextureMapView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import animaladvertis.com.animaladvertis.beans.AnimalOnMap;

import static com.baidu.location.h.j.ab;


public class GuideActivity extends AppCompatActivity{

    private TextureMapView map;
    private PoiSearch poiSearch;
    private LatLng currentLocation;
    private LocationClient locationClient;
    private String adress;
    private BaiduMap mBaidumap;
    private List<AnimalOnMap> animas;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        map.setCustomMapStylePath("/mnt/shell/emulated/0/custom_config_black");
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
        mBaidumap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                ll.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

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
        Double lat = currentLocation.latitude+0.001;
        Double lon = currentLocation.longitude;
        animas.add(new AnimalOnMap(lat,lon,"thor",R.drawable.move,"出现概率高"));
        animas.add(new AnimalOnMap(lat-0.002,lon,"thor1",R.drawable.move2,"出现概率一般"));
    }

    private void getCurrentPosition() {
        locationClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd0911");
        option.setOpenGps(true);
        option.setScanSpan(5000);
        option.setNeedDeviceDirect(true);
        mBaidumap.setMapStatus(MapStatusUpdateFactory.zoomTo(20));
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new BDLocationListener() {
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
                    if(locationClient.isStarted()){
                        locationClient.stop();
                    }
                }

            }
        });
        locationClient.start();
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
