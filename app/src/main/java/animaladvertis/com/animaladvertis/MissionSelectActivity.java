package animaladvertis.com.animaladvertis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.List;

import animaladvertis.com.animaladvertis.adapter.ViewHodler;
import animaladvertis.com.animaladvertis.beans.AnimalMission;
import animaladvertis.com.animaladvertis.beans.MissionAnimal;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.callback.OnMissionsFind;
import animaladvertis.com.animaladvertis.util.FindObjectUtil;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;


public class MissionSelectActivity extends AppCompatActivity {

    @BindView(R.id.lv_mission)
    ListView lvMission;
    @BindView(R.id.activity_maission_select)
    RelativeLayout activityMaissionSelect;
    private List<AnimalMission> animalMissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maission_select);
        ButterKnife.bind(this);
        lvMission.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MissionSelectActivity.this,NewAniamlActivity.class);
                intent.putExtra("missionName",animalMissions.get(position).getMissonName());
                startActivity(intent);
            }
        });
        new FindObjectUtil(BmobUser.getCurrentUser(User.class)).findAnimalMission(new OnMissionsFind() {
            @Override
            public void result(List<AnimalMission> missions, int progress) {
                if(missions!=null){
                    animalMissions = missions;
                    lvMission.setAdapter(new ListAdapter());
                }
            }
        });
    }

    class ListAdapter extends BaseAdapter{
        private ViewHodler hodler;
        private LayoutInflater layoutInflater;
        public ListAdapter(){
            layoutInflater = getLayoutInflater();
        }
        @Override
        public int getCount() {
            return animalMissions.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = layoutInflater.from(getApplicationContext()).inflate(R.layout.item_collect,null);
                hodler = new ViewHodler();
                hodler.tv_kind = (TextView) convertView.findViewById(R.id.tv_kind_item_collect);
                hodler.pg_progress = (NumberProgressBar) convertView.findViewById(R.id.pg_progress_item_collect);
                hodler.im_imag = (ImageView) convertView.findViewById(R.id.im_item_img);
                convertView.setTag(hodler);
            }else {
                hodler = (ViewHodler)convertView.getTag();
            }
            AnimalMission animalMission = animalMissions.get(position);
            new LoadImageUtil().loadIMage(getApplicationContext(),hodler.im_imag,animalMission.getPicFile()
                    .getFileUrl(),0);
            hodler.tv_kind.setText(animalMission.getName());
            return convertView;
        }
    }

}
