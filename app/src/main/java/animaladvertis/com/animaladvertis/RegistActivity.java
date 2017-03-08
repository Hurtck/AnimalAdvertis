package animaladvertis.com.animaladvertis;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mingle.widget.LoadingView;

import java.io.File;

import animaladvertis.com.animaladvertis.beans.AnimalMission;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.beans.UserMission;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.baidu.location.h.j.U;
import static com.baidu.location.h.j.u;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_userName,et_pwd,et_email;
    private TextView tva,tvb,tvc;
    private Button bt_regist;
    private LoadingView loading;
    private CircleImageView img;
    private final User user = new User();
    private String TAG = "RegistACtivityMsg";
    private BmobFile userPhotoF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        Bmob.initialize(this,"65749386b1ac27ecde1a176282d5f49b ");

        et_userName = (EditText)findViewById(R.id.et_regist_userName);
        et_pwd = (EditText)findViewById(R.id.et_regist_pwd);
        et_email = (EditText)findViewById(R.id.et_regist_email);
        bt_regist = (Button) findViewById(R.id.bt_regist);
        loading = (LoadingView) findViewById(R.id.load_regist);
        tva =(TextView) findViewById(R.id.tva);
        tvb =(TextView) findViewById(R.id.tvb);
        tvc =(TextView) findViewById(R.id.tvc);
        img = (CircleImageView) findViewById(R.id.default_activity_button_image);

        bt_regist.setOnClickListener(this);
        img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_regist){
            login();
        }
        if(v.getId()==R.id.default_activity_button_image){
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
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
            Glide.with(this).load(mPath).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    img.setImageBitmap(resource);
                }
            });
            cursor.close();
            userPhotoF = new BmobFile(new File(mPath));
        }
    }


    private void login() {
        final String userName = et_userName.getText().toString().trim();
        final String pwd = et_pwd.getText().toString().trim();
        final String email = et_email.getText().toString().trim();

        if(userName==null||"".equals(userName)){
            tva.setVisibility(View.VISIBLE);
        }else if(pwd==null||"".equals(pwd)){
            tvb.setVisibility(View.VISIBLE);
        }else if(email==null||"".equals(email)){
            tvc.setVisibility(View.VISIBLE);
        }else if(userPhotoF==null){
            Toast.makeText(getApplicationContext(),"请选择用户头像",Toast.LENGTH_LONG).show();
        }else{
            tva.setVisibility(View.INVISIBLE);
            tvb.setVisibility(View.INVISIBLE);
            tvc.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.VISIBLE);
            userPhotoF.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null) loginProgress(userName, pwd, email);;
                }
            });
        }
    }

    private void loginProgress(String userName, String pwd, String email) {
        user.setDefault(true);//设置用户状态为默认
        user.setUsername(userName);//设置用户名
        user.setPassword(pwd);//设置用户密码
        user.setPwd(pwd);
        user.setUserPhoto(userPhotoF);
        user.setEmail(email);
        user.setType("normal");//设置用户类型
        user.setRank(1);
        user.setLevel(1);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(final User user, BmobException e) {
                if(user!=null){
                    Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistActivity.this,UserActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),e.getErrorCode()+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                loading.setVisibility(View.INVISIBLE);
            }
        });
    }
}
