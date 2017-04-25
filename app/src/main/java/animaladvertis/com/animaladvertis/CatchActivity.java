package animaladvertis.com.animaladvertis;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.io.IOException;
import java.util.ArrayList;

import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.beans.UserAnimal;

import animaladvertis.com.animaladvertis.callback.OnCathListener;
import animaladvertis.com.animaladvertis.myview.MyCatchBall;
import animaladvertis.com.animaladvertis.myview.MyGLSurfaceView;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import animaladvertis.com.animaladvertis.util.MyRenderer;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import static animaladvertis.com.animaladvertis.R.id.catch_ball;
import static animaladvertis.com.animaladvertis.R.id.detail;
import static animaladvertis.com.animaladvertis.R.id.profile_image;
import static com.baidu.location.h.j.s;
import static java.lang.Integer.getInteger;


public class CatchActivity extends BaserActivity implements SurfaceHolder.Callback,View.OnClickListener{

    private SurfaceView sf_look;
    private Camera camera;
    private MyGLSurfaceView glSurfaceView;
    private MyCatchBall myCatchBall;
    private TextView tvName;
    private TextView tvLevel,animalName,animalDescripte;
    private MyRenderer myRender;
    private SurfaceHolder hodler;
    private boolean isPreview = false;
    private Animal animal;
    private RelativeLayout rlDetail;
    private ImageView animalSrc;
    private Button btCatch,detail;
    private int STATE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);

        getSupportActionBar().hide();


        glSurfaceView = (MyGLSurfaceView) findViewById(R.id.sf_target);
        sf_look = (SurfaceView) findViewById(R.id.sf_look);
        tvLevel = (TextView) findViewById(R.id.tv_name);
        tvName = (TextView) findViewById(R.id.tv_level);
        animalName = (TextView) findViewById(R.id.tv_animalName);
        animalDescripte = (TextView) findViewById(R.id.tv_animalDescripte);
        myCatchBall = (MyCatchBall) findViewById(R.id.mb_catchBall);
        rlDetail = (RelativeLayout) findViewById(R.id.rl_animal);
        animalSrc = (ImageView) findViewById(R.id.animalSrc);
        btCatch = (Button) findViewById(R.id.bt_catch);
        detail = (Button) findViewById(R.id.bt_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        animal = (Animal)bundle.get("animal");
        tvName.setText(animal.getName()+"");
        tvLevel.setText(animal.getScore()+"");

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//设置高亮

        btCatch.setOnClickListener(this);
        detail.setOnClickListener(this);
        myCatchBall.setOnCatchLisener(new OnCathListener() {
            @Override
            public void onStateChange(int state) {
                rlDetail.setVisibility(View.VISIBLE);
                glSurfaceView.setVisibility(View.INVISIBLE);

                animalDescripte.setText(animal.getDescription());
                animalName.setText(animal.getName());
                LoadImageUtil.loadIMage(getApplicationContext(),animalSrc,animal.getPicture().getFileUrl(),0);
                ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1, Animation.RELATIVE_TO_SELF,0.5f,
                        Animation.RELATIVE_TO_SELF,0.5f);
                scaleAnimation.setDuration(400);
                rlDetail.setAnimation(scaleAnimation);
                final User crrentUser = BmobUser.getCurrentUser(User.class);
                UserAnimal userAnimal = new UserAnimal();
                userAnimal.setAnimalName(animal.getName());
                userAnimal.setUserName(crrentUser.getUsername());
                userAnimal.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if(e==null) {
                            Toast.makeText(getApplicationContext(),"捕捉成功",Toast.LENGTH_SHORT).show();
                            User mUser = new User();
                            int userRank = crrentUser.getRank()+animal.getScore();
                            int userLevel = crrentUser.getLevel();
                            userLevel = (int)Math.sqrt(userRank/10);
                            mUser.setRank(userRank);
                            mUser.setLevel(userLevel);
                            mUser.update(BmobUser.getCurrentUser().getObjectId(),new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e!=null) Toast.makeText(getApplicationContext(),"用户数据更新失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else Toast.makeText(getApplicationContext(),"捕捉失败"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
            }
        });





        /**
         * 添加SurfaceView图层
         */
        hodler = sf_look.getHolder();
        sf_look.getHolder().addCallback(this);
        hodler.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        glSurfaceView.setRenderer(new MyRenderer());




    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myCatchBall.stop();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try{
            camera = Camera.open();
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);//得到窗口管理器
            Display display = wm.getDefaultDisplay();//得到当前屏幕
            Camera.Parameters parameters = camera.getParameters();//得到摄像头的参数
            parameters.setPreviewSize(display.getWidth(), display.getHeight());//设置预览照片的大小
            parameters.setPreviewFrameRate(3);//设置每秒3帧
            camera.setPreviewDisplay(sf_look.getHolder());
            camera.setDisplayOrientation(90);
            camera.startPreview();
        }catch (IOException e){
            Log.e("CatchActivity", e.toString());
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(camera!=null){
            if(isPreview){
                camera.startPreview();
            }
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bt_detail){startActivity(new Intent(CatchActivity.this,CollectdetailActivity.class));finish();}
        if(v.getId()==R.id.bt_catch){startActivity(new Intent(CatchActivity.this,SearchActivity.class));finish();}
    }
}
