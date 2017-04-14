package animaladvertis.com.animaladvertis;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.beans.Merchant;
import animaladvertis.com.animaladvertis.beans.User;
import animaladvertis.com.animaladvertis.callback.OnAnimalFind;
import animaladvertis.com.animaladvertis.util.FindObjectUtil;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

import static com.baidu.location.h.j.B;
import static com.baidu.location.h.j.S;
import static com.baidu.location.h.j.p;

public class MerchantActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_userDate)
    Button tvUserDate;
    // @BindView(R.id.cv_chart)
    LineChartView cvChart;
    @BindView(R.id.activity_merchant)
    RelativeLayout activityMerchant;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.rl_animalState)
    RelativeLayout rlAnimalState;
    @BindView(R.id.rl_comment)
    RelativeLayout rlComment;
    //@BindView(R.id.rl_charts)
    RelativeLayout rlCharts;
    @BindView(R.id.sv_mainContent)
    ScrollView svMainContent;
    @BindView(R.id.ll_mainContent)
    LinearLayout llMainContent;
    private User currentUser;
    private List<Animal> mAnimals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        ButterKnife.bind(this);


        init();
        tvUserDate.setOnClickListener(this);
        rlAnimalState.setOnClickListener(this);
        rlComment.setOnClickListener(this);
    }

    private void init(){
        mAnimals = new ArrayList<Animal>();
        currentUser = BmobUser.getCurrentUser(User.class);

        tvUsername.setText(currentUser.getMerChantName());
        new LoadImageUtil().loadIMage(getApplicationContext(),profileImage,currentUser.getMerChantPhoto().getFileUrl(),0);
        new FindObjectUtil(currentUser).findAnimalForMerchant(currentUser.getMerChantName(), new OnAnimalFind() {
            @Override
            public void result(List<Animal> animals) {
                if(animals!=null){
                    for (Animal animal: animals) {
                        addChars(animal);
                    }
                }
            }
        });
    }

    private void addChars(Animal animal) {

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_charts,null);
        cvChart = (LineChartView) view.findViewById(R.id.cv_chart);

        TextView textView = (TextView) view.findViewById(R.id.tv_animalNme);
        textView.setText(animal.getName());

        //cvChart = new LineChartView(getApplicationContext());
        ViewGroup.LayoutParams params= new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
        ,400);

        view.setLayoutParams(params);

        View lineView = new View(getApplicationContext());
        ViewGroup.LayoutParams lineParams= new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                ,10);
        lineView.setLayoutParams(lineParams);
        llMainContent.addView(lineView);



        //获取月份的数据
        List<AxisValue> mAxisValues = new ArrayList<AxisValue>();
        for (int i = 1; i < 13; i++) {
            String xItemStr = i + "月份";
            mAxisValues.add((new AxisValue(i)).setLabel(xItemStr)); //为每个对应的i设置相应的label(显示在X轴)  
        }
        //添加在坐标上对应的点
        List<PointValue> values = new ArrayList<PointValue>();
        values.add(new PointValue(1, 13));
        values.add(new PointValue(2, 9));
        values.add(new PointValue(3, 2));
        values.add(new PointValue(4, 1));
        values.add(new PointValue(5, 5));
        values.add(new PointValue(6, 29.82f));
        values.add(new PointValue(7, 13));
        values.add(new PointValue(8, 9));
        values.add(new PointValue(9, 2));
        values.add(new PointValue(10, 1));
        values.add(new PointValue(11, 5));
        values.add(new PointValue(12, 5));

        //添加线段
        Line line = new Line(values).setColor(Color.BLUE).setCubic(false);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axisX = new Axis().setHasTiltedLabels(true);
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("\r\n\r");
        axisX.setMaxLabelChars(1);
        axisX.setValues(mAxisValues);

        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setBaseValue(Float.NEGATIVE_INFINITY);


        cvChart.setInteractive(true);
        cvChart.setZoomType(ZoomType.HORIZONTAL);
        cvChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        cvChart.setLineChartData(data);
        llMainContent.addView(view);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_userDate){
            startActivity(new Intent(MerchantActivity.this,MissionSelectActivity.class));
        }
    }
}
