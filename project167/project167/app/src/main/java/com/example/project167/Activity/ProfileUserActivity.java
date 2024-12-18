package com.example.project167.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project167.R;
import com.example.project167.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileUserActivity extends AppCompatActivity {
    ActivityProfileBinding binding;

    FirebaseAuth firebaseAuth;
    TextView txtUserFullName;
    Button btn_verifyEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainNavigation();
        CourseNavigation();
        LogoutNavigation();
        statusBarColor();
        resetPwd();

        // Liên kết TextView
        txtUserFullName = findViewById(R.id.txt_UserFullName);

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
            txtUserFullName.setText("Khách");
        }

        btn_verifyEmail = findViewById(R.id.btn_verifyEmail);

        // xử lí xác thực email
    if(!firebaseAuth.getCurrentUser().isEmailVerified()){
        btn_verifyEmail.setVisibility(View.VISIBLE);
    }
    else {
        btn_verifyEmail.setVisibility(View.GONE);
    }
    btn_verifyEmail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //send verification email
            firebaseAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ProfileUserActivity.this, "Đã gửi mail xác thực.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    });

    }

    private void CourseNavigation() {
        binding.btnCourse.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileUserActivity.this, CoursesPopularListActivity.class);
            // Xóa ProfileActivity khỏi stack
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            // Áp dụng hiệu ứng mượt giống như nút Back
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    private void MainNavigation() {
        binding.btnHome.setOnClickListener(v -> finish());
    }

    private void LogoutNavigation() {
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileUserActivity.this, MainActivity.class);
                Toast.makeText(ProfileUserActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void resetPwd(){
        binding.btnChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileUserActivity.this, ResetPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //chỉnh màu thanh trạng thái
    private void statusBarColor() {
        Window window=ProfileUserActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(ProfileUserActivity.this, R.color.lightGrey));
    }
}
