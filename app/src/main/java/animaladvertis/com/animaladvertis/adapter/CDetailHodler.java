package animaladvertis.com.animaladvertis.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import animaladvertis.com.animaladvertis.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 47321 on 2016/12/21 0021.
 */

public class CDetailHodler extends RecyclerView.ViewHolder {
    private int type;
    private CircleImageView userPhoto;
    private TextView userName;
    private TextView time;
    private TextView content;
    private RatingBar star;

    public int getType() {
        return type;
    }

    public CircleImageView getUserPhoto() {
        return userPhoto;
    }

    public TextView getUserName() {
        return userName;
    }

    public TextView getTime() {
        return time;
    }

    public TextView getContent() {
        return content;
    }

    public RatingBar getStar() {
        return star;
    }

    public CDetailHodler(View itemView, int type){
        super(itemView);
        this.type = type;
        if(type==0){
            userPhoto = (CircleImageView) itemView.findViewById(R.id.cv_item_cdtail_photo);
            star = (RatingBar) itemView.findViewById(R.id.rb_item_cdtail_star);
        }
        if(type==1){
            userPhoto = (CircleImageView) itemView.findViewById(R.id.cv_item_collecdes_userphoto);
            userName = (TextView) itemView.findViewById(R.id.tv_item_collecdes_username);
            time = (TextView) itemView.findViewById(R.id.tv_item_collecdes_time);
            content = (TextView) itemView.findViewById(R.id.tv_item_collecdes_content);
        }
    }

}
