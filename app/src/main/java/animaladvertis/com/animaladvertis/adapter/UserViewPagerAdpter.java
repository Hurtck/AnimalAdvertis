package animaladvertis.com.animaladvertis.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

/**
 * Created by 47321 on 2016/12/14 0014.
 */

public class UserViewPagerAdpter extends PagerAdapter {
    private List<View> mlist;
    public UserViewPagerAdpter(List<View> list){
        mlist = list;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mlist.get(position));
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if(mlist.get(position).getParent()==null) container.addView(mlist.get(position));
        else {
            ((ViewGroup)mlist.get(position).getParent()).removeView(mlist.get(position));
            container.addView(mlist.get(position));
        }
        return mlist.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
