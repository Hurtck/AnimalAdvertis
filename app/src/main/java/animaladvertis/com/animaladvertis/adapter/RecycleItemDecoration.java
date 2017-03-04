package animaladvertis.com.animaladvertis.adapter;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import animaladvertis.com.animaladvertis.R;

/**
 * Created by 47321 on 2016/12/13 0013.
 */

public class RecycleItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    public RecycleItemDecoration(int space){
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildPosition(view) != 0){
            outRect.top = 1;
        }
    }
}
