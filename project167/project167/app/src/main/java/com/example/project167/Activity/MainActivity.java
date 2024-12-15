package com.example.project167.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.example.project167.Adapter.PopularAdapter;
import com.example.project167.R;
import com.example.project167.databinding.ActivityMainBinding;
import com.example.project167.domain.PopularDomain;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //ConstraintLayout btn1=findViewById(R.id.currentCourse);
        //btn1.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CoursesListActivity.class)));

         statusBarColor();
         initRecyclerView();
         CartNavigation();
//       CourseNavigation(); (Điều hướng sang trang mới)
         ProfileNavigation();
         SeeMoreCourseNavigation();
    }

    private void SeeMoreCourseNavigation(){
        binding.btnSeeMorePopularCourse.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CoursesPopularListActivity.class)));
    }

    private void CartNavigation() {
        binding.btnCart.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            if (isLoggedIn) {
                // Nếu đã đăng nhập, chuyển đến màn hình Profile
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginUserActivity.class);
                // test
                intent.putExtra("fromCart", true);
                startActivity(intent);
            }
        });
    }

    // Điều hướng đến khóa học
//    private void CourseNavigation() {
//        binding.currentCourse.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CoursesListActivity.class)));
//    }

    private void ProfileNavigation() {
        binding.btnMyProfile.setOnClickListener(v -> {
            // Lấy trạng thái đăng nhập từ SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            if (isLoggedIn) {
                // Nếu đã đăng nhập, chuyển đến màn hình Profile
                Intent intent = new Intent(MainActivity.this, ProfileUserActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginUserActivity.class);
                startActivity(intent);
            }
        });
    }


    private void statusBarColor() {
        Window window=MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.purple_Dark));
    }

    private void initRecyclerView() {
        ArrayList<PopularDomain> items=new ArrayList<>();
        items.add(new PopularDomain("T-shirt black","item_1",15,4,500,"Immerse yourself in a world of vibrant visuals and\n" +
                " immersive sound with the monitor.\n" +
                " Its cutting-edge monitor technology brings every\n" +
                " scene to life with striking clarity and rich colors.\n" +
                " With seamless integration and a sleek, modern\n" +
                " design, the monitor Pro is not just a monitor , but a\n" +
                " centerpiece for your entertainment space.\n" +
                "With its sleek, modern design, the monitor is\n" +
                " not just a TV, but a centerpiece for your \n" +
                "entertainment space. The ultra-slim bezel and\n" +
                " premium finish blend seamlessly with any decor"));
        items.add(new PopularDomain("Smart Watch","item_2",10,4.5,450,"Immerse yourself in a world of vibrant visuals and\n" +
                " immersive sound with the monitor.\n" +
                " Its cutting-edge monitor technology brings every\n" +
                " scene to life with striking clarity and rich colors.\n" +
                " With seamless integration and a sleek, modern\n" +
                " design, the monitor Pro is not just a monitor , but a\n" +
                " centerpiece for your entertainment space.\n" +
                "With its sleek, modern design, the monitor is\n" +
                " not just a TV, but a centerpiece for your \n" +
                "entertainment space. The ultra-slim bezel and\n" +
                " premium finish blend seamlessly with any decor"));
        items.add(new PopularDomain("Phone","item_3",3,4.9,800,"Immerse yourself in a world of vibrant visuals and\n" +
                " immersive sound with the monitor.\n" +
                " Its cutting-edge monitor technology brings every\n" +
                " scene to life with striking clarity and rich colors.\n" +
                " With seamless integration and a sleek, modern\n" +
                " design, the monitor Pro is not just a monitor , but a\n" +
                " centerpiece for your entertainment space.\n" +
                "With its sleek, modern design, the monitor is\n" +
                " not just a TV, but a centerpiece for your \n" +
                "entertainment space. The ultra-slim bezel and\n" +
                " premium finish blend seamlessly with any decor"));

        binding.PopularView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.PopularView.setAdapter(new PopularAdapter(items));
    }
}