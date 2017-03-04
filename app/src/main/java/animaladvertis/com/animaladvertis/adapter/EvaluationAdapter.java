package animaladvertis.com.animaladvertis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 47321 on 2016/12/15 0015.
 */

public class EvaluationAdapter extends BaseAdapter {

    List<Map<String,Object>> mList;
    Context mContext;
    public EvaluationAdapter(Context context, List<Map<String,Object>> list){
        mContext = context;
        mList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(convertView==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_evaluation,parent);
        }else{
            view = convertView;
        }
        CircleImageView userPhoto = (CircleImageView) view.findViewById(R.id.cv_item_collecdes_userphoto);
        TextView userName = (TextView) view.findViewById(R.id.tv_item_collecdes_username);
        TextView time = (TextView) view.findViewById(R.id.tv_item_collecdes_time);
        TextView content = (TextView) view.findViewById(R.id.tv_item_collecdes_content);
        userPhoto.setImageResource(R.drawable.pig);
        Map map = mList.get(position);
        userName.setText((String)map.get("userName"));
        time.setText((String)map.get("time"));
        content.setText((String)map.get("content"));
        return view;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
}
