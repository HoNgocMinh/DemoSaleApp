package com.example.project167.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.R;
import com.example.project167.databinding.ActivityMyCourseListBinding;

public class MyCourseActivity extends AppCompatActivity {
    private ActivityMyCourseListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_list);

        // Sử dụng View Binding để lấy các view
        binding = ActivityMyCourseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Thiết lập sự kiện click cho btnBack
        binding.backbtn.setOnClickListener(v -> finish());
    }
}