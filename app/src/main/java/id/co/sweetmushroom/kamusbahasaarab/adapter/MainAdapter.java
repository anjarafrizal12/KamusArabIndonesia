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
import id.co.sweetmushroom.kamusbahasaarab.fragment.MainFragment;
import id.co.sweetmushroom.kamusbahasaarab.holder.MainViewHolder;
import id.co.sweetmushroom.kamusbahasaarab.item.MainItem;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private Activity activity;
    private ArrayList<MainItem> arrayList;
    private ArrayList<MainItem> arrayListCopy;
    private ArrayList<MainItem> items;
    private Context mContext;
    public DatabaseHelper databaseHelper;
    private OnTapListener onTapListener;

    public MainAdapter(Activity activity, ArrayList<MainItem> items){
        this.activity = activity;
        this.items = items;
        this.arrayList = new ArrayList<MainItem>();
        this.arrayList.addAll(MainFragment.arrayList);
        this.mContext = activity;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclemain_item,parent,false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainViewHolder holder, final int position) {

        int num = Integer.parseInt(items.get(position).getNo());

        NumberFormat nf = NumberFormat.getInstance(new Locale("ar","EG"));

        holder.tv_id.setText(nf.format(num));
        holder.tv_title.setText(items.get(position).getTitle());
        holder.tv_content.setText(items.get(position).getContent());

        holder.iv_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.iv_favorite.setImageResource(R.drawable.ic_star_click);
                editData(position);
            }
        });

        if (items.get(position).getFavorite().equals("0")){
            holder.iv_favorite.setImageResource(R.drawable.ic_star);
       } else {
            holder.iv_favorite.setImageResource(R.drawable.ic_star_click);
        }

        holder.iv_copy.setOnClickListener(new View.OnClickListener() {
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

    public void filterQuery(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());
        MainFragment.arrayList.clear();
        if (charText.length() == 0) {
            MainFragment.arrayList.addAll(arrayList);
        } else {
            for (MainItem wp : arrayList) {
                //Toast.makeText(mContext,wp.getTitle(), Toast.LENGTH_SHORT).show();
                if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText) || wp.getContent().toLowerCase(Locale.getDefault()).contains(charText)) {
                    MainFragment.arrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();

        if (MainFragment.arrayList.size() == 0){
            MainFragment.emptyItem.setVisibility(View.VISIBLE);
            MainFragment.recyclerView.setVisibility(View.INVISIBLE);
        } else {
            MainFragment.emptyItem.setVisibility(View.INVISIBLE);
            MainFragment.recyclerView.setVisibility(View.VISIBLE);
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

            db.execSQL("UPDATE indo_arab_2 SET favorite='1' WHERE id='"+items.get(position).getId()+"'");
            Toast.makeText(mContext, "Telah di tambahkan di favorite", Toast.LENGTH_SHORT).show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setOnTapListener(OnTapListener onTapListener){
        this.onTapListener = onTapListener;
    }
}
