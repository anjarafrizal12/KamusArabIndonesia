package id.co.sweetmushroom.kamusbahasaarab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class FlashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        ImageView iView = findViewById(R.id.splashLogo);
        Animation animasi = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        iView.startAnimation(animasi);

        Thread timer = new Thread(){

            @Override
            public void run(){

                try {
                    sleep(3000);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        };
        timer.start();
    }
}