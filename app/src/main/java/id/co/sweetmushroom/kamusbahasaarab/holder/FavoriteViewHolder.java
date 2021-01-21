package id.co.sweetmushroom.kamusbahasaarab.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.co.sweetmushroom.kamusbahasaarab.R;

public class FavoriteViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_id_favorite;
    public TextView tv_title_favorite;
    public TextView tv_content_favorite;
    public ImageView iv_copy_fav;
    public ImageView iv_trash__favorite;

    public FavoriteViewHolder(View itemView) {
        super(itemView);
        tv_id_favorite      = itemView.findViewById(R.id.tv_id_fav);
        tv_title_favorite   = itemView.findViewById(R.id.tv_title_fav);
        tv_content_favorite = itemView.findViewById(R.id.tv_content_fav);
        iv_trash__favorite  = itemView.findViewById(R.id.iv_trash_item);
        iv_copy_fav         = itemView.findViewById(R.id.iv_copy_fav);
    }
}

