package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

public class ComposeActivity extends AppCompatActivity {
    ImageView ivUserProfileImage;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ivUserProfileImage = findViewById(R.id.ivProfileImage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTweet);
        setSupportActionBar(toolbar);

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // close this activity and return to preview activity (if there is any)
            }
        });

    }
}