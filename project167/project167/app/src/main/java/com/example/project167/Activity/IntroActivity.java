package com.example.project167.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.project167.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        statusBarColor();

        new Handler().postDelayed(() -> {
            // Chuyển sang MainActivity
            startActivity(new Intent(IntroActivity.this, MainActivity.class)); //Test chức năng của Login <MainActivity>
            // Kết thúc IntroActivity
            finish();
        }, 3000); // 3000ms = 3 giây
    }

    //chỉnh màu thanh trạng thái
    private void statusBarColor() {
        Window window=IntroActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(IntroActivity.this,R.color.white));
    }
}
