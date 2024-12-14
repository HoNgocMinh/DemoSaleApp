package com.example.project167.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project167.R;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText input_UserName, inputPassword;
    private Button btnLogin;
    private TextView txtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo các thành phần giao diện
        input_UserName = findViewById(R.id.input_UserName);
        inputPassword = findViewById(R.id.input_Pwd);
        btnLogin = findViewById(R.id.btn_login);
        txtSignUp = findViewById(R.id.txt_signup);

        // Kiểm tra xem người dùng đã đăng nhập chưa
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);  // Đọc trạng thái đăng nhập
        if (isLoggedIn) {
            // Nếu đã đăng nhập, chuyển đến màn hình chính
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();  // Đóng màn hình đăng nhập
        }

        // Xử lý sự kiện nút Đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserName = input_UserName.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Kiểm tra thông tin nhập vào
                if (UserName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Kiểm tra tên đăng nhập và mật khẩu (ở đây bạn có thể sử dụng một phương thức kiểm tra chính thức từ server)
                    String savedUserName = sharedPreferences.getString("userName", "");
                    String savedPassword = sharedPreferences.getString("userPassword", "");

                    if (UserName.equals(savedUserName) && password.equals(savedPassword)) {
                        // Lưu trạng thái đăng nhập vào SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);  // Đánh dấu đã đăng nhập
                        editor.apply();

                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                        // Chuyển sang màn hình chính sau khi đăng nhập thành công
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Xử lý sự kiện Đăng ký
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();  // Đảm bảo không quay lại được màn hình đăng nhập sau khi đăng ký
            }
        });
    }
}
