package animaladvertis.com.animaladvertis.callback;

import android.content.Context;
import android.hardware.Camera;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import org.apache.http.conn.ConnectTimeoutException;

/**
 * Created by 47321 on 2016/12/15 0015.
 */

public class SurfaceCallback implements SurfaceHolder.Callback {
    private Camera camera;
    private Context mContext;
    public SurfaceCallback(Context context){
        mContext = context;
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);//得到窗口管理器
        Display display = wm.getDefaultDisplay();//得到当前屏幕
        Camera.Parameters parameters = camera.getParameters();//得到摄像头的参数
        parameters.setPreviewSize(display.getWidth(), display.getHeight());//设置预览照片的大小
        parameters.setPreviewFrameRate(3);//设置每秒3帧

    }
}
