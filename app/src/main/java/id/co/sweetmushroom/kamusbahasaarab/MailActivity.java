package id.co.sweetmushroom.kamusbahasaarab;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MailActivity extends AppCompatActivity {

    public View viewer;
    private Toolbar toolbar;
    private Button sendButton;
    private EditText sndto,sbj,body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        toolbar = findViewById(R.id.toolbarMail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Send Feedback Mail");

        sendButton  = findViewById(R.id.buttonSend);
        sndto       = findViewById(R.id.sendTo);
        sbj         = findViewById(R.id.subject);
        body        = findViewById(R.id.body);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

                emailIntent.setType("message/rfc822");

                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ sndto.getText().toString()});

                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, sbj.getText());

                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body.getText());

                startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"));
            }
        });




    }
}
