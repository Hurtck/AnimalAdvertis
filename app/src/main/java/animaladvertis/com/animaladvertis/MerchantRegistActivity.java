package animaladvertis.com.animaladvertis;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.io.File;

import animaladvertis.com.animaladvertis.beans.Merchant;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.bmob.v3.BmobUser.getCurrentUser;

public class MerchantRegistActivity extends AppCompatActivity {

    @BindView(R.id.bt_regist)
    Button btRegist;
    @BindView(R.id.tva)
    TextView tva;
    @BindView(R.id.et_regist_userName)
    EditText etRegistUserName;
    @BindView(R.id.tvb)
    TextView tvb;
    @BindView(R.id.et_regist_pwd)
    EditText etRegistPwd;
    @BindView(R.id.tvc)
    TextView tvc;
    @BindView(R.id.et_regist_email)
    EditText etRegistEmail;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.default_activity_button_image)
    CircleImageView defaultActivityButtonImage;
    @BindView(R.id.iv_collect_back)
    ImageView ivCollectBack;
    @BindView(R.id.collect_title)
    TextView collectTitle;
    @BindView(R.id.activity_merchant_regist)
    RelativeLayout activityMerchantRegist;
    @BindView(R.id.tv_loc)
    TextView tvLoc;

    private double lat, lon;
    private String adress;
    private String mPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_regist);
        ButterKnife.bind(this);

        User user = BmobUser.getCurrentUser(User.class);
        LoadImageUtil.loadIMage(getApplicationContext(), defaultActivityButtonImage, user.getUserPhoto().getFileUrl(), 1);
        btRegist.setClickable(false);
        btRegist.setText("请等待定位成功");
        getLocation();
    }


    private void getLocation() {
        LocationClient locationClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd00911");
        option.setProdName("animalAdvertis");
        option.setOpenAutoNotifyMode();
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if (bdLocation != null) {
                    lat = bdLocation.getLatitude();
                    lon = bdLocation.getLongitude();
                    tvLoc.setText("当前定位：" + bdLocation.getAddrStr());
                    btRegist.setText("注册");
                    btRegist.setClickable(true);
                }
            }
        });
        locationClient.start();
    }

    @OnClick({R.id.bt_regist, R.id.default_activity_button_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_regist:
                String name, phoneNum, email;
                if ((name = etRegistUserName.getText().toString().trim()) != null) {
                    tva.setVisibility(View.VISIBLE);
                } else if ((phoneNum = etRegistPwd.getText().toString().trim()) != null) {
                    tvb.setVisibility(View.VISIBLE);
                } else if ((email = etRegistEmail.getText().toString().trim()) != null) {
                    tvc.setVisibility(View.VISIBLE);
                } else {
                    BmobFile merchantFile = new BmobFile(new File(mPath));
                    Merchant merchant = new Merchant();
                    merchant.setMerChantPhoto(merchantFile);
                    merchant.setName(name);
                    merchant.setPhone(phoneNum);
                    merchant.setEmail(email);
                    merchant.setLocation(adress);
                    merchant.setLat(lat);
                    merchant.setLon(lon);
                    merchant.setIdentifild(false);
                    merchant.setObjectId(User.getCurrentUser(User.class).getObjectId());
                    getCurrentUser(User.class).setType("merchant");
                    merchant.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {

                        }
                    });

                }
                break;
            case R.id.default_activity_button_image:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                break;
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
            mPath = cursor.getString(cursor.getColumnIndex(filePathColumns[0]));
            cursor.close();
        }
    }
}
