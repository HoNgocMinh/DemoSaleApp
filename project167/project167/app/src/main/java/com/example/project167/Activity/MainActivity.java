package com.example.project167.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project167.Adapter.PopularAdapter;
import com.example.project167.R;
import com.example.project167.databinding.ActivityMainBinding;
import com.example.project167.domain.PopularDomain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private TextView txtUserFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        //ConstraintLayout btn1=findViewById(R.id.currentCourse);
        //btn1.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CoursesListActivity.class)));

         statusBarColor();
         initRecyclerView();
         CartNavigation();
         CourseNavigation(); // (Điều hướng sang trang danh mục trước)
         ProfileNavigation();
         SeeMoreCourseNavigation();
         SeeMoreCategoryNavigation();

        // Liên kết TextView
        txtUserFullName = findViewById(R.id.txt_UserName);

        // Lấy thông tin từ Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            // Lấy Display Name (Full Name)
            String fullName = user.getDisplayName();
            if (fullName != null) {
                txtUserFullName.setText(fullName);
            } else {
                txtUserFullName.setText("Tên người dùng chưa được cập nhật");
            }
        } else {
            txtUserFullName.setText("Người dùng chưa đăng nhập");
        }

        //check veriy mail
//        if(!firebaseAuth.getCurrentUser().isEmailVerified()){
//            Toast.makeText(this, "Vui lòng xác thực Email.", Toast.LENGTH_SHORT).show();
//        }
    }

    private void SeeMoreCourseNavigation(){
        binding.btnSeeMorePopularCourse.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CoursesPopularListActivity.class)));
    }

    private void SeeMoreCategoryNavigation(){
        binding.btnSeeAllCategory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CategoryListActivity.class)));
    }

    private void CourseNavigation() {
        binding.currentCourse.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CoursesPopularListActivity.class)));
    }

    private void CartNavigation() {
        binding.btnCart.setOnClickListener(v->startActivity(new Intent(MainActivity.this, CartActivity.class)));
    }

    private void ProfileNavigation() {
        binding.btnMyProfile.setOnClickListener(v -> {

            // Lấy trạng thái đăng nhập
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();

            if (currentUser == null) {
                // Nếu người dùng chưa đăng nhập, chuyển về trang Login
                Toast.makeText(MainActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginUserActivity.class));
                finish();
            }
            // Nếu đã đăng nhập, chuyển sang trang
            else {
                startActivity(new Intent(MainActivity.this, ProfileUserActivity.class));
            }
        });
    }


    private void statusBarColor() {
        Window window=MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.dark_blue));
    }

    private void initRecyclerView() {
        ArrayList<PopularDomain> items=new ArrayList<>();
        items.add(new PopularDomain("lập trình web","ic_1",3,5,500,"Học cùng thầy X"));
        items.add(new PopularDomain("Công nghệ phần mềm","ic_3",2,4.5,450,"Học cùng cô Y"));
        items.add(new PopularDomain("Nấu ăn ngon mỗi ngày","ic_2",3,4.9,800,"Học cùng thầy Bẻo"));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //binding.PopularView.setLayoutManager(gridLayoutManager);
        binding.PopularView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.PopularView.setAdapter(new PopularAdapter(items));
    }
}