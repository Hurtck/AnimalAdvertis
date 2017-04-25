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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.john.waveview.WaveView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.R;
import animaladvertis.com.animaladvertis.beans.Animal;
import animaladvertis.com.animaladvertis.util.LoadImageUtil;
import cn.bmob.v3.datatype.BmobFile;

import static android.R.attr.resource;
import static com.baidu.location.h.a.h;
import static com.baidu.location.h.j.am;

/**
 * Created by 47321 on 2016/12/13 0013.
 */

public class CollecRecycleViewAdapter extends RecyclerView.Adapter<CollecdesHodler> {

    private static final int TYPE_HEAD=0;
    private static final int TYPE_NORMAL=1;

    private int mProgress;
    private BmobFile mSrc;
    private List<Animal> mAnimals;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public CollecRecycleViewAdapter(List<Animal> animals,int progress,BmobFile src){

        mProgress = progress;
        mSrc = src;
        mAnimals = animals;

    }

    @Override
    public int getItemViewType(int position) {
        if(position==0) return TYPE_HEAD;
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
        return new CollecdesHodler(itemView,TYPE_NORMAL);
    }

    @Override
    public void onBindViewHolder(final CollecdesHodler holder, final int position) {
        if(holder.getType()==TYPE_HEAD){
            Log.d("TTitle","head");
            holder.getWaveView().setProgress(mProgress);
            Glide.with(mContext).load(mSrc.getUrl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    holder.getWaveView().setBackground(new BitmapDrawable(resource));
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    holder.getWaveView().setBackground(errorDrawable);
                }
            });
        }
        if(holder.getType()==TYPE_NORMAL){
            int mposition = position-1;
            Animal animal = mAnimals.get(mposition);
            LoadImageUtil.loadIMage(mContext,holder.getIv_image(),animal.getPicture().getFileUrl(),1,0.2f);

            Long str = System.currentTimeMillis();
            Log.d("Currenttime4",""+str);
            if(animal.isTag()) holder.getTv_title().setVisibility(View.INVISIBLE);
            else {
                holder.getTv_title().setVisibility(View.VISIBLE);
                holder.getTv_title().setText("未收集");
            }

            String location = animal.getLocationname();
            int score = animal.getScore();
            String shopName = animal.getMerchantName();
            holder.getTv_position().setText(location);
            holder.getTv_sell().setText("积分 "+score);
            holder.getTv_name().setText(animal.getName());
            BmobFile nSrc = animal.getShop();
            LoadImageUtil.loadIMage(mContext,holder.getShop(),nSrc.getFileUrl(),1,0.2f);
            holder.getShopName().setText(shopName);
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
        return mAnimals.size()+1;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
