package id.co.sweetmushroom.kamusbahasaarab.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.co.sweetmushroom.kamusbahasaarab.R;
import id.co.sweetmushroom.kamusbahasaarab.database.DatabaseHelper;
import id.co.sweetmushroom.kamusbahasaarab.holder.HistoryViewHolder;
import id.co.sweetmushroom.kamusbahasaarab.item.MainItem;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    private Activity activity;
    private ArrayList<MainItem> items;
    private Context mContext;
    public DatabaseHelper databaseHelper;

    public HistoryAdapter(Activity activity, ArrayList<MainItem> items){
        this.activity = activity;
        this.items = items;
        this.mContext = activity;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder holder, final int position) {
        holder.tv_title_history.setText(items.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
