package com.example.project167.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.project167.R;
import com.example.project167.databinding.ActivityProfileBinding;


public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainNavigation();
        CourseNavigation1();
        CourseNavigation2();
        LogoutNavigation();
    }

    private void CourseNavigation1() {
        binding.btnMyCourse1.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, CoursesListActivity.class);
            // Xóa ProfileActivity khỏi stack
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            // Áp dụng hiệu ứng mượt giống như nút Back
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    private void CourseNavigation2() {
        binding.btnMyCourse2.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, CoursesListActivity.class);
            // Xóa ProfileActivity khỏi stack
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            // Áp dụng hiệu ứng mượt giống như nút Back
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    private void MainNavigation() {
        binding.btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            //
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            //
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    private void LogoutNavigation(){
        binding.btnLogout.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, LoginActivity.class)));
    }
}
