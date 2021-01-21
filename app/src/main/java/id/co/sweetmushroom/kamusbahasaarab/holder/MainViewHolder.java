package id.co.sweetmushroom.kamusbahasaarab.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.co.sweetmushroom.kamusbahasaarab.R;

public class MainViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_id;
    public TextView tv_title;
    public TextView tv_content;
    public ImageView iv_favorite;
    public ImageView iv_copy;

    public MainViewHolder(View itemView) {
        super(itemView);
        tv_id       = itemView.findViewById(R.id.tv_id);
        tv_title    = itemView.findViewById(R.id.tv_title);
        tv_content  = itemView.findViewById(R.id.tv_content);
        iv_favorite = itemView.findViewById(R.id.iv_favorite);
        iv_copy     = itemView.findViewById(R.id.iv_copy);
    }
}
