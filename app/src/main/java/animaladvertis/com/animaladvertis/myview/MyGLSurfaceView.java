package animaladvertis.com.animaladvertis.myview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.List;

import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import animaladvertis.com.animaladvertis.util.MyRenderer;

/**
 * Created by 47321 on 2017/3/28 0028.
 */

public class MyGLSurfaceView extends GLSurfaceView implements SensorEventListener {

    private MyRenderer mRenderer;
    private SensorManager sensorManager;

    float x,y;
    float hotizontal=161f,vertical=-30f,z =0f;

    public MyGLSurfaceView(Context context){
        super(context);
        setZOrderOnTop(true);
        setEGLConfigChooser(8,8,8,8,16,0);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if(sensors.size()!=0){
            Sensor sensor = sensors.get(0);
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs){
        super(context,attrs);
        setZOrderOnTop(true);
        setEGLConfigChooser(8,8,8,8,16,0);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if(sensors.size()!=0){
            Sensor sensor = sensors.get(0);
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
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
        this.mRenderer = (MyRenderer) renderer;
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                x=event.getX();y=event.getY();
                break;
            case MotionEvent.ACTION_MOVE :
                if(event.getX()>x){
                    mRenderer.rotate+=5;
                }
                if(event.getX()<x){
                    mRenderer.rotate-=5;

                }
                requestRender();
                break;
            case MotionEvent.ACTION_UP :
                break;
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float dataX= event.values[SensorManager.DATA_X];
        float dataY = event.values[SensorManager.DATA_Y];
        float dataZ = event.values[SensorManager.DATA_Z];

        float xChange = dataX-hotizontal;
        float yChange = dataY-vertical;
        float zChange = dataZ-z;

       /* Log.d("MyGLSurfaceView","xChange "+xChange+"|"
                +"yChange "+yChange+"|"
                +"zChange "+zChange);*/


        if(xChange>0.5){
            Log.d("MyGLSurfaceView","向左"+xChange);
            setXHotizontal(-xChange*0.0075f);
        }
        if(xChange<-0.5){
            Log.d("MyGLSurfaceView","向右"+xChange);
            setXHotizontal(-xChange*0.011f);
        }
        if(yChange>3){//
            Log.d("MyGLSurfaceView","向下"+yChange);
            setXHotizontal(-yChange*0.01f);
        }
        if(yChange<-3){
            Log.d("MyGLSurfaceView","向上"+yChange);
            setXHotizontal(yChange*0.01f);
        }
        Log.d("MyGLSurfaceView","--------------------------");
        hotizontal = dataX;
        vertical = dataY;
        z = dataZ;

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
