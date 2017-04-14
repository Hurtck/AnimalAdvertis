package animaladvertis.com.animaladvertis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.ArrayList;
import java.util.List;

import animaladvertis.com.animaladvertis.beans.AnimalModel;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.callback.FindRusultListener;
import animaladvertis.com.animaladvertis.util.FindObjectUtil;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

public class NewAniamlActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.vp_newAnimal)
    ViewPager vpNewAnimal;
    @BindView(R.id.loadingBar)
    TextView loadingBar;

    private List<View> viewList = new ArrayList<>();
    private List<AnimalModel> animalModels = new ArrayList<>();
    private String missionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_aniaml);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        missionName = bundle.getString("missionName");
        loadingBar.setText(missionName);

        new FindObjectUtil(BmobUser.getCurrentUser(User.class)).findAnimalModelByMissoion(
                missionName, new FindRusultListener<AnimalModel>() {
                    @Override
                    public void result(List<AnimalModel> result) {
                        if (result.size()!=0) {
                            animalModels = result;
                            initUI();
                        } else {
                            loadingBar.setText("加载失败");
                            Toast.makeText(getApplicationContext(), "卧槽，没有数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void initUI() {
        for (AnimalModel model : animalModels) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.item_viewpage_animal, null);
            ImageView src = (ImageView) view.findViewById(R.id.iv_src);
            TextView name = (TextView) view.findViewById(R.id.tv_name);
            TextView score = (TextView) view.findViewById(R.id.tv_score);
            TextView price = (TextView) view.findViewById(R.id.tv_price);
            RelativeLayout add = (RelativeLayout) view.findViewById(R.id.rl_animalList);
            NumberProgressBar npg = (NumberProgressBar) view.findViewById(R.id.npb_star);

            new LoadImageUtil().loadIMage(getApplicationContext(), src, model.getSrc().getFileUrl(), 1);
            name.setText(model.getName());
            score.setText(model.getScore());
            price.setText(model.getPrice()+"");
            npg.setProgress(model.getStart());
            add.setOnClickListener(this);
            viewList.add(view);
        }
        vpNewAnimal.setAdapter(new Adapter());

    }

    @Override
    public void onClick(View v) {
        int current = vpNewAnimal.getCurrentItem();
        AnimalModel animalModel = animalModels.get(current);
        Intent intent = new Intent(NewAniamlActivity.this,NewAniamlDetailActivity.class);
        intent.putExtra("model",animalModel);
        intent.putExtra("missionName",missionName);
        startActivity(intent);
    }


    class Adapter extends PagerAdapter {


        @Override
        public int getCount() {
            return animalModels.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    }
}
