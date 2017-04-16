package animaladvertis.com.animaladvertis;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.beans.UserAnimal;
import animaladvertis.com.animaladvertis.myview.MyGLSurfaceView;
import animaladvertis.com.animaladvertis.util.FindObjectUtil;
import animaladvertis.com.animaladvertis.util.MyRenderer;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class CatchActivity extends BaserActivity implements SurfaceHolder.Callback{

    private SurfaceView sf_look;
    private Camera camera;
    private FloatingActionButton catchBall;
    //private MyGLSurfaceView glSurfaceView;

    private TextView tvName;
    private TextView tvLevel;
    private ArrayList<Animal> animals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catch);
        //glSurfaceView = (MyGLSurfaceView) findViewById(R.id.sf_target);
        sf_look = (SurfaceView) findViewById(R.id.sf_look);
        tvLevel = (TextView) findViewById(R.id.tv_name);
        tvName = (TextView) findViewById(R.id.tv_level);
        catchBall = (FloatingActionButton) findViewById(R.id.catch_ball);

        getSupportActionBar().hide();
        animals = SearchActivity.mAnimals;

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//设置高亮

        sf_look.getHolder().addCallback(this);
        //glSurfaceView.setRenderer(new MyRenderer());

        catchBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.catch_ball){
                    final String name = animals.get(0).getName();
                    UserAnimal userAnimal = new UserAnimal();
                    userAnimal.setUserName(BmobUser.getCurrentUser(User.class).getUsername());
                    userAnimal.setAnimalName(name);
                    userAnimal.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null) Toast.makeText(getApplicationContext(),"捕捉成功"+name,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        camera.stopPreview();
        camera.release();
    }

}
