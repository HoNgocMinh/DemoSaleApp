package com.example.project167.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.project167.R;
import com.example.project167.databinding.ActivityProfileAdminBinding;
import com.example.project167.databinding.ActivityProfileBinding;

public class ProfileAdminActivity extends AppCompatActivity {
    ActivityProfileAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_admin);
        binding = ActivityProfileAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LogoutNavigation();
        statusBarColor();
    }

    private void LogoutNavigation() {
        binding.btnLogout.setOnClickListener(v -> {
            // Xóa trạng thái đăng nhập
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);  // Đặt lại trạng thái là chưa đăng nhập
            editor.apply();

            Toast.makeText(ProfileAdminActivity.this, "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show();

            // Chuyển hướng về màn hình đăng nhập
            Intent intent = new Intent(ProfileAdminActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
    //chỉnh màu thanh trạng thái
    private void statusBarColor() {
        Window window=ProfileAdminActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(ProfileAdminActivity.this,R.color.lightGrey));
    }
}
