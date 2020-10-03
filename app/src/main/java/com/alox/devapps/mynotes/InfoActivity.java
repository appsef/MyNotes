package com.alox.devapps.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.BuildCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        versionCode = findViewById(R.id.versionCodeText);

        String version = BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE+")" + " - " +BuildConfig.BUILD_TYPE;
        versionCode.setText(version);

        ImageButton backBtn = findViewById(R.id.backBtnIA);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}