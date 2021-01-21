package id.co.sweetmushroom.kamusbahasaarab.fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import id.co.sweetmushroom.kamusbahasaarab.MailActivity;
import id.co.sweetmushroom.kamusbahasaarab.R;

public class ContactFragment extends Fragment {

    public View viewer;
    private Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewer = inflater.inflate(R.layout.fragment_contact, container, false);

        toolbar = viewer.findViewById(R.id.toolbarContact);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).setTitle("");

        return viewer;
    }



    public void openGmail(Activity activity) {
        Intent intent=new Intent(Intent.ACTION_SEND);
        String[] recipients={"sweetmushroom.official@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback Application");
        intent.putExtra(Intent.EXTRA_TEXT,"");
        intent.setType("text/html");

        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> resInfo = pm.queryIntentActivities(intent, 0);
        for (int i = 0; i < resInfo.size(); i++) {
            ResolveInfo ri = resInfo.get(i);
            if (ri.activityInfo.packageName.contains("android.gm")) {
                intent.setPackage("com.google.android.gm");
            }
        }

        startActivity(Intent.createChooser(intent, "Send mail"));
    }
}
