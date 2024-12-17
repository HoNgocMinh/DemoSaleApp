package com.example.project167.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUserActivity extends AppCompatActivity {

    private EditText inputUserName, inputPassword;
    private Button btnLogin;
    private TextView txtSignUp, txtAdminLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        // Ánh xạ các thành phần giao diện
        inputUserName = findViewById(R.id.input_UserName);
        inputPassword = findViewById(R.id.input_Pwd);
        btnLogin = findViewById(R.id.btn_login);
        txtSignUp = findViewById(R.id.txt_signup);
        txtAdminLogin = findViewById(R.id.txt_AdminLogin);

        // Kiểm tra trạng thái đăng nhập
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            MainNavigation();
        }

        // Xử lý sự kiện chuyển sang màn hình đăng ký
        txtSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginUserActivity.this, SignUpUserActivity.class);
            startActivity(intent);
        });

                // Xử lý chuyển hướng giao diện login_admin
        txtAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến AdminActivity khi bấm vào TextView
                Intent intent = new Intent(LoginUserActivity.this, LoginAdminActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //extract / validate
                if (inputUserName.getText().toString().isEmpty()){
                    inputUserName.setError("Không có email");
                    return;
                }
                if (inputPassword.getText().toString().isEmpty()){
                    inputPassword.setError("Không thấy password.");
                    return;
                }

                // data is valid
                // login user

                firebaseAuth.signInWithEmailAndPassword(inputUserName.getText().toString(),inputPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                     //login suceessful
                        Toast.makeText(LoginUserActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                        // Kiểm tra nếu người dùng đến từ Giỏ hàng
                        Intent intent;
                        boolean fromCart = getIntent().getBooleanExtra("fromCart", false);
                        // Logic từ giỏ hàng về lại giỏ hàng sau đăng nhập
                        if (fromCart) {
                            // Nếu đến từ giỏ hàng, chuyển về lại giỏ hàng
                            intent = new Intent(LoginUserActivity.this, CartActivity.class);
                        } else {
                            // Nếu không, chuyển đến MainActivity
                            intent = new Intent(LoginUserActivity.this, MainActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void MainNavigation() {
        Intent intent = new Intent(LoginUserActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
