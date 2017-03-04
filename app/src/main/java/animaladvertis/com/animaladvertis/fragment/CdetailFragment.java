package animaladvertis.com.animaladvertis.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import animaladvertis.com.animaladvertis.R;

/**
 * Created by 47321 on 2016/12/21 0021.
 */

public class CdetailFragment extends Fragment {
    private ViewPager vp_image;
    private TextView tv_name;
    private TextView tv_title;
    private List<View> views;
    private TextView tv_moredetail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cdetail1,container,false);
        vp_image = (ViewPager) view.findViewById(R.id.vp_detail);
        tv_title = (TextView) view.findViewById(R.id.tv_title);

        tv_moredetail = (TextView) view.findViewById(R.id.tv_moredetail);
        tv_moredetail.setText("传承饮食文化，传播特色美味——我家xx(店名)饭店\n" +
                "　　美味和实惠 “我家xx(店名)”为您呈现\n" +
                "　　绿色环保爽口大众化的美食——我家xx(店名)饭店\n" +
                "　　给肠温柔抚摸，给胃舒服享受——我家xx(店名)饭店\n" +
                "　　在餐饮行业的道路上和刘翔赛跑——我家xx(店名)饭店"+
                "传承饮食文化，传播特色美味——我家xx(店名)饭店\n" +
                "　　美味和实惠 “我家xx(店名)”为您呈现\n" +
                "　　绿色环保爽口大众化的美食——我家xx(店名)饭店\n" +
                "　　给肠温柔抚摸，给胃舒服享受——我家xx(店名)饭店\n" +
                "　　在餐饮行业的道路上和刘翔赛跑——我家xx(店名)饭店"+
                "传承饮食文化，传播特色美味——我家xx(店名)饭店\n" +
                "　　美味和实惠 “我家xx(店名)”为您呈现\n" +
                "　　绿色环保爽口大众化的美食——我家xx(店名)饭店\n" +
                "　　给肠温柔抚摸，给胃舒服享受——我家xx(店名)饭店\n" +
                "　　在餐饮行业的道路上和刘翔赛跑——我家xx(店名)饭店"+
                "传承饮食文化，传播特色美味——我家xx(店名)饭店\n" +
                "　　美味和实惠 “我家xx(店名)”为您呈现\n" +
                "　　绿色环保爽口大众化的美食——我家xx(店名)饭店\n" +
                "　　给肠温柔抚摸，给胃舒服享受——我家xx(店名)饭店\n" +
                "　　在餐饮行业的道路上和刘翔赛跑——我家xx(店名)饭店"+
                "传承饮食文化，传播特色美味——我家xx(店名)饭店\n" +
                "　　美味和实惠 “我家xx(店名)”为您呈现\n" +
                "　　绿色环保爽口大众化的美食——我家xx(店名)饭店\n" +
                "　　给肠温柔抚摸，给胃舒服享受——我家xx(店名)饭店\n" +
                "　　在餐饮行业的道路上和刘翔赛跑——我家xx(店名)饭店");

        views = new ArrayList<>();
        View view1 = inflater.inflate(R.layout.item_viewpager_detail1,null);
        views.add(view1);
        view1 = inflater.inflate(R.layout.item_viewpager_detail2,null);
        views.add(view1);
        view1 = inflater.inflate(R.layout.item_viewpager_detail3,null);
        views.add(view1);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {

                return view==object;
            }
        };
        vp_image.setAdapter(pagerAdapter);

        return view;
    }
}
