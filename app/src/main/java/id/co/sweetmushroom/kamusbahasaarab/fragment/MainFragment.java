package id.co.sweetmushroom.kamusbahasaarab.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import id.co.sweetmushroom.kamusbahasaarab.DetailKeterangan;
import id.co.sweetmushroom.kamusbahasaarab.MainActivity;
import id.co.sweetmushroom.kamusbahasaarab.OnBackPressed;
import id.co.sweetmushroom.kamusbahasaarab.R;
import id.co.sweetmushroom.kamusbahasaarab.TapListener.OnTapListener;
import id.co.sweetmushroom.kamusbahasaarab.adapter.MainAdapter;
import id.co.sweetmushroom.kamusbahasaarab.database.DatabaseHelper;
import id.co.sweetmushroom.kamusbahasaarab.item.MainItem;

public class MainFragment extends Fragment{

    private MainAdapter adapter;
    public static MainAdapter adapter2;
    private Toolbar toolbar;
    public static TextView emptyItem;
    public static ArrayList<MainItem> arrayList;
    public static ArrayList<MainItem> arrayList2;
    public ViewGroup viewer;
    public static RecyclerView recyclerView;
    public DatabaseHelper databaseHelper;
    public static EditText searchView;
    public static int codeStat = 0;
    MainActivity ma;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        viewer = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        toolbar = viewer.findViewById(R.id.toolbarMain);

        emptyItem = viewer.findViewById(R.id.emptyItem);

        searchView = viewer.findViewById(R.id.searchText);
        searchView.setImeActionLabel("Cari", KeyEvent.KEYCODE_ENTER);

        searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 0 || event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (searchView.getText().toString().equals("")){
                        return false;
                    } else {
                        searchData(searchView.getText().toString());
                        insertData(searchView.getText().toString());
                        codeStat = 1;
                        return true;
                    }
                } else {
                    return false;
                }
            }
        });

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        if (((AppCompatActivity)getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(null);
        }



        recyclerView = viewer.findViewById(R.id.recycleMain);
        loadData();

        if (arrayList.isEmpty()){
            recyclerView.setVisibility(View.INVISIBLE);
            emptyItem.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyItem.setVisibility(View.INVISIBLE);
        }

        return viewer;
    }

    private static String whitespace(String word){
        String hasilword = null;
        hasilword = String.valueOf(word.charAt(0));

        for (int i=1; i<word.length(); i++){
            if (word.charAt(i) == ' '){
                hasilword = hasilword + String.valueOf('%');
            } else {
                hasilword = hasilword + String.valueOf(word.charAt(i));
            }
        }

        return hasilword;
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

            Cursor cursor = databaseHelper.queryData("SELECT DISTINCT id, title, content,favorite,keterangan FROM indo_arab_2 ORDER BY title");
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

            arrayList2 = arrayList;

        } catch (SQLException e){
            e.printStackTrace();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new MainAdapter(getActivity(), arrayList);

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
                Cursor cursor = databaseHelper.queryData("SELECT DISTINCT id,title,content,favorite,keterangan FROM indo_arab_2 ORDER BY title");

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

                String newResult;

                newResult = whitespace(newText);

                Cursor cursor = databaseHelper.queryData("SELECT DISTINCT id, title, content,favorite,keterangan FROM indo_arab_2 where title like '%"+newResult+"%' or content like '%"+newResult+"%' ORDER BY title");

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

        adapter = new MainAdapter(getActivity(), arrayList);
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

        recyclerView.setAdapter(adapter);


        if (arrayList.isEmpty()){
            recyclerView.setVisibility(View.INVISIBLE);
            emptyItem.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyItem.setVisibility(View.INVISIBLE);
        }

    }


    public void insertData(String charText) {
        databaseHelper = new DatabaseHelper(getActivity());
        try {
            databaseHelper.checkAndCopyDatabase();
            databaseHelper.openDatbase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            db.execSQL("INSERT INTO history(title)values('" +
                    charText +"')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeCode(){
        codeStat = 0;
        searchView.getText().clear();
        this.loadData();
        recyclerView.setVisibility(View.VISIBLE);
        emptyItem.setVisibility(View.INVISIBLE);
    }

}
