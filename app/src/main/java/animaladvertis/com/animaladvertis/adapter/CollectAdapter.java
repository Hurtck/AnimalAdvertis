package animaladvertis.com.animaladvertis.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.R;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import cn.bmob.v3.datatype.BmobFile;

import static android.R.attr.tag;
import static android.content.ContentValues.TAG;
import static animaladvertis.com.animaladvertis.R.drawable.list;
import static animaladvertis.com.animaladvertis.R.drawable.mission;
import static animaladvertis.com.animaladvertis.R.id.map;
import static com.baidu.location.h.j.p;

/**
 * Created by 47321 on 2016/12/12 0012.
 */


public class CollectAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String, Object>> mList;
    private LayoutInflater mInflater = null;

    public CollectAdapter(Context context, List<Map<String, Object>> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View tView = null;
        Map<String, Object> map = mList.get(position);
        int progress = (int) map.get("number");
        String kind = (String) map.get("name");
        BmobFile src = (BmobFile) map.get("src");

        if (convertView == null) {
            tView = mInflater.inflate(R.layout.item_collect, null);
        } else {
            tView = convertView;
        }
        TextView tv_kind = (TextView) tView.findViewById(R.id.tv_kind_item_collect);
        NumberProgressBar pg_progress = (NumberProgressBar) tView.findViewById(R.id.pg_progress_item_collect);
        ImageView im_imag = (ImageView) tView.findViewById(R.id.im_item_img);

        tv_kind.setText(kind + "");
        pg_progress.setProgress(progress);
        LoadImageUtil.loadIMage(mContext, im_imag, src.getFileUrl(), 1);
        return tView;
    }
}
