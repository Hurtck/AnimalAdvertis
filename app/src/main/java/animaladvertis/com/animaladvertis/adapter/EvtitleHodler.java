package animaladvertis.com.animaladvertis.adapter;

import android.view.View;
import android.widget.TextView;

import animaladvertis.com.animaladvertis.R;

/**
 * Created by 47321 on 2016/12/15 0015.
 */

public class EvtitleHodler extends CollecdesHodler {

    private TextView title;

    public TextView getTitle() {
        return title;
    }

    public EvtitleHodler(View itemView){
        super(itemView,3);
        title = (TextView)itemView.findViewById(R.id.tv_collectdes_usernum);
    }
}
