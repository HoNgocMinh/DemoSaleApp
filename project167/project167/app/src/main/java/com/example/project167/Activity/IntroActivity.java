package com.example.project167.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        new Handler().postDelayed(() -> {
            // Chuyển sang MainActivity
            startActivity(new Intent(IntroActivity.this, MainActivity.class)); //Test chức năng của Login <MainActivity>
            // Kết thúc IntroActivity
            finish();
        }, 3000); // 3000ms = 3 giây
    }
}
