package animaladvertis.com.animaladvertis;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.fragment.CdetailFragment;
import animaladvertis.com.animaladvertis.fragment.CdetailFragmentB;

import static android.R.attr.rating;

public class CollectdetailActivity extends BaserActivity {

    private CdetailFragment detail;
    private CdetailFragmentB cdetailFragmentB;
    private TextView ib_a;
    private TextView ib_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collectdetail);
        ib_a = (TextView) findViewById(R.id.ib_detail_a);
        ib_b = (TextView) findViewById(R.id.ib_detail_b);
        ib_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultFragment();
            }
        });

        ib_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                cdetailFragmentB = new CdetailFragmentB();
                ib_a.setBackgroundResource(R.drawable.jieshao);
                ib_b.setBackgroundResource(R.drawable.pinglun_down);
                fragmentTransaction.replace(R.id.fl_content,cdetailFragmentB);
                fragmentTransaction.commit();
            }
        });

        setDefaultFragment();
    }


    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        ib_a.setBackgroundResource(R.drawable.jieshao_down);
        ib_b.setBackgroundResource(R.drawable.pinglun);
        detail = new CdetailFragment();
        fragmentTransaction.replace(R.id.fl_content,detail);
        fragmentTransaction.commit();
    }
}
