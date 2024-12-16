package com.example.project167.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.example.project167.R;
import com.example.project167.databinding.ActivityProfileBinding;

public class ProfileUserActivity extends AppCompatActivity {
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainNavigation();
        CourseNavigation();
        LogoutNavigation();
        statusBarColor();
    }

    private void CourseNavigation() {
        binding.btnCourse.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileUserActivity.this, CategoryListActivity.class);
            // Xóa ProfileActivity khỏi stack
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            // Áp dụng hiệu ứng mượt giống như nút Back
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    private void MainNavigation() {
        binding.btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileUserActivity.this, MainActivity.class);
            //
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            //
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    private void LogoutNavigation() {
        binding.btnLogout.setOnClickListener(v -> {
            // Xóa trạng thái đăng nhập
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);  // Đặt lại trạng thái là chưa đăng nhập
            editor.apply();

            Toast.makeText(ProfileUserActivity.this, "Đã đăng xuất thành công", Toast.LENGTH_SHORT).show();

            // Chuyển hướng về màn hình đăng nhập
            Intent intent = new Intent(ProfileUserActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
    //chỉnh màu thanh trạng thái
    private void statusBarColor() {
        Window window=ProfileUserActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(ProfileUserActivity.this, R.color.lightGrey));
    }
}
