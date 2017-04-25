package animaladvertis.com.animaladvertis;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import animaladvertis.com.animaladvertis.beans.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.R.attr.path;
import static com.baidu.location.h.j.u;

public class UserSelectActivity extends BaserActivity {
    @BindView(R.id.iv_select_back)
    ImageView ivSelectBack;
    @BindView(R.id.rl_photo)
    RelativeLayout rlPhoto;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.rl_detail)
    RelativeLayout rlDetail;
    @BindView(R.id.rl_favourite)
    RelativeLayout rlFavourite;

    private User mUser;
    private String mPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);
        ButterKnife.bind(this);
        Bmob.initialize(this,"65749386b1ac27ecde1a176282d5f49b ");
        mUser = new User();

    }

    @OnClick({R.id.iv_select_back, R.id.rl_photo, R.id.rl_name, R.id.rl_detail, R.id.rl_favourite})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_select_back:
                sendChange();
                finish();
                break;
            case R.id.rl_photo:

                break;
            case R.id.rl_name:

                break;
            case R.id.rl_detail:
                break;
            case R.id.rl_favourite:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK&&requestCode==1&&data!=null){

        }
    }

    private void sendChange(){
        mUser.update(BmobUser.getCurrentUser().getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(),"数据修改成功",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),e.getMessage()+"",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
