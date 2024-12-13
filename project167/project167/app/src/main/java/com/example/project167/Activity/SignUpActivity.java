package com.example.project167.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputName, inputEmail, inputPwd, inputRePwd;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Ánh xạ các thành phần từ layout
        inputName = findViewById(R.id.input_Name);
        inputEmail = findViewById(R.id.editTextTextPersonName2);
        inputPwd = findViewById(R.id.input_Pwd);
        inputRePwd = findViewById(R.id.input_RePwd);
        btnSignup = findViewById(R.id.btn_Signup);

        // Xử lý sự kiện nút Đăng ký
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignup();
            }
        });
    }

    private void handleSignup() {
        String name = inputName.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPwd.getText().toString().trim();
        String confirmPassword = inputRePwd.getText().toString().trim();

        // Kiểm tra các trường nhập liệu
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUpActivity.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiện thông báo đăng ký thành công (thay bằng logic lưu trữ sau)
        Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        saveUser(name, email, password);
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);

        // Quay lại màn hình đăng nhập
        finish();
    }

    private void saveUser(String name, String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", name);
        editor.putString("userEmail", email);
        editor.putString("userPassword", password); // Không khuyến khích lưu mật khẩu dưới dạng plaintext
        editor.apply();
    }
}
