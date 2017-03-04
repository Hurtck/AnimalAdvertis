package animaladvertis.com.animaladvertis.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import static android.R.attr.x;

/**
 * Created by 47321 on 2016/12/16 0016.
 */

public class ExtendScrollView extends ScrollView {
    // 滑动距离及坐标
    private float xDistance, yDistance, xLast, yLast;

    public ExtendScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN :
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance +=Math.abs(curX-xLast);
                yDistance +=Math.abs(curY-yLast);
                yLast = curY;
                xLast = curX;
                if(xDistance>yDistance) return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
