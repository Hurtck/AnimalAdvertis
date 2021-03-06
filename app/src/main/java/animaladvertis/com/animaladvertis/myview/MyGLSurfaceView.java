package animaladvertis.com.animaladvertis.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import java.util.List;

import animaladvertis.com.animaladvertis.util.GLRender;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import animaladvertis.com.animaladvertis.util.MyRenderer;
import animaladvertis.com.animaladvertis.util.SensorUtils;
import animaladvertis.com.animaladvertis.util.Sphere;

import static android.R.attr.angle;
import static android.R.attr.logo;
import static android.R.attr.offset;
import static android.R.attr.value;
import static android.content.Context.WINDOW_SERVICE;
import static com.baidu.location.h.j.S;
import static com.baidu.location.h.j.m;
import static com.baidu.location.h.j.s;
import static com.baidu.location.h.j.v;
import static vi.com.gdi.bgl.android.java.EnvDrawText.pt;

/**
 * Created by 47321 on 2017/3/28 0028.
 */

public class MyGLSurfaceView extends GLSurfaceView implements SensorEventListener {

    private MyRenderer mRenderer;
    private GLRender glRender;
    private SensorManager sensorManager;

    private static float[] mTmp = new float[16];
    float x,y;
    private long lastUpdateTime;
    private final long UPTATE_INTERVAL_TIME = 50;
    private float change[] = new float[2];
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    int time=0;
    private float offset = (float) (5*Math.tan(30));


    String TAG = "MyGlSurfaceView";
    public MyGLSurfaceView(Context context){
        super(context);
        setZOrderOnTop(true);
        setEGLConfigChooser(8,8,8,8,16,0);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensors = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,sensors,SensorManager.SENSOR_DELAY_GAME);
        WindowManager windowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs){
        super(context,attrs);
        setZOrderOnTop(true);
        setEGLConfigChooser(8,8,8,8,16,0);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensors = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this,magnetic,SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this,sensors,SensorManager.SENSOR_DELAY_GAME);
        WindowManager windowManager = (WindowManager)context.getSystemService(WINDOW_SERVICE);
    }


    public void setXHotizontal(float x){
        mRenderer.xTranslate += x;
        requestRender();
    }

    public void setYVertical(float y){
        mRenderer.yTranslate +=y;
        requestRender();
    }


    @Override
    public void setRenderer(Renderer renderer) {
        super.setRenderer(renderer);
        this.glRender = (GLRender) renderer;

    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        time++;
        long currentUpdateTime = System.currentTimeMillis();
        long timeIterval = currentUpdateTime - lastUpdateTime;
        if(timeIterval<UPTATE_INTERVAL_TIME) return;

        lastUpdateTime = currentUpdateTime;

        if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            magneticFieldValues = event.values;

        }
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            accelerometerValues = event.values;
        }

        calculateOrientation();
    }

    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticFieldValues);
        SensorManager.getOrientation(R, values);
        values[0] = (float) Math.toDegrees(values[0]);
        values[1] = (float) Math.toDegrees(values[1]);
        values[2] = (float) Math.toDegrees(values[2]);

        float baseX = Math.abs(values[1]);
        float horizontal = 5*(float) Math.tan(Math.toRadians(90-values[2]));
        float vertical = offset-5*(float) Math.tan(Math.toRadians(90-baseX));
        vertical = 15-5*(float) Math.tan(Math.toRadians(90-baseX));
        horizontal = 5*(float) Math.tan(Math.toRadians(values[2]));
        horizontal = horizontal>=20?20:horizontal;
        horizontal = horizontal<=-20?-20:horizontal;
        vertical = vertical>-20?vertical:-20;

        glRender.mvp = new float[]{horizontal,-vertical,0};
        requestRender();

        Log.i("MyGlSurfaceView", "Y轴方向角度："+baseX+" X轴方向角度："+values[2]+" y偏移量："+(5-5*(float) Math.tan(Math.toRadians(90-baseX)))+" x偏移量: "+5*(float) Math.tan(Math.toRadians(values[2])));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);

    }

}
