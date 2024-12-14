package com.example.project167.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;
import com.example.project167.R;

public class LoginAdminActivity extends AppCompatActivity {
    private EditText inputUserName, inputPwd;
    private Button btnLogin;
    private TextView txtAdminLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login); // Thay đổi tên layout nếu cần

        inputUserName = findViewById(R.id.input_username_admin);
        inputPwd = findViewById(R.id.input_Pwd_admin);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> {
            String userName = inputUserName.getText().toString();
            String password = inputPwd.getText().toString();

            // Kiểm tra thông tin đăng nhập
            if (userName.equals("admin") && password.equals("admin123")) {
                // Nếu thông tin đúng, chuyển hướng tới trang Admin
                Intent intent = new Intent(LoginAdminActivity.this, ProfileAdminActivity.class);
                startActivity(intent);
                finish(); // Đóng màn hình đăng nhập
            } else {
                // Nếu thông tin sai, hiển thị thông báo
                Toast.makeText(LoginAdminActivity.this, "Tên đăng nhập hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
