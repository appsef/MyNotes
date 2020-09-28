package com.alox.devapps.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class WelcomeActivity extends AppCompatActivity {

    FrameLayout continueBtn;
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final ImageView logo = findViewById(R.id.logo);
        final ImageView icon = findViewById(R.id.icon_logo);

        //init
        mPreferences = getSharedPreferences("myNotes", MODE_PRIVATE);
        continueBtn = findViewById(R.id.continueBtn);



        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = mPreferences.edit();
                editor.putBoolean("notNew", true);
                editor.apply();

                Intent main = new Intent(WelcomeActivity.this, MainActivity.class);

                Pair[] pairs = new Pair[2];

                pairs[0] =new android.util.Pair<View ,String>(logo, "logo");
                pairs[1] =new android.util.Pair<View ,String>(icon, "icon");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this,pairs);

                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main,options.toBundle());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1000);
            }
        });

    }
}