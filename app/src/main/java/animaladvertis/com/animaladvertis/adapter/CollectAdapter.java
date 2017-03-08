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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.R;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import cn.bmob.v3.datatype.BmobFile;

import static android.content.ContentValues.TAG;

/**
 * Created by 47321 on 2016/12/12 0012.
 */


public class CollectAdapter extends BaseAdapter {
    private Context mContext;
    private List<Map<String,Object>> mList;
    private LayoutInflater mInflater = null;
    private LruCache<String,Bitmap> mMemoryCache;
    private ViewHodler hodler;

    public CollectAdapter(Context context, List<Map<String,Object>> list){
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
        int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        int cacheSize = maxMemory/8;
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
    }

    public void loadBitmap(int resID,ImageView imageView){
        final String imageKey = String.valueOf(resID);
        final Bitmap bitmap = mMemoryCache.get(imageKey);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setImageResource(R.drawable.collec);
            BitmapWorkerTask task = new BitmapWorkerTask();
            task.execute(resID);
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if(mMemoryCache.get(key)==null){
            mMemoryCache.put(key, bitmap);
        }
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
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.item_collect,null);
            hodler = new ViewHodler();
            hodler.tv_kind = (TextView) convertView.findViewById(R.id.tv_kind_item_collect);
            hodler.pg_progress = (NumberProgressBar) convertView.findViewById(R.id.pg_progress_item_collect);
            hodler.im_imag = (ImageView) convertView.findViewById(R.id.im_item_img);
            convertView.setTag(hodler);
        }else{
            hodler = (ViewHodler)convertView.getTag();
        }
        Map<String,Object> map = mList.get(position);
        String kind = (String) map.get("kind");
        int progress =(int) map.get("number");
        BmobFile src = (BmobFile) map.get("src");
        hodler.tv_kind.setText(kind);
        hodler.pg_progress.setProgress(progress);
        LoadImageUtil.loadIMage(mContext,hodler.im_imag,src.getFileUrl(),1);
        return convertView;
    }
    class BitmapWorkerTask extends AsyncTask<Integer,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(Integer... params) {
            InputStream is = mContext.getResources().openRawResource(params[0]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            //options.inSampleSize = 5;
            final Bitmap bitmap = BitmapFactory.decodeStream(is,null,options);
            addBitmapToMemoryCache(String.valueOf(params[0]),bitmap);
            return bitmap;
        }
    }
}
