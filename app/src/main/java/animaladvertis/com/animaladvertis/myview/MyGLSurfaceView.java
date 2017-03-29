package animaladvertis.com.animaladvertis.myview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import animaladvertis.com.animaladvertis.util.MyRenderer;

/**
 * Created by 47321 on 2017/3/28 0028.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    MyRenderer mRenderer;

    float x,y;

    public MyGLSurfaceView(Context context){
        super(context);
        setZOrderOnTop(true);
        setEGLConfigChooser(8,8,8,8,16,0);
        getHolder().setFormat(PixelFormat.TRANSPARENT);

    }

    public MyGLSurfaceView(Context context, AttributeSet attrs){
        super(context,attrs);
        setZOrderOnTop(true);
        setEGLConfigChooser(8,8,8,8,16,0);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
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
                    mRenderer.rotate+=0.1f;
                }
                if(event.getX()<x){
                mRenderer.rotate-=0.1f;
                }
                requestRender();
                break;
            case MotionEvent.ACTION_UP :
                break;
        }
        return true;
    }
}
