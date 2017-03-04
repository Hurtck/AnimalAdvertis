package animaladvertis.com.animaladvertis.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.platform.comapi.map.D;
import com.john.waveview.WaveView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.R;
import animaladvertis.com.animaladvertis.beans.Animal;

/**
 * Created by 47321 on 2016/12/13 0013.
 */

public class CollecRecycleViewAdapter extends RecyclerView.Adapter<CollecdesHodler> {

    private static final int TYPE_HEAD=0;
    private static final int TYPE_NORMAL=1;
    private static final int TYPE_EVA = 2;
    private static final int TYPE_TITLE = 3;

    private int mProgress,mSrc;

    private LruCache<String,Bitmap> mMemoryCache;
    private List<Map<String,Object>> mList;
    private List<Animal> mAnimals;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public CollecRecycleViewAdapter(List<Animal> animals,int progress,int src){
        if(animals==null){

        }
        int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        int cacheSize = maxMemory/8;
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };

        mProgress = progress;
        mSrc = src;

        mAnimals = animals;

        mList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("userName","asd");
        map.put("time","2016-9-46");
        map.put("content","有谁收集好了");
        mList.add(map);

        map = new HashMap<>();
        map.put("userName","asd");
        map.put("time","2016-9-46");
        map.put("content","好厉害");
        mList.add(map);

        map = new HashMap<>();
        map.put("userName","asd");
        map.put("time","2016-9-46");
        map.put("content","想去看电影");
        mList.add(map);

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return TYPE_HEAD;
        if(position>mAnimals.size()+1) return TYPE_EVA;
        if(position==mAnimals.size()+1) return TYPE_TITLE;
        else return TYPE_NORMAL;
    }

    @Override
    public CollecdesHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        mContext = parent.getContext();
        if(viewType==TYPE_NORMAL){
            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collectdescrit
                    ,parent,false);
        }
        if(viewType==TYPE_HEAD){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collectdescrit_head
                    ,parent,false);
            return new CollecdesHodler(itemView,TYPE_HEAD);
        }
        if(viewType==TYPE_EVA){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation
                    ,parent,false);
            return new EvaHodler(itemView);
        }
        if(viewType==TYPE_TITLE){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evatille
                    ,parent,false);
            return new EvtitleHodler(itemView);
        }
        return new CollecdesHodler(itemView,TYPE_NORMAL);
    }

    @Override
    public void onBindViewHolder(final CollecdesHodler holder, final int position) {
        Log.d("T","Title"+holder.getType());
        if(holder.getType()==TYPE_HEAD){
            Log.d("T","head");
            holder.getWaveView().setProgress(mProgress);
           /* InputStream is = mContext.getResources().openRawResource(mSrc);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inSampleSize = 2;
            Bitmap btp = BitmapFactory.decodeStream(is,null,options);*/
            loadBitmap(mSrc,holder.getWaveView());
            //holder.getWaveView().setBackgroundResource(mSrc);
        }
        if(holder.getType()==TYPE_NORMAL){
            int mposition = position-1;
            int nSrc = mAnimals.get(mposition).getPicSrc();

            holder.getIv_image().setImageResource(nSrc);

            String location = mAnimals.get(mposition).getHomeLocation();
            String sell = mAnimals.get(mposition).getSell();
            String titel = mAnimals.get(mposition).getTitle();
            holder.getTv_position().setText(location);
            holder.getTv_sell().setText(sell);
            holder.getTv_title().setText(titel);
            holder.getTv_gotohome().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,holder.getLayoutPosition());
                }
            });

            holder.getIv_image().setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v,holder.getLayoutPosition());
                }
            });
        }
        if(holder.getType()==TYPE_TITLE){
            Log.d("T","Title");
        }
        if(holder.getType()==TYPE_EVA){//设置评论数据
            int mpositon  = position - mAnimals.size()-2;
            ((EvaHodler)holder).getUserName().setText("hahah");
            ((EvaHodler)holder).getTime().setText("2016-12-15");
            ((EvaHodler)holder).getUserPhoto().setImageResource(R.drawable.pig);
            ((EvaHodler)holder).getContent().setText("WERWE");
        }

    }


    @Override
    public void onViewAttachedToWindow(CollecdesHodler holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null&& lp instanceof StaggeredGridLayoutManager.LayoutParams){
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getLayoutPosition() == 0);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEAD?gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mAnimals.size()+mList.size()+2;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if(mMemoryCache.get(key)==null){
            mMemoryCache.put(key, bitmap);
        }
    }

    public void loadBitmap(int resID,WaveView wave){
        final String imageKey = String.valueOf(resID);
        final Bitmap bitmap = mMemoryCache.get(imageKey);
        if(bitmap!=null){
            wave.setBackground(new BitmapDrawable(bitmap));
        }else{
            wave.setBackground(new BitmapDrawable());
            BitmapWorkerTask task = new BitmapWorkerTask();
            task.execute(resID);
        }
    }

    public void loadBitmapimg(int resID,ImageView img){
        final String imageKey = String.valueOf(resID);
        final Bitmap bitmap = mMemoryCache.get(imageKey);
        if(bitmap!=null){
            img.setImageBitmap(bitmap);
        }else{
            img.setImageBitmap(bitmap);
            BitmapWorkerTask task = new BitmapWorkerTask();
            task.execute(resID);
        }
    }

    class BitmapWorkerTask extends AsyncTask<Integer,Void,Bitmap> {
        @Override
        protected Bitmap doInBackground(Integer... params) {
            InputStream is = mContext.getResources().openRawResource(params[0]);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inSampleSize = 1;
            final Bitmap bitmap = BitmapFactory.decodeStream(is,null,options);
            addBitmapToMemoryCache(String.valueOf(params[0]),bitmap);
            return bitmap;
        }
    }

}
