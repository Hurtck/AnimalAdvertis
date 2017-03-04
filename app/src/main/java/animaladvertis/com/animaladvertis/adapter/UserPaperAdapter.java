package animaladvertis.com.animaladvertis.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.ListView;

import java.util.List;

/**
 * Created by 47321 on 2016/12/14 0014.
 */

public class UserPaperAdapter extends PagerAdapter{
    List<View> mViews;
    public UserPaperAdapter(List<View> Views){
        mViews = Views;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

}
