package animaladvertis.com.animaladvertis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.adapter.CollecRecycleViewAdapter;
import animaladvertis.com.animaladvertis.adapter.CollecdesHodler;
import animaladvertis.com.animaladvertis.adapter.EvaluationAdapter;
import animaladvertis.com.animaladvertis.adapter.RecycleItemDecoration;
import animaladvertis.com.animaladvertis.beans.Animal;

public class CollectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Animal> animals;
    private CollecRecycleViewAdapter mAdapter;
    private ImageView iv_back;
    private ImageView iv_list;
    private TextView title;
    int progress,src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        recyclerView = (RecyclerView) findViewById(R.id.rv_collect);
        iv_back = (ImageView) findViewById(R.id.iv_collect_back);
        iv_list = (ImageView) findViewById(R.id.iv_collect_list);
        title = (TextView) findViewById(R.id.collect_title);


        Bundle bundle = getIntent().getExtras();
        String kind = (String) bundle.get("kind");
        progress = (int) bundle.get("number");
        src = (int) bundle.get("src");
        title.setText(kind);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        animals = new ArrayList<>();
        addDate();
        initRecyclView();
    }


    private void initRecyclView(){

        recyclerView.setHasFixedSize(true);
        initRecyclerLayoutManager();
        initRecyclerAdapter(); // 初始化Adapter
        initItemDecoration(); // 初始化边界装饰
        initItemAnimator(); // 初始化动画效果

    }


    private void addDate(){
        Animal animal = new Animal();
        animal.setSell("10积分");
        animal.setHomeLocation("南昌");
        animal.setTitle("雷神");
        animal.setPicSrc(R.drawable.move);
        animals.add(animal);

        animal = new Animal();
        animal.setSell("121积分");
        animal.setHomeLocation("上海");
        animal.setTitle("RESDGNT");
        animal.setPicSrc(R.drawable.move2);
        animals.add(animal);

        animal = new Animal();
        animal.setSell("15积分");
        animal.setHomeLocation("上海");
        animal.setTitle("天地英雄");
        animal.setPicSrc(R.drawable.move3);
        animals.add(animal);
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
                    Log.d("LOOK","1234567889wioerigj"+position);
                    Intent intent = new Intent(getApplicationContext(),CollectdetailActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void initItemDecoration(){

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
        recyclerView.addItemDecoration(new RecycleItemDecoration(40));

    }
    private void initItemAnimator(){
    }
}
