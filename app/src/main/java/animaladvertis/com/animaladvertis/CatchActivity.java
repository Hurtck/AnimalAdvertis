package animaladvertis.com.animaladvertis;

import android.content.Context;
import android.hardware.Camera;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

import animaladvertis.com.animaladvertis.callback.SurfaceCallback;

import static android.R.attr.rotation;

public class CatchActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    private SurfaceView sf_look;
    private Camera camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_catch);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//设置高亮
        sf_look = (SurfaceView) findViewById(R.id.sf_look);
        sf_look.getHolder().addCallback(this);
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
