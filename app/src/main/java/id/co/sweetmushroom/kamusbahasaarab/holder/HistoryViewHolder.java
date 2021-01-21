package id.co.sweetmushroom.kamusbahasaarab.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import id.co.sweetmushroom.kamusbahasaarab.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_title_history;

    public HistoryViewHolder(View itemView) {
        super(itemView);
        tv_title_history = itemView.findViewById(R.id.tv_title_history);
    }
}
