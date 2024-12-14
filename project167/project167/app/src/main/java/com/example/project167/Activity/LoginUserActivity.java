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

import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.R;

public class LoginUserActivity extends AppCompatActivity {

    private EditText inputUserName, inputPassword;
    private Button btnLogin;
    private TextView txtSignUp, txtAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
            navigateToMain();
        }

        // Xử lý sự kiện nút Đăng nhập
        btnLogin.setOnClickListener(v -> handleLogin(sharedPreferences));

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
    }

    private void handleLogin(SharedPreferences sharedPreferences) {
        String userName = inputUserName.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        // Kiểm tra thông tin nhập vào
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy thông tin tài khoản từ SharedPreferences
        String savedUserName = sharedPreferences.getString("userName", "");
        String savedPassword = sharedPreferences.getString("userPassword", "");

        // Kiểm tra tài khoản và mật khẩu
        if (userName.equals(savedUserName) && encryptPassword(password).equals(savedPassword)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.apply();

            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            navigateToMain();
        } else {
            Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginUserActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private String encryptPassword(String password) {
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes());
            byte[] hash = messageDigest.digest();

            // Chuyển hash thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
