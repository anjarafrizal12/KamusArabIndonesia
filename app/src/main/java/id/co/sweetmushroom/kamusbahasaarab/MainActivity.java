package id.co.sweetmushroom.kamusbahasaarab;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import id.co.sweetmushroom.kamusbahasaarab.fragment.ContactFragment;
import id.co.sweetmushroom.kamusbahasaarab.fragment.FavoriteFragment;
import id.co.sweetmushroom.kamusbahasaarab.fragment.HistoryFragment;
import id.co.sweetmushroom.kamusbahasaarab.fragment.InfoFragment;
import id.co.sweetmushroom.kamusbahasaarab.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_contact, iv_main, iv_favorite, iv_history, iv_info;
    private ContactFragment contactFragment;
    private MainFragment mainFragment;
    private FavoriteFragment favoriteFragment;
    private InfoFragment infoFragment;
    private HistoryFragment historyFragment;
    private String statusFragment;
    public boolean doubleTapParam = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactFragment = new ContactFragment();
        mainFragment = new MainFragment();
        favoriteFragment = new FavoriteFragment();
        infoFragment = new InfoFragment();
        historyFragment = new HistoryFragment();

        statusFragment = "main";
        setFragment(mainFragment, 1, 1);


        iv_contact = findViewById(R.id.iv_contact);
        iv_main = findViewById(R.id.iv_MainBook);
        iv_favorite = findViewById(R.id.iv_Favorite);
        iv_info = findViewById(R.id.iv_infoPolicy);
        iv_history = findViewById(R.id.iv_History);

        iv_main.setImageResource(R.drawable.ic_main_menu_click);

        iv_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_contact.setImageResource(R.drawable.ic_question_answer_click);
                iv_favorite.setImageResource(R.drawable.ic_star_border);
                iv_main.setImageResource(R.drawable.book1);
                iv_info.setImageResource(R.drawable.ic_info_nonclick);
                iv_history.setImageResource(R.drawable.ic_history_nonclick);
                setFragment(contactFragment, 0, 0);
                statusFragment = "contact";
            }
        });

        iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_contact.setImageResource(R.drawable.ic_question_answer_black_noclick);
                iv_favorite.setImageResource(R.drawable.ic_star_border);
                iv_main.setImageResource(R.drawable.book1);
                iv_info.setImageResource(R.drawable.ic_info_click);
                iv_history.setImageResource(R.drawable.ic_history_nonclick);
                setFragment(infoFragment, 0, 1);
                statusFragment = "policy";
            }
        });

        iv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_contact.setImageResource(R.drawable.ic_question_answer_black_noclick);
                iv_favorite.setImageResource(R.drawable.ic_star_border);
                iv_main.setImageResource(R.drawable.ic_main_menu_click);
                iv_info.setImageResource(R.drawable.ic_info_nonclick);
                iv_history.setImageResource(R.drawable.ic_history_nonclick);
                setFragment(mainFragment, 1, 1);
                statusFragment = "main";

            }
        });

        iv_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_favorite.setImageResource(R.drawable.ic_star_black_click);
                iv_contact.setImageResource(R.drawable.ic_question_answer_black_noclick);
                iv_main.setImageResource(R.drawable.book1);
                iv_info.setImageResource(R.drawable.ic_info_nonclick);
                iv_history.setImageResource(R.drawable.ic_history_nonclick);
                setFragment(favoriteFragment, 1, 2);
                statusFragment = "favorite";
            }
        });

        iv_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_contact.setImageResource(R.drawable.ic_question_answer_black_noclick);
                iv_favorite.setImageResource(R.drawable.ic_star_border);
                iv_main.setImageResource(R.drawable.book1);
                iv_info.setImageResource(R.drawable.ic_info_nonclick);
                iv_history.setImageResource(R.drawable.ic_history_click);
                setFragment(historyFragment, 2, 2);
                statusFragment = "history";
            }
        });

    }

    private void setFragment(Fragment fragment, int value, int value2) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (value == 0 && value2 == 0) {
            if (statusFragment.equals("policy") || statusFragment.equals("main") || statusFragment.equals("favorite") || statusFragment.equals("history")) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left_invers, R.anim.slide_in_rigth_invers);
            }
        } else if (value == 0 && value2 == 1) {

            if (statusFragment.equals("contact")) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left_invers, R.anim.slide_in_rigth_invers);
            }

        } else if (value == 1 && value2 == 1) {
            if (statusFragment.equals("contact") || statusFragment.equals("policy")) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
            } else if (statusFragment.equals("favorite") || statusFragment.equals("history")){
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left_invers, R.anim.slide_in_rigth_invers);
            }
        } else if (value == 1 && value2 == 2) {

            if (statusFragment.equals("history")){
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left_invers, R.anim.slide_in_rigth_invers);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
            }
        } else {
            if (statusFragment.equals("policy") || statusFragment.equals("main") || statusFragment.equals("favorite") || statusFragment.equals("contact")) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
            }
        }

        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (MainFragment.codeStat == 1){
            MainFragment.searchView.clearFocus();
            mainFragment.changeCode();
        } else {
            if (doubleTapParam) {
                super.onBackPressed();
                return;
            }

            this.doubleTapParam = true;
            Toast.makeText(this, "tekan sekali lagi untuk keluar aplikasi", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleTapParam = false;
                }
            }, 2000);
        }
    }



}
