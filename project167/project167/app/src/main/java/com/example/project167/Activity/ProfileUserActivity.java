package com.example.project167.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project167.R;
import com.example.project167.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileUserActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    FirebaseAuth firebaseAuth;
    TextView txtUserFullName;
    Button btn_verifyEmail;
    AlertDialog.Builder reset_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reset_alert = new AlertDialog.Builder(this);

        MainNavigation();
        CourseNavigation();
        LogoutNavigation();
        MyCourseNavigation();
        statusBarColor();
        resetPwd();
        //myProfile();
        deleteAccount();

        // Liên kết TextView
        txtUserFullName = findViewById(R.id.txt_UserFullName);
        btn_verifyEmail = findViewById(R.id.btn_verifyEmail);

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

    private void MyCourseNavigation(){
        binding.btnMyCourse.setOnClickListener(v -> startActivity(new Intent(ProfileUserActivity.this, MyCourseActivity.class)));
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

//    private void myProfile () {
//        binding.btnMyInfo.setOnClickListener(view -> startActivity(new Intent(ProfileUserActivity.this,MyProfile.class)));
//    }
    //đăng xuất
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
                txtUserFullName.setText("Khách");
            }
        });
    }

    //đổi pwd
    private void resetPwd(){
        binding.btnChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileUserActivity.this, ResetPassword.class);
                startActivity(intent);
            }
        });
    }

    private void deleteAccount(){
        binding.btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reset_alert.setTitle("Xóa tài khoản?")
                        .setMessage("Bạn chắc chưa? Bạn sợ à?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(ProfileUserActivity.this, "Xóa tài khoản thành công.", Toast.LENGTH_SHORT).show();
                                        firebaseAuth.signOut();
                                        startActivity(new Intent(getApplicationContext(), LoginUserActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ProfileUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).setNegativeButton("cancel", null)
                        .create().show();
            }
        });

    }

    //chỉnh màu thanh trạng thái
    private void statusBarColor() {
        Window window=ProfileUserActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(ProfileUserActivity.this, R.color.lightGrey));
    }
}
