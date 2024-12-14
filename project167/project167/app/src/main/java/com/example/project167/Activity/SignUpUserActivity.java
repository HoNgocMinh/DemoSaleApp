package com.example.project167.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpUserActivity extends AppCompatActivity {

    private EditText inputFullName, inputUserName, inputPassword, inputRePassword;
    private Button btnSignUp;
    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Ánh xạ các thành phần từ layout
        inputFullName = findViewById(R.id.input_Name);
        inputUserName = findViewById(R.id.input_UserName);
        inputPassword = findViewById(R.id.input_Pwd);
        inputRePassword = findViewById(R.id.input_RePwd);
        btnSignUp = findViewById(R.id.btn_Signup);
        txtLogin = findViewById(R.id.txt_login);

        // Xử lý sự kiện nút Đăng ký
        btnSignUp.setOnClickListener(v -> handleSignUp());

        // Xử lý sự kiện quay lại màn hình đăng nhập
        txtLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpUserActivity.this, LoginUserActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void handleSignUp() {
        String fullName = inputFullName.getText().toString().trim();
        String userName = inputUserName.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String rePassword = inputRePassword.getText().toString().trim();

        // Kiểm tra đầu vào
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(userName) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(rePassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xem tên đăng nhập đã tồn tại chưa
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String savedUserName = sharedPreferences.getString("userName", "");
        if (userName.equals(savedUserName)) {
            Toast.makeText(this, "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lưu thông tin người dùng
        saveUser(fullName, userName, password);

        // Thông báo và chuyển sang màn hình đăng nhập
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUpUserActivity.this, LoginUserActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveUser(String fullName, String userName, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userFullName", fullName);
        editor.putString("userName", userName);

        // Mã hóa mật khẩu trước khi lưu
        String encryptedPassword = encryptPassword(password);
        editor.putString("userPassword", encryptedPassword);
        editor.apply();
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
