package animaladvertis.com.animaladvertis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.adapter.CollecRecycleViewAdapter;
import animaladvertis.com.animaladvertis.adapter.RecycleItemDecoration;
import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.ApplicationDate;
import animaladvertis.com.animaladvertis.beans.UserAnimal;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static animaladvertis.com.animaladvertis.R.id.map;
import static com.baidu.location.h.j.l;
import static java.lang.System.currentTimeMillis;

public class CollectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Animal> animals = new ArrayList<>();
    private CollecRecycleViewAdapter mAdapter;
    private ImageView iv_back;
    private ImageView iv_list;
    private TextView title;
    private int progress;
    private BmobFile src;
    private String TAG = "CollectActivitymsg";
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        recyclerView = (RecyclerView) findViewById(R.id.rv_collect);
        iv_back = (ImageView) findViewById(R.id.iv_collect_back);
        iv_list = (ImageView) findViewById(R.id.iv_collect_list);//更多选项
        title = (TextView) findViewById(R.id.collect_title);


        Bundle bundle = getIntent().getExtras();
        String kind = (String) bundle.get("kind");
        progress = (int) bundle.get("number");
        src = (BmobFile) bundle.get("src");
        title.setText(kind);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getDate();
        //initRecyclView();
    }


    private void initRecyclView(){
        recyclerView.setHasFixedSize(true);
        initRecyclerLayoutManager();
        initRecyclerAdapter(); // 初始化Adapter
        initItemDecoration(); // 初始化边界装饰
        initItemAnimator(); // 初始化动画效果
    }

    private void getDate(){
        Long str = System.currentTimeMillis();
        Log.d("Currenttime1",""+str);
        final BmobQuery<UserAnimal> query = new BmobQuery<UserAnimal>();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        ApplicationDate app = (ApplicationDate)getApplication();
        query.addWhereEqualTo("userName",app.getUsername());
        query.findObjects(new FindListener<UserAnimal>() {
            @Override
            public void done(List<UserAnimal> list, BmobException e) {
                if(e!=null) Log.d(TAG,""+e.getMessage()+e.getErrorCode());
                else if(list.size()==0) Log.d(TAG,"no date found");
                else{
                    for(UserAnimal userAnimal: list){
                        final int size = list.size();
                        BmobQuery<Animal> amQuery = new BmobQuery<Animal>();
                        amQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                        Log.d(TAG,userAnimal.getAnimalName());
                        amQuery.addWhereEqualTo("name",userAnimal.getAnimalName());
                        amQuery.findObjects(new FindListener<Animal>() {
                            @Override
                            public void done(final List<Animal> list, BmobException e) {
                                if(e!=null) Log.d(TAG,""+e.getMessage()+e.getErrorCode());
                                else if(list.size()==0) Log.d(TAG,"no date found+1");
                                else{
                                    animals.add(list.get(0));
                                    if(animals.size()==size)
                                    initRecyclView();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void initRecyclerAdapter() {
    }

    private void initRecyclerLayoutManager() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new CollecRecycleViewAdapter(animals,progress,src);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CollecRecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(view.getId()==R.id.tv_gotohome){//传递数据，开启导航界面
                    int pagePosition = position-1;
                    Intent intent = new Intent(CollectActivity.this,GuideActivity.class);
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
