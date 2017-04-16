package animaladvertis.com.animaladvertis;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import animaladvertis.com.animaladvertis.adapter.CollecRecycleViewAdapter;
import animaladvertis.com.animaladvertis.adapter.RecycleItemDecoration;
import animaladvertis.com.animaladvertis.beans.Animal;

import animaladvertis.com.animaladvertis.beans.AnimalMission;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.callback.OnAnimalFind;
import animaladvertis.com.animaladvertis.callback.OnMissionsFind;
import animaladvertis.com.animaladvertis.util.FindObjectUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;


public class CollectActivity extends BaserActivity {

    private RecyclerView recyclerView;
    private List<Animal> mAnimals = new ArrayList<>();
    private CollecRecycleViewAdapter mAdapter;
    private TextView loading;
    private int progress;
    private BmobFile src;
    private String TAG = "CollectActivitymsg";
    private String missionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        recyclerView = (RecyclerView) findViewById(R.id.rv_collect);
        loading = (TextView) findViewById(R.id.loading_remain);

        Bundle bundle = getIntent().getExtras();
        String name = (String) bundle.get("name");
        getSupportActionBar().setTitle(name);
        progress = (int) bundle.get("number");
        src = (BmobFile) bundle.get("src");
        missionName = (String)bundle.get("missionname");
        getDate();
    }


    private void getDate(){
        FindObjectUtil find = new FindObjectUtil(BmobUser.getCurrentUser(User.class));

        find.findAniamlByMission(missionName, new OnAnimalFind() {
            @Override
            public void result(List<Animal> animals) {
                if(animals!=null){
                    mAnimals = animals;
                    initRecyclView();
                }
            }
        });

    }

    private void initRecyclView(){
        recyclerView.setHasFixedSize(true);
        initRecyclerLayoutManager();
        initRecyclerAdapter(); // 初始化Adapter
        initItemDecoration(); // 初始化边界装饰
        initItemAnimator(); // 初始化动画效果
    }

    private void initRecyclerAdapter() {
    }

    private void initRecyclerLayoutManager() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new CollecRecycleViewAdapter(mAnimals,progress,src);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CollecRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(view.getId()==R.id.tv_gotohome){//传递数据，开启导航界面
                    Intent intent = new Intent(CollectActivity.this,GuideActivity.class);
                    intent.putExtra("merchantName",mAnimals.get(position-1).getMerchantName());
                    startActivity(intent);
                }
                if(view.getId()==R.id.iv_item_pictrue){//开启传单详情界面
                    Intent intent = new Intent(getApplicationContext(),CollectdetailActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void initItemDecoration(){
        recyclerView.addItemDecoration(new RecycleItemDecoration(40));
    }
    private void initItemAnimator(){
    }
}
