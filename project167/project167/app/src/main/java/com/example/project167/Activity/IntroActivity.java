package com.example.project167.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

    //làm trong suốt thanh trạng thái
    private void statusBarColor() {
        Window window = IntroActivity.this.getWindow();

        // Làm thanh trạng thái trong suốt
        window.setStatusBarColor(android.graphics.Color.TRANSPARENT);

        // Kích hoạt chế độ toàn màn hình (ẩn thanh trạng thái và điều hướng)
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // Ẩn thanh trạng thái
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Ẩn thanh điều hướng
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // Duy trì chế độ toàn màn hình
        );
    }
}
