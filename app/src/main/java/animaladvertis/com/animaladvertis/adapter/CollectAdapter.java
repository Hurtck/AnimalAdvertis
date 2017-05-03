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
import static animaladvertis.com.animaladvertis.R.id.imageView;
import static animaladvertis.com.animaladvertis.R.id.map;
import static animaladvertis.com.animaladvertis.R.id.view_offset_helper;
import static com.baidu.location.h.j.p;

/**
 * Created by 47321 on 2016/12/12 0012.
 */


public class CollectAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String, Object>> mList;
    private LayoutInflater mInflater = null;
    private ViewHodler hodler;


    public CollectAdapter(Context context, List<Map<String, Object>> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
        Log.d("ColloectAdpterMSG"," "+mList.size());

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
        Map<String, Object> map = mList.get(position);
        BmobFile src = (BmobFile) map.get("src");

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_collect, null);
            hodler = new ViewHodler();
            hodler.tv_kind = (TextView) convertView.findViewById(R.id.tv_kind_item_collect);
            hodler.pg_progress = (NumberProgressBar) convertView.findViewById(R.id.pg_progress_item_collect);
            hodler.im_imag = (ImageView) convertView.findViewById(R.id.im_item_img);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler)convertView.getTag();
        }
        hodler.tv_kind.setText((String) map.get("name"));
        hodler.pg_progress.setProgress((int) map.get("number"));

        LoadImageUtil.loadIMage(mContext, hodler.im_imag, src.getFileUrl(),1);
        return convertView;
    }
}
