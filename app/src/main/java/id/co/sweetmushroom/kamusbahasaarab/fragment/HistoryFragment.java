package id.co.sweetmushroom.kamusbahasaarab.fragment;


import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.co.sweetmushroom.kamusbahasaarab.R;
import id.co.sweetmushroom.kamusbahasaarab.adapter.FavoriteAdapter;
import id.co.sweetmushroom.kamusbahasaarab.adapter.HistoryAdapter;
import id.co.sweetmushroom.kamusbahasaarab.database.DatabaseHelper;
import id.co.sweetmushroom.kamusbahasaarab.item.MainItem;

public class HistoryFragment extends Fragment {

    private HistoryAdapter adapter;
    private Toolbar toolbar;
    public static ArrayList<MainItem> arrayList;
    public static TextView emptyItem;
    public ViewGroup viewer;
    public static RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    public static FavoriteFragment ma;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        viewer = (ViewGroup) inflater.inflate(R.layout.fragment_history, container, false);
        toolbar = viewer.findViewById(R.id.toolbarHistory);

        toolbar.setTitleTextColor(0xFFFFFFFF);

        emptyItem = viewer.findViewById(R.id.emptyItemHis);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity)getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("مفضل");
            ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.drawable.ic_history_click_appbar);
        }


        recyclerView = viewer.findViewById(R.id.recycleHis);
        loadData();

        return viewer;
    }

    public void loadData() {

        arrayList = new ArrayList<MainItem>();

        databaseHelper = new DatabaseHelper(getActivity());
        try {
            databaseHelper.checkAndCopyDatabase();
            databaseHelper.openDatbase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            int i = 1;

            Cursor cursor = databaseHelper.queryData("select * from history ORDER BY id DESC");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        MainItem item = new MainItem();
                        item.setNo(Integer.toString(i));
                        item.setId(cursor.getString(0));
                        item.setTitle(cursor.getString(1));
                        item.setContent(cursor.getString(2));
                        item.setFavorite(cursor.getString(3));
                        arrayList.add(item);
                        i = i + 1;
                    } while (cursor.moveToNext());
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new HistoryAdapter(getActivity(), arrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        adapter.notifyDataSetChanged();

        if (arrayList.size() == 0){
            emptyItem.setText("No Item");
            emptyItem.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            emptyItem.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_history, menu);

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (item.getItemId() == R.id.action_empty){

            if (arrayList.size() == 0){
                Toast.makeText(getActivity(), "Data History Kosong", Toast.LENGTH_SHORT).show();
            } else {
                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // Set a title for alert dialog
                builder.setTitle("Empty History");

                // Ask the final question
                builder.setMessage("Are you sure to empty history item?");

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when user clicked the Yes button
                        // Set the TextView visibility GONE

                        databaseHelper = new DatabaseHelper(getActivity());
                        try {
                            databaseHelper.checkAndCopyDatabase();
                            databaseHelper.openDatbase();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        try {
                            SQLiteDatabase db = databaseHelper.getReadableDatabase();

                            db.execSQL("DELETE FROM history");
                            loadData();
                            Toast.makeText(getActivity(), "Berhasil mengosongkan history", Toast.LENGTH_SHORT).show();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when No button clicked

                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }

        }
        return true;
    }

    private void setItemVisibility(Menu menu, MenuItem exception, boolean visible){
        for (int i=0; i<menu.size(); i++){
            MenuItem item = menu.getItem(i);
            if (item != exception) {
                item.setVisible(visible);
            }
        }
    }

}
