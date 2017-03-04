package animaladvertis.com.animaladvertis.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.R;
import animaladvertis.com.animaladvertis.adapter.CDetailRecycleAdapter;
import animaladvertis.com.animaladvertis.adapter.RecycleItemDecoration;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 47321 on 2016/12/21 0021.
 */

public class CdetailFragmentB extends android.app.Fragment {
    private RecyclerView rv_cDtail;
    private List<Map<String,Object>> mapList;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail2,container,false);
        rv_cDtail = (RecyclerView) view.findViewById(R.id.rv_cDetail);

        mapList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("userName","asd");
        map.put("time","2016-9-46");
        map.put("content","498sdfasdfa");
        mapList.add(map);

        map = new HashMap<>();
        map.put("userName","asd");
        map.put("time","2016-9-46");
        map.put("content","498sdfasdfa");
        mapList.add(map);

        map = new HashMap<>();
        map.put("userName","asd");
        map.put("time","2016-9-46");
        map.put("content","498sdfasdfa");
        mapList.add(map);

        map = new HashMap<>();
        map.put("userName","asd");
        map.put("time","2016-9-46");
        map.put("content","498sdfasdfa");
        mapList.add(map);

        map = new HashMap<>();
        map.put("userName","asd");
        map.put("time","2016-9-46");
        map.put("content","498sdfasdfa");
        mapList.add(map);
        rv_cDtail.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        CDetailRecycleAdapter recycleAdapter = new CDetailRecycleAdapter(mapList);
        rv_cDtail.setAdapter(recycleAdapter);
        rv_cDtail.addItemDecoration(new RecycleItemDecoration(10));
        return view;
    }
}
