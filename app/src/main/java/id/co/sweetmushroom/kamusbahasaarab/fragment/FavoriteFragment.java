package id.co.sweetmushroom.kamusbahasaarab.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
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

import id.co.sweetmushroom.kamusbahasaarab.DetailKeterangan;
import id.co.sweetmushroom.kamusbahasaarab.R;
import id.co.sweetmushroom.kamusbahasaarab.TapListener.OnTapListener;
import id.co.sweetmushroom.kamusbahasaarab.adapter.FavoriteAdapter;
import id.co.sweetmushroom.kamusbahasaarab.adapter.MainAdapter;
import id.co.sweetmushroom.kamusbahasaarab.database.DatabaseHelper;
import id.co.sweetmushroom.kamusbahasaarab.item.MainItem;

public class FavoriteFragment extends Fragment {

    private FavoriteAdapter adapter;
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
        viewer = (ViewGroup) inflater.inflate(R.layout.fragment_favorite, container, false);
        toolbar = viewer.findViewById(R.id.toolbarFavorite);

        toolbar.setTitleTextColor(0xFFFFFFFF);

        emptyItem = viewer.findViewById(R.id.emptyItemFav);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity)getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("مفضل");
            ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.drawable.ic_star_click);
        }


        ma = this;

        recyclerView = viewer.findViewById(R.id.recycleFav);
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

            Cursor cursor = databaseHelper.queryData("select * from indo_arab_2 where favorite='1'");
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
        adapter = new FavoriteAdapter(getActivity(), arrayList);

        adapter.setOnTapListener(new OnTapListener() {
            @Override
            public void OnTapView(int position) {
                Intent i = new Intent(getContext(),DetailKeterangan.class);
                i.putExtra("indo",arrayList.get(position).getTitle());
                i.putExtra("arab",arrayList.get(position).getContent());
                i.putExtra("keterangan", arrayList.get(position).getKeterangan());
                startActivity(i);
            }
        });

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

    private void searchData(String newText) {

        arrayList.clear();

        databaseHelper = new DatabaseHelper(getActivity());
        try {
            databaseHelper.checkAndCopyDatabase();
            databaseHelper.openDatbase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            int i = 1;

            if (newText.length() == 0){
                Cursor cursor = databaseHelper.queryData("select * from indo_arab_2 where favorite='1'");

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
            } else {
                Cursor cursor = databaseHelper.queryData("select * from indo_arab_2 where (title like '%"+newText+"%' and favorite='1') or (content like '%"+newText+"%' and favorite='1') ORDER BY id");

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
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        adapter = new FavoriteAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);


        if (arrayList.isEmpty()){
            recyclerView.setVisibility(View.INVISIBLE);
            emptyItem.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyItem.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favorite, menu);

        final MenuItem searchMenu   = menu.findItem(R.id.action_search);
        final MenuItem emptyMenu    = menu.findItem(R.id.action_empty);
        final SearchView searchView = (SearchView) searchMenu.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchMenu.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                setItemsVisibility(menu, searchMenu, false);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        searchData(query);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                setItemsVisibility(menu, searchMenu, true);
                searchData("");
                return true;
            }
        });

        emptyMenu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (arrayList.size() == 0){
                    Toast.makeText(getActivity(), "Data Favorite Kosong", Toast.LENGTH_SHORT).show();
                } else {

                    // Build an AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    // Set a title for alert dialog
                    builder.setTitle("Empty Favorite");

                    // Ask the final question
                    builder.setMessage("Are you sure to empty favorite item?");

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

                                db.execSQL("UPDATE indo_arab_2 SET favorite='0'");
                                loadData();
                                Toast.makeText(getActivity(), "Berhasil mengosongkan favorite", Toast.LENGTH_SHORT).show();

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

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        return false;
    }

    private void setItemVisibility(Menu menu, MenuItem exception, boolean visible){
        for (int i=0; i<menu.size(); i++){
            MenuItem item = menu.getItem(i);
            if (item != exception) {
                item.setVisible(visible);
            }
        }
    }

    private void setItemsVisibility(final Menu menu, final MenuItem exception,
                                    final boolean visible) {
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception)
                item.setVisible(visible);
        }
    }

    public void clearFavorite() {
        databaseHelper = new DatabaseHelper(getActivity());
        try {
            databaseHelper.checkAndCopyDatabase();
            databaseHelper.openDatbase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            db.execSQL("UPDATE indo_arab SET favorite='0'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}