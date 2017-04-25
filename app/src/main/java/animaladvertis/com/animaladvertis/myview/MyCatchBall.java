package animaladvertis.com.animaladvertis.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import animaladvertis.com.animaladvertis.callback.OnCathListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 47321 on 2017/4/21 0021.
 */

public class MyCatchBall extends ImageView implements SensorEventListener {

    private int mHeight;
    private int mWidth;
    private int radius = 0, rotate = 0,rotate1 = 0,cx,cy,time = 0;
    private float scale = 1.0f;
    private int STATE=0;
    private int oratation = 0;
    private int speed = 10;
    public static final int SLOW = 0;
    public static final int ACCELERATED = 1;
    public static final int STOP = 1;
    public static final int STAR = 0;
    private Context mContext;
    private SensorManager sensorManager;
    private float[] accelerometerValues = new float[3];
    private float[] magneticFieldValues = new float[3];
    private int catchState = 0;
    private OnCathListener onCathListener;
    private int catchTime = 0;
    private RectF rect ;
    private float topOffset;


    public MyCatchBall(Context context) {
        super(context);
    }

    public MyCatchBall(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensors = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this,magnetic,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensors,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec) + getPaddingLeft() + getPaddingRight();
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec) + getPaddingBottom() + getPaddingTop();
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        cx = mWidth/2;cy = mHeight/2;
        topOffset = (mHeight-mWidth)/2;
        rect = new RectF(0,(mHeight-mWidth)/2,mWidth,mWidth+(mHeight-mWidth)/2);
        super.onMeasure(MeasureSpec.makeMeasureSpec(widthSize, widthMode), MeasureSpec.makeMeasureSpec(heightSize, heightMode));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        paint.setColor(Color.GREEN);
        paint.setAlpha(100);
        paint.setAntiAlias(true);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, paint);
        paint.setAlpha(50);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(rect,0,catchState,true,paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(12);
        canvas.save();
        canvas.rotate(rotate , mWidth / 2, mHeight / 2);
        paint.setStrokeWidth(6);
        canvas.drawLine(0, cy, cx * 0.2f, mHeight / 2, paint);
        canvas.drawLine(cx,topOffset,cx,topOffset+cx*0.2f,paint);
        canvas.drawLine(cx*2,cy,cx*2-cx * 0.2f,cy,paint);
        canvas.drawLine(cx,cy*2-topOffset,cx,cy*2-topOffset-cx*0.2f,paint);

        paint.setStrokeWidth(14);
        paint.setAlpha(100);
        canvas.restore();
        canvas.scale(scale,scale,cy,cx);
        canvas.rotate(rotate1 , cx, cy);
        canvas.drawLine(cx*0.4f,cy,cx*0.6f,cy,paint);
        canvas.drawCircle(cx, cy, mWidth / 2 * 0.6f, paint);
        if(STATE==0){
            if(time==speed){
                if(scale<0.3) {//scala小于0.3方向变为变大
                    scale+=0.01;oratation = 1;
                }
                else if(scale>0.9) {//scala大于0.9方向变为变变小
                    scale-=0.01; oratation = 0;
                }
                else if(oratation==0) scale-=0.01;//<0.3scale<0.9方向为变小 scale变小
                else scale+=0.01;

                rotate1+=2;
                rotate+=1;
                rotate = rotate>360?0:rotate;
                rotate1 = rotate1>360?0:rotate1;
                Log.d("MyView", "date;　"+rotate+" scale"+scale+"ortation "+oratation);
            }
            time++;
            time = time>speed?time=0:time;
            this.invalidate();
        }
    }
    private void changeOritation(){
        oratation = (int)(Math.random()*360);
    }
    public void changeState(int tend){
        STATE = tend;
        invalidate();
    }
    public void changeSpeed(int state){
        if(state == ACCELERATED) speed--;
        if(state == SLOW) speed++;
        speed = speed>=0?speed:0;
        Log.d("MyView+","toast: "+speed);
        invalidate();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            magneticFieldValues = event.values;

        }
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            accelerometerValues = event.values;
        }
        //catchTime = catchTime>10?catchTime=0:catchTime++;
         calculateOrientation();
    }

    private void calculateOrientation() {
        float[] values = new float[3];
        float[] R = new float[9];
        SensorManager.getRotationMatrix(R, null, accelerometerValues,
                magneticFieldValues);
        SensorManager.getOrientation(R, values);
        float x = values[2];
        float y = values[1];
        values[0] = (float) Math.toDegrees(values[0]);
        values[1] = (float) Math.toDegrees(values[1]);
        values[2] = (float) Math.toDegrees(values[2]);

        float baseX = Math.abs(values[1]);
        float horizontal = 5*(float) Math.tan(Math.toRadians(90-values[2]));
        float vertical = (float) (5*Math.tan(30))-5*(float) Math.tan(Math.toRadians(90-baseX));
        vertical = 5-5*(float) Math.tan(Math.toRadians(90-baseX));
        horizontal = 5*(float) Math.tan(Math.toRadians(values[2]));
        horizontal = horizontal>=10?10:horizontal;
        vertical = vertical>-7?vertical:-7;

        if(Math.abs(horizontal)+Math.abs(vertical)>=2) {
            speed=0;catchState=0;
        } else {
            speed=10;catchState++;
        }

        if(catchState==360&&onCathListener!=null&&speed==10)  onCathListener.onStateChange(catchState);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void stop(){
        sensorManager.unregisterListener(this);
    }

    public void setOnCatchLisener(OnCathListener onCatchLisener){
        this.onCathListener = onCatchLisener;
    }
}
