package animaladvertis.com.animaladvertis;

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
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
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
import static com.baidu.location.h.j.p;

public class MerchantActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.tv_registToMerchant)
    TextView tvRegistToMerchant;
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

    private Merchant merchant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        ButterKnife.bind(this);


        getCurrentMenchant();//获取商户信息

        initChars();//初始化表格数据



        tvUserDate.setOnClickListener(this);
    }

    private void getCurrentMenchant() {
        BmobQuery<Merchant> query = new BmobQuery();
        query.addWhereEqualTo("objectId", User.getCurrentUser(User.class).getObjectId());
        query.findObjects(new FindListener<Merchant>() {
            @Override
            public void done(List<Merchant> list, BmobException e) {
                if(list!=null){
                    merchant = list.get(0);
                }
            }
        });
    }

    private void initChars() {

        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_charts,null);
        cvChart = (LineChartView) view.findViewById(R.id.cv_chart);

        TextView textView = (TextView) view.findViewById(R.id.tv_animalNme);
        textView.setText("呵呵");

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
            Animal animal1 = new Animal();
            animal1.setName("哈哈");
            animal1.setScore(10);
            //animal1.setPicture(userDate.getUserPhoto());
            animal1.setPicture(merchant.getMerChantPhoto());
            animal1.setShop(merchant.getMerChantPhoto());
            //animal1.setShop(userDate.getUserPhoto());
            animal1.setLocationname(merchant.getLocation());
            animal1.setShopName(merchant.getName());
            animal1.setTargetLocation("中国江西省南昌市青山湖区");
            animal1.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e!=null) Log.d("UserActivitymsg",e.getErrorCode()+e.getMessage());
                }
            });
        }
    }
}
