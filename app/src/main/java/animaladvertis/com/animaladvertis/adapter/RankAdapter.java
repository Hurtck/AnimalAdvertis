package animaladvertis.com.animaladvertis.adapter;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.R;

import static animaladvertis.com.animaladvertis.R.drawable.rank;

/**
 * Created by 47321 on 2016/12/12 0012.
 */

public class RankAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater minflater;
    private RankHodler hodler;
    private List<Map<String,Object>> mlist;
    public RankAdapter(Context context,List<Map<String,Object>> list){
        mContext = context;
        mlist = list;
        minflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mlist.size();
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
            convertView = minflater.inflate(R.layout.item_rank,null);
            hodler = new RankHodler();
            hodler.ci_photo = (de.hdodenhof.circleimageview.CircleImageView)convertView.findViewById(R.id.ci_item_rankPhoto);
            hodler.pg_progress = (NumberProgressBar)convertView.findViewById(R.id.pg_rankProgress_item_rank);
            hodler.rankName = (TextView)convertView.findViewById(R.id.tv_item_rankName);
            hodler.rankScore = (TextView)convertView.findViewById(R.id.tv_item_rankScore);
            convertView.setTag(hodler);
        }else{
            hodler = (RankHodler)convertView.getTag();
        }
        Map<String,Object> map = mlist.get(position);
        hodler.ci_photo.setImageResource((int)map.get("rankPhoto"));
        hodler.pg_progress.setProgress((int)map.get("progress"));
        hodler.rankScore.setText("Score "+map.get("rankScore"));
        hodler.rankName.setText((String)map.get("rankName"));
        return convertView;
    }
}
