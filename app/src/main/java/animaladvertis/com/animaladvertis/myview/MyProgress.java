package animaladvertis.com.animaladvertis.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import animaladvertis.com.animaladvertis.R;

/**
 * Created by 47321 on 2016/12/12 0012.
 */

public class MyProgress extends ProgressBar {
    private String  text;
    private Paint paint;

    public MyProgress(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        System.out.println("1");
        initText();
    }

    public MyProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        System.out.println("2");
        initText();
    }

    public MyProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        System.out.println("3");
        initText();
    }

    @Override
    public synchronized void setProgress(int progress) {
        setText(progress);
        super.setProgress(progress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        this.paint.getTextBounds(this.text,0,this.text.length(),rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.paint);
    }
    private void initText(){
        this.paint = new Paint();
        this.paint.setColor(Color.WHITE);
    }
    private void setText(){
        setText(this.getProgress());
    }
    private void setText(int progress){
        int i = (progress * 100)/this.getMax();
        this.text = String.valueOf(i) + "%";
    }

}
