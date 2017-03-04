package animaladvertis.com.animaladvertis.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.R;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.baidu.location.f.mC;

/**
 * Created by 47321 on 2016/12/25 0025.
 */

public class LookAdpter extends BaseAdapter {
    private List<Map<String,Object>> mList;
    private Context mContext;
    private MyHodler hodler;

    public LookAdpter(List<Map<String,Object>> list,Context context){
        mList = list;
        mContext = context;

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_look,null);
            hodler = new MyHodler();
            hodler.img = (CircleImageView) convertView.findViewById(R.id.cv_item_look);
            hodler.title = (TextView) convertView.findViewById(R.id.tv_title_item_look);
            hodler.content = (TextView) convertView.findViewById(R.id.tv_content_item_look);
            convertView.setTag(hodler);
        }else{
            hodler = (MyHodler)convertView.getTag();
        }
        Map<String,Object> map = mList.get(position);
        hodler.img.setImageResource((int)map.get("img"));
        hodler.title.setText((String)map.get("title"));
        hodler.content.setText((String)map.get("info"));
        return convertView;
    }

    class MyHodler{
        public CircleImageView img;
        public TextView title;
        public TextView content;
    }
}
