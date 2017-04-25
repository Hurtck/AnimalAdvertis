package animaladvertis.com.animaladvertis.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.john.waveview.WaveView;

import animaladvertis.com.animaladvertis.R;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.id;

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
    private CircleImageView shop;
    private TextView tv_name;

    public TextView getTv_name() {
        return tv_name;
    }

    public void setTv_name(TextView tv_name) {
        this.tv_name = tv_name;
    }

    public TextView getShopName() {
        return shopName;
    }

    public void setShopName(TextView shopName) {
        this.shopName = shopName;
    }

    public CircleImageView getShop() {
        return shop;
    }

    public void setShop(CircleImageView shop) {
        this.shop = shop;
    }

    private TextView shopName;


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
            shop = (CircleImageView) itemView.findViewById(R.id.cv_item_collecdes_imag);
            shopName = (TextView) itemView.findViewById(R.id.tv_item_collecdes_shopname);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
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
