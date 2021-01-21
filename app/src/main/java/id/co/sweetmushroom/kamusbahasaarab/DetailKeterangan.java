package id.co.sweetmushroom.kamusbahasaarab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailKeterangan extends AppCompatActivity {

    private TextView arabText, indoText, keterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_keterangan);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Keterangan");
            }

        arabText = findViewById(R.id.arabText);
        indoText = findViewById(R.id.indoText);

        indoText.setText(getIntent().getStringExtra("indo"));
        arabText.setText(getIntent().getStringExtra("arab"));
        //keterangan.setText(getIntent().getStringExtra("keterangan"));

    }

    public void onBackPressed(){
        finish();
    }
}
