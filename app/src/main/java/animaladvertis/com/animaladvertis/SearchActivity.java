package animaladvertis.com.animaladvertis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import animaladvertis.com.animaladvertis.myview.RadarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.radar)
    RadarView radar;
    @BindView(R.id.tv_search_number)
    TextView tvSearchNumber;
    @BindView(R.id.br_search_map)
    Button brSearchMap;
    @BindView(R.id.iv_search_back)
    ImageView ivSearchBack;
    @BindView(R.id.activity_search)
    RelativeLayout activitySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.br_search_map, R.id.iv_search_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.br_search_map:
                Intent intent = new Intent(SearchActivity.this,GuideActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_search_back:
                finish();
                break;
        }
    }
}
