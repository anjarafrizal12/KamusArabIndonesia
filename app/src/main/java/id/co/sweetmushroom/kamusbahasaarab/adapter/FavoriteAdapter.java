package id.co.sweetmushroom.kamusbahasaarab.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import id.co.sweetmushroom.kamusbahasaarab.R;
import id.co.sweetmushroom.kamusbahasaarab.TapListener.OnTapListener;
import id.co.sweetmushroom.kamusbahasaarab.database.DatabaseHelper;
import id.co.sweetmushroom.kamusbahasaarab.fragment.FavoriteFragment;
import id.co.sweetmushroom.kamusbahasaarab.fragment.MainFragment;
import id.co.sweetmushroom.kamusbahasaarab.holder.FavoriteViewHolder;
import id.co.sweetmushroom.kamusbahasaarab.item.MainItem;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    private Activity activity;
    private ArrayList<MainItem> arrayList;
    private ArrayList<MainItem> arrayListCopy;
    private ArrayList<MainItem> items;
    private Context mContext;
    public DatabaseHelper databaseHelper;
    private OnTapListener onTapListener;

    public FavoriteAdapter(Activity activity, ArrayList<MainItem> items){
        this.activity = activity;
        this.items = items;
        this.arrayList = new ArrayList<MainItem>();
        this.arrayList.addAll(FavoriteFragment.arrayList);
        this.mContext = activity;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclefav_item,parent,false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FavoriteViewHolder holder, final int position) {

        int num = Integer.parseInt(items.get(position).getNo());

        NumberFormat nf = NumberFormat.getInstance(new Locale("ar","EG"));

        holder.tv_id_favorite.setText(nf.format(num));
        holder.tv_title_favorite.setText(items.get(position).getTitle());
        holder.tv_content_favorite.setText(items.get(position).getContent());

        holder.iv_trash__favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData(position);
                FavoriteFragment.ma.loadData();

            }
        });

        holder.iv_copy_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copy", items.get(position).getContent());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext,"Copy to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTapListener != null) {
                    onTapListener.OnTapView(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void filter(String charText) {


        charText = charText.toLowerCase(Locale.getDefault());
        FavoriteFragment.arrayList.clear();
        if (charText.length() == 0) {
            FavoriteFragment.arrayList.addAll(arrayList);
        } else {
            for (MainItem wp : arrayList) {
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText) || wp.getContent().toLowerCase(Locale.getDefault()).contains(charText)) {
                    FavoriteFragment.arrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();

        if (FavoriteFragment.arrayList.size() == 0){
            FavoriteFragment.emptyItem.setVisibility(View.VISIBLE);
            FavoriteFragment.recyclerView.setVisibility(View.INVISIBLE);
        } else {
            FavoriteFragment.emptyItem.setVisibility(View.INVISIBLE);
            FavoriteFragment.recyclerView.setVisibility(View.VISIBLE);
        }
    }

    public void editData(final int position) {
        databaseHelper = new DatabaseHelper(mContext);
        try {
            databaseHelper.checkAndCopyDatabase();
            databaseHelper.openDatbase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            db.execSQL("UPDATE indo_arab_2 SET favorite='0' WHERE id='"+items.get(position).getId()+"'");
            Toast.makeText(mContext, "Berhasil di hapus dari favorite", Toast.LENGTH_SHORT).show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setOnTapListener(OnTapListener onTapListener){
        this.onTapListener = onTapListener;
    }
}
