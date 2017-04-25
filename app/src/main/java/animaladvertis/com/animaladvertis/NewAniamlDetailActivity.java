package animaladvertis.com.animaladvertis;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.AnimalModel;
import animaladvertis.com.animaladvertis.beans.MissionAnimal;
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

import static com.baidu.location.h.j.c;
import static com.baidu.location.h.j.s;

public class NewAniamlDetailActivity extends BaserActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.shopName)
    EditText shopName;
    @BindView(R.id.targetLocation)
    EditText targetLocation;
    @BindView(R.id.detail)
    EditText detail;
    @BindView(R.id.type)
    EditText type;
    @BindView(R.id.importImg)
    TextView importImg;
    @BindView(R.id.pic)
    ImageView pic;
    @BindView(R.id.comfirm)
    Button comfirm;
    @BindView(R.id.activity_new_aniaml_detail)
    LinearLayout activityNewAniamlDetail;

    private AnimalModel animalModel;
    private Animal animal;
    private User currentUser;
    private String missionName;
    private BmobFile bmobFile,dataSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_aniaml_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("完善传单细节");

        missionName = getIntent().getExtras().getString("missionName");
        animalModel = (AnimalModel) getIntent().getSerializableExtra("model");
        currentUser = BmobUser.getCurrentUser(User.class);
        animal = new Animal();
        bmobFile = animalModel.getSrc();
        new LoadImageUtil().loadIMage(getApplicationContext(), imageView, animalModel.getSrc().getFileUrl(), 0);
        targetLocation.setText(currentUser.getLocation());
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
            LoadImageUtil.loadIMage(getApplicationContext(), imageView, mPath, 0);
            bmobFile = new BmobFile(new File(mPath));
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {

                }
            });
            animal.setPicture(bmobFile);
        }
        if (resultCode == RESULT_OK && requestCode == 2 && data != null) {
            Uri uri = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, filePathColumns, null, null, null);
            cursor.moveToFirst();
            String mPath = cursor.getString(cursor.getColumnIndex(filePathColumns[0]));
            cursor.close();
            LoadImageUtil.loadIMage(getApplicationContext(), pic, mPath, 0);
            dataSrc = new BmobFile(new File(mPath));
            dataSrc.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {

                }
            });
            animal.setDataSrc(dataSrc);//设置传单描述图片
        }
    }


    private void selectImg() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    private void imporeImg() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    private void publishAnimal(){

        final String tAniMalname,tTargetLocation,tDetail,tType;
        tAniMalname = shopName.getText().toString().trim();
        tTargetLocation = targetLocation.getText().toString().trim();
        tDetail = detail.getText().toString().trim();
        tType = type.getText().toString().trim();

        if(tAniMalname==null||"".equals(tAniMalname)
                ||tTargetLocation==null||"".equals(tTargetLocation)
                ||tDetail==null||"".equals(tDetail)
                ||tType==null||"".equals(tType)){
            Toast.makeText(getApplicationContext(),"请完善信息",Toast.LENGTH_SHORT).show();
        }else{
            animal.setPicture(bmobFile);//设置传单图片
            animal.setName(tAniMalname);
            animal.setShop(currentUser.getMerChantPhoto());
            animal.setLocationname(currentUser.getLocation());
            animal.setTargetLocation(tTargetLocation);
            animal.setKind(tType);
            animal.setScore(animalModel.getScore());
            animal.setMissionName(missionName);
            animal.setMerchantName(currentUser.getMerChantName());
            animal.setDescription(tDetail);
            animal.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null) {
                        MissionAnimal missionAnimal = new MissionAnimal();
                        Log.d("NewAnimalDetailMsg","missname: "+missionName+" tAnimalname: "+tAniMalname);
                        missionAnimal.setMissionName(missionName);
                        missionAnimal.setAnimalName(tAniMalname);
                        missionAnimal.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null) Toast.makeText(getApplicationContext(),"动物发布成功",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else Toast.makeText(getApplicationContext(),"发布成功失败",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @OnClick({R.id.imageView, R.id.importImg, R.id.comfirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView:selectImg();
                break;
            case R.id.importImg:imporeImg();
                break;
            case R.id.comfirm:publishAnimal();
                break;
        }
    }
}
