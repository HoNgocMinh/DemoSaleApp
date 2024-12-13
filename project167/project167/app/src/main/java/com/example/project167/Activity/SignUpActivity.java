package com.example.project167.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText input_fullName, input_UserName, inputPwd, inputRePwd;
    private Button btnSignup;
    private TextView txt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Ánh xạ các thành phần từ layout
        input_fullName = findViewById(R.id.input_Name);
        input_UserName = findViewById(R.id.input_UserName);
        inputPwd = findViewById(R.id.input_Pwd);
        inputRePwd = findViewById(R.id.input_RePwd);
        btnSignup = findViewById(R.id.btn_Signup);
        txt_login = findViewById(R.id.txt_login);

        // Xử lý sự kiện nút Đăng ký
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignup();
            }
        });

        //Xử lí lệnh quay lại đăng nhập
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void handleSignup() {
        String fullName = input_fullName.getText().toString().trim();
        String UserName = input_UserName.getText().toString().trim();
        String password = inputPwd.getText().toString().trim();
        String confirmPassword = inputRePwd.getText().toString().trim();

        // Kiểm tra các trường nhập liệu
        if (fullName.isEmpty() || UserName.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUpActivity.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiện thông báo đăng ký thành công (thay bằng logic lưu trữ sau)
        Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        saveUser(fullName, UserName, password);

        // Quay lại màn hình đăng nhập
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveUser(String fullName, String UserName, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userFullName", fullName);
        editor.putString("userName", UserName);
        editor.putString("userPassword", password); // Không khuyến khích lưu mật khẩu dưới dạng plaintext
        editor.apply();
    }

}
