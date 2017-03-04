package animaladvertis.com.animaladvertis.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import animaladvertis.com.animaladvertis.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 47321 on 2016/12/15 0015.
 */

public class EvaHodler extends CollecdesHodler {
    private CircleImageView userPhoto;
    private TextView userName;
    private TextView time;
    private TextView content;

    public EvaHodler (View itemView){
        super(itemView,2);
        userPhoto = (CircleImageView) itemView.findViewById(R.id.cv_item_collecdes_userphoto);
        userName = (TextView) itemView.findViewById(R.id.tv_item_collecdes_username);
        time = (TextView) itemView.findViewById(R.id.tv_item_collecdes_time);
        content = (TextView) itemView.findViewById(R.id.tv_item_collecdes_content);
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

}
