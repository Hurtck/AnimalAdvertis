package animaladvertis.com.animaladvertis.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import animaladvertis.com.animaladvertis.R;
import animaladvertis.com.animaladvertis.callback.RadarListener;


/**
 * Created by 47321 on 2017/2/16 0016.
 */

public class RadarView extends View {

    private Context mContext;
    private Boolean isSearching;
    private Paint mPaint;
    private Bitmap mScanBmp;
    private int mOffsetArgs;//扫描的偏移量
    private Bitmap mDefaultPointBmp;//默认的扫描设备偏移量
    private Bitmap mLightPointBmp;//高亮的扫描设备偏移量
    private int mPointCount;//被扫描点的数量
    private List<String> mPointArray;//存放偏移量的map
    private Random mRandom = new Random();
    private int mHight,mWidth;
    int moutWidth;//外圆高度
    int mCy,mCx;//中心点
    int mInsideRadius,mOutsideRadius;//内外圆半径
    private RadarListener mRadarListener;

    public RadarView(Context context, AttributeSet attribuset,int defStyleAttr){
        super(context,attribuset,defStyleAttr);
        init(context);
    }

    public  RadarView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        init(context);
    }

    public RadarView(Context context){
        super(context);
        init(context);
    }
    /*
    初始化要用的对象
     */
    private void init(Context context){
        mPaint = new Paint();
        isSearching = false;
        mPointCount = 3;
        mPointArray = new ArrayList<String>();
        mContext = context;
        mLightPointBmp = Bitmap.createBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.radar_light_point_ico));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            Log.d("TAG","anananan");
            isSearching=true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP) {
            isSearching=false;
            mOffsetArgs = 0;
            Log.d("TAG","upupup");
        }
        this.invalidate();
        return true;
    }

    /*
        测量视图获取控件大小
         */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mHight==0||mWidth==0){
            final int minimumWidth = getSuggestedMinimumWidth();
            final int minimumHight = getSuggestedMinimumHeight();
            mWidth = resolveMeasured(widthMeasureSpec,minimumWidth);
            mHight = resolveMeasured(widthMeasureSpec,minimumHight);
            mScanBmp = Bitmap.createBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.radar_scan_img));
            float scaleX =  ((float) mWidth)/mScanBmp.getWidth();
            float scaleY = ((float) mHight)/mScanBmp.getHeight();
            Matrix matrix = new Matrix();
            matrix.setScale(scaleX,scaleY);
            mScanBmp = Bitmap.createBitmap(mScanBmp,0,0,mScanBmp.getWidth(),mScanBmp.getHeight(),matrix,true);
            //计算中心点
            mCx = mWidth/2;
            mCy = mHight/2;
            //计算外圆宽度
            moutWidth = mWidth/10;
            //计算内、外圆半径
            mOutsideRadius = mWidth/2;
            mInsideRadius = (mWidth-moutWidth)/4/2;
        }
    }

    /*
    绘制视图
     */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        //绘制圆
        canvas.drawCircle(mCx,mCy,mOutsideRadius,mPaint);

        mPaint.setColor(0xff3278B4);
        canvas.drawCircle(mCx,mCy,mInsideRadius*4,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff31C9F2);
        canvas.drawCircle(mCx,mCy,mInsideRadius*3,mPaint);

        canvas.drawCircle(mCx,mCy,mInsideRadius*2,mPaint);

        canvas.drawCircle(mCx,mCy,mInsideRadius*1,mPaint);
        //绘制对角线
        int startX,startY,endX,endY;
        double radian;
        radian = Math.toRadians((double)45);
        startX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));
        startY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));

        radian = Math.toRadians((double) 45 + 180);
        endX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));
        endY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));
        canvas.drawLine(startX, startY, endX, endY, mPaint);
        // 绘制135°~315°对角线
        // 计算开始位置x/y坐标点
        radian = Math.toRadians((double) 135);
        startX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));
        startY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));
        // 计算结束位置x/y坐标点
        radian = Math.toRadians((double) 135 + 180);
        endX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));
        endY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));
        canvas.drawLine(startX, startY, endX, endY, mPaint);

        canvas.save();// 用来保存Canvas的状态.save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作.

        if (isSearching) {// 判断是否处于扫描
            canvas.rotate(mOffsetArgs, mCx, mCy);// 绘制旋转角度,参数一：角度;参数二：x中心;参数三：y中心.
            canvas.drawBitmap(mScanBmp, mCx - mScanBmp.getWidth() / 2, mCy
                    - mScanBmp.getHeight() / 2, null);// 绘制Bitmap扫描图片效果
            mOffsetArgs += 3;
            Log.d("RadarViewmsg1",""+mOffsetArgs);
            if(mOffsetArgs>=720&&mRadarListener!=null){
                mOffsetArgs = 0;
                Log.d("RadarViewmsg2",""+mOffsetArgs);
                mRadarListener.onChangeListener();
            }
        } else {
            canvas.drawBitmap(mScanBmp, mCx - mScanBmp.getWidth() / 2, mCy
                    - mScanBmp.getHeight() / 2, null);
        }

        canvas.restore();// 用来恢复Canvas之前保存的状态.防止save后对Canvas执行的操作对后续的绘制有影响.

        if(mPointCount >0){
            if (mPointCount > mPointArray.size()) {// 当圆点总数大于存储坐标点数目时,说明有增加,需要重新生成随机坐标点
                int mx = mInsideRadius + mRandom.nextInt(mInsideRadius * 6);
                int my = mInsideRadius + mRandom.nextInt(mInsideRadius * 6);
                mPointArray.add(mx + "/" + my);
            }
            // 开始绘制坐标点
            for (int i = 0; i < mPointArray.size(); i++) {
                String[] result = mPointArray.get(i).split("/");

                // 开始绘制动态点
                if (i < mPointArray.size())
                    canvas.drawBitmap(mLightPointBmp,
                            Integer.parseInt(result[0]),
                            Integer.parseInt(result[1]), null);
            }

        }
        if (isSearching)
            this.invalidate();
    }

    public void setSearchingListener(RadarListener radarListener){
        mRadarListener = radarListener;
    }

    /**
     * TODO<设置扫描状态>
     *
     * @return void
     */
    public void setSearching(boolean status) {
        this.isSearching = status;
        this.invalidate();
    }

    public boolean isSearching(){
        return this.isSearching;
    }

    /**
     * TODO<新增动态点>
     *
     * @return void
     */
    public void addPoint() {
        mPointCount++;
        this.invalidate();
    }

    /**
     * TODO<获取动态点数量>
     *
     * @return int
     */
    public int getPointCount(){
        return mPointCount;
    }

    /**
     * TODO<解析获取控件宽高>
     *
     * @return int
     */
    private int resolveMeasured(int measureSpec, int desired) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = desired;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:
            default:
                result = specSize;
        }
        return result;
    }

}
