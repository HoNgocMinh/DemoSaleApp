package com.example.project167.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project167.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {
    EditText userPwd, userRePwd, userOldPwd;
    Button btnConfirm;
    FirebaseUser user;
    FirebaseAuth auth;
    TextView txtBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        userPwd = findViewById(R.id.newUserPwd);
        userRePwd = findViewById(R.id.newRePwd);
        userOldPwd = findViewById(R.id.oldUserPwd);
        btnConfirm = findViewById(R.id.btn_Confirm);
        txtBack = findViewById(R.id.txtBack);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        btnConfirm.setOnClickListener(view -> {
            String oldPwd = userOldPwd.getText().toString().trim();
            String newPwd = userPwd.getText().toString().trim();
            String reNewPwd = userRePwd.getText().toString().trim();

            if (oldPwd.isEmpty()) {
                userOldPwd.setError("Không được để trống");
                return;
            }
            if (newPwd.isEmpty()) {
                userPwd.setError("Không được để trống");
                return;
            }
            if (reNewPwd.isEmpty()) {
                userRePwd.setError("Không được để trống");
                return;
            }
            if (!newPwd.equals(reNewPwd)) {
                userRePwd.setError("Mật khẩu không khớp");
                return;
            }

            // Re-authenticate user with old password
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPwd);
            user.reauthenticate(credential).addOnSuccessListener(aVoid -> {
                // Update password after successful re-authentication
                user.updatePassword(newPwd).addOnSuccessListener(aVoid1 -> {
                    Toast.makeText(ResetPassword.this, "Cập nhật mật khẩu mới thành công.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ProfileUserActivity.class));
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(ResetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }).addOnFailureListener(e -> {
                userOldPwd.setError("Mật khẩu cũ không chính xác");
                Toast.makeText(ResetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
        //Thêm điều hướng trở về đăng nhập
        txtBack.setOnClickListener(v -> finish());
    }
}
