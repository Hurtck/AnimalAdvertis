package animaladvertis.com.animaladvertis.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 47321 on 2016/12/12 0012.
 */

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    public MyViewPagerAdapter(android.support.v4.app.FragmentManager fm, List<Fragment> fragments){
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);

    }
}
