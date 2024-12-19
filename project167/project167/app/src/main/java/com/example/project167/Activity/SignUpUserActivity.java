package com.example.project167.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpUserActivity extends AppCompatActivity {

    private EditText inputFullName, inputUserName, inputPassword, inputRePassword;
    private Button btnSignUp;
    private TextView txtLogin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

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

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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

        if(fullName.isEmpty()){
            inputFullName.setError("Vui lòng nhập họ và tên.");
            return;
        }

        if(userName.isEmpty()){
            inputUserName.setError("Vui lòng nhập email.");
            return;
        }
        if(password.isEmpty()){
            inputPassword.setError("Vui lòng nhập mật khẩu.");
            return;
        }
        if(rePassword.isEmpty()){
            inputRePassword.setError("Mật khẩu không khớp.");
            return;
        }

        fAuth.createUserWithEmailAndPassword(userName,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseUser user = fAuth.getCurrentUser();
                if (user != null) {

                    // Cập nhật Display Name (Họ tên) của người dùng
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(fullName) // fullName là chuỗi họ tên từ form đăng ký
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Firebase", "User profile updated.");

                                        // Sau khi cập nhật thành công, chuyển đến trang đăng nhập

                                        //send verification email
                                        fAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(SignUpUserActivity.this, "Đăng ký thành công, đã gửi mail xác thực.", Toast.LENGTH_SHORT).show();
                                                DocumentReference df = fStore.collection("Users").document();
                                            }
                                        });
                                        Intent intent = new Intent(SignUpUserActivity.this, LoginUserActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Log.e("Firebase", "Failed to update profile: " + task.getException().getMessage());
                                        Toast.makeText(SignUpUserActivity.this, "Failed to save user profile.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
