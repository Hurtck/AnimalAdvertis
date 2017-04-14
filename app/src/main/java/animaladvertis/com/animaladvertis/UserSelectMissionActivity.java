package animaladvertis.com.animaladvertis;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import animaladvertis.com.animaladvertis.beans.AnimalMission;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.callback.OnAnimalAdd;
import animaladvertis.com.animaladvertis.callback.OnMissionsFind;
import animaladvertis.com.animaladvertis.util.FindObjectUtil;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

public class UserSelectMissionActivity extends AppCompatActivity {

    @BindView(R.id.loadingBar)
    TextView loadingBar;
    @BindView(R.id.vp_newAnimal)
    ViewPager vpNewAnimal;

    private User currentUser;
    private List<View> viewList = new ArrayList<>();
    private List<AnimalMission> animalMissions = new ArrayList<>();
    private String missionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select_mission);
        ButterKnife.bind(this);
        currentUser = BmobUser.getCurrentUser(User.class);
        new FindObjectUtil(BmobUser.getCurrentUser(User.class)).findAnimalMission(new OnMissionsFind() {
            @Override
            public void result(List<AnimalMission> missions, int progress) {
                if(missions!=null){
                    animalMissions = missions;
                    initUI();
                }else{
                    loadingBar.setText("加载失败");
                }
            }
        });

    }
    private void initUI() {
        for (AnimalMission model : animalMissions) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.item_viewpage_animal, null);
            ImageView src = (ImageView) view.findViewById(R.id.iv_src);
            TextView name = (TextView) view.findViewById(R.id.tv_name);
            TextView score = (TextView) view.findViewById(R.id.tv_score);
            TextView price = (TextView) view.findViewById(R.id.tv_price);
            TextView add = (TextView) view.findViewById(R.id.bt_addDetail);
            NumberProgressBar npg = (NumberProgressBar) view.findViewById(R.id.npb_star);

            new LoadImageUtil().loadIMage(getApplicationContext(), src, model.getPicFile().getFileUrl(), 1);
            name.setText(model.getName());
            score.setText(model.getRank()+"");
            price.setText(model.getPrice()+"");
            add.setText("点我添加");
            npg.setVisibility(View.INVISIBLE);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    missionName = animalMissions.get(vpNewAnimal.getCurrentItem()).getMissonName();
                    new FindObjectUtil(currentUser).addMissionForUser(missionName, new OnAnimalAdd() {
                        @Override
                        public void result(String msg) {
                            if("".equals(msg)) Toast.makeText(getApplicationContext(),"动物添加成功",Toast.LENGTH_SHORT).show();
                            else Toast.makeText(getApplicationContext(),""+msg,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            viewList.add(view);
        }
        vpNewAnimal.setAdapter(new Adapter());

    }

    class Adapter extends PagerAdapter {
        @Override
        public int getCount() {
            return animalMissions.size();
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
