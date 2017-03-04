package animaladvertis.com.animaladvertis.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.john.waveview.WaveView;

import animaladvertis.com.animaladvertis.R;

/**
 * Created by 47321 on 2016/12/13 0013.
 */

public class CollecdesHodler extends RecyclerView.ViewHolder{
    private ImageView iv_image;
    private TextView tv_sell;
    private TextView tv_position;
    private int type;
    private TextView tv_title;
    private TextView tv_gotohome;
    private WaveView waveView;

    public TextView getTv_title() {
        return tv_title;
    }

    public int getType() {
        return type;
    }

    public TextView getTv_gotohome() {
        return tv_gotohome;
    }

    public WaveView getWaveView() {
        return waveView;
    }

    public CollecdesHodler(View itemView, int type){
        super(itemView);
        this.type=-1;
        if(type==1){
            this.type=1;
            iv_image = (ImageView) itemView.findViewById(R.id.iv_item_pictrue);
            tv_sell = (TextView) itemView.findViewById(R.id.tv_item_collecdes_sell);
            tv_position = (TextView) itemView.findViewById(R.id.tv_item_collecdes_time);
            tv_title = (TextView) itemView.findViewById(R.id.tv_item_collectdes_title);
            tv_gotohome = (TextView) itemView.findViewById(R.id.tv_gotohome);
        }
        if(type==0){
            this.type=0;
            waveView = (WaveView) itemView.findViewById(R.id.vw_item_progress);
        }

    }



    public ImageView getIv_image() {
        return iv_image;
    }


    public TextView getTv_sell() {
        return tv_sell;
    }


    public TextView getTv_position() {
        return tv_position;
    }

}
