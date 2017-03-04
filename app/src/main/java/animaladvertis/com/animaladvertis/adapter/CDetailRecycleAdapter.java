package animaladvertis.com.animaladvertis.adapter;

import android.graphics.Bitmap;
import android.preference.PreferenceActivity;
import android.support.v4.util.LruCache;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import animaladvertis.com.animaladvertis.R;

import static android.R.attr.bitmap;
import static android.R.attr.key;

/**
 * Created by 47321 on 2016/12/21 0021.
 */

public class CDetailRecycleAdapter extends RecyclerView.Adapter<CDetailHodler> {
    private static final int TYPE_HEAD=0;
    private static final int TYPE_NORMAL=1;

    private List<Map<String,Object>> mList;


    public CDetailRecycleAdapter(List<Map<String,Object>> list){
        mList = list;

    }



    @Override
    public int getItemViewType(int position) {
        if(position==0) return TYPE_HEAD;
        else return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public CDetailHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if(viewType== TYPE_HEAD){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_head_cdetail,parent,false);
            return new CDetailHodler(itemView,TYPE_HEAD);
        }
        if(viewType==TYPE_NORMAL){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluation,parent,false);
        }
        return new CDetailHodler(itemView,TYPE_NORMAL);
    }

    @Override
    public void onBindViewHolder(CDetailHodler holder, int position) {
        if(holder.getType()==TYPE_HEAD){
            holder.getUserPhoto().setImageResource(R.drawable.pig);
            holder.getStar().setRating(3.5f);
        }
        if(holder.getType()==TYPE_NORMAL){
            String time = (String) mList.get(position-1).get("time");
            String userName = (String) mList.get(position-1).get("userName");
            String content = (String) mList.get(position-1).get("content");
            holder.getUserPhoto().setImageResource(R.drawable.pig);
            holder.getTime().setText(time);
            holder.getUserName().setText(userName);
            holder.getContent().setText(content);
        }
    }

    @Override
    public void onViewAttachedToWindow(CDetailHodler holder) {
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


}
