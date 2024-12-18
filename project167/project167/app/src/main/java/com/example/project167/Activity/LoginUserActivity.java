package com.example.project167.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project167.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUserActivity extends AppCompatActivity {

    private EditText inputUserName, inputPassword;
    private Button btnLogin;
    private TextView txtSignUp, txtAdminLogin, txt_forgotPwd;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    private ImageView imageAvatar;
    private Button btnChangeAvatar;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        // Ánh xạ các thành phần giao diện
        inputUserName = findViewById(R.id.input_UserName);
        inputPassword = findViewById(R.id.input_Pwd);
        btnLogin = findViewById(R.id.btn_login);
        txtSignUp = findViewById(R.id.txt_signup);
        txtAdminLogin = findViewById(R.id.txt_AdminLogin);
        txt_forgotPwd = findViewById(R.id.txt_forgotPwd);
        imageAvatar = findViewById(R.id.imageAvatar);
        btnChangeAvatar = findViewById(R.id.btnChangeAvatar);

        // Hiển thị avatar hiện tại
//        loadUserAvatar();
//
//        // Bắt sự kiện đổi avatar
//        btnChangeAvatar.setOnClickListener(v -> openImagePicker());

        //chức năng quên mật khẩu
        txt_forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start alertdialog

                View view = inflater.inflate(R.layout.reset_pop, null);

                reset_alert.setTitle("Quên mật khẩu?")
                        .setMessage("Điền Email để nhận link reset mật khẩu.")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //validate the email address
                                EditText email = view.findViewById(R.id.rest_email_pop);
                                if(email.getText().toString().isEmpty()){
                                    email.setError("Vui lòng điền Email.");
                                    return;
                                }
                                //send the reset link
                                firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(LoginUserActivity.this, "Đã gửi Email Reset mật khẩu.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).setNegativeButton("Hủy", null)
                        .setView(view)
                        .create().show();
            }
        });


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
//
//    //XỬ LÝ AVATAR
//    private void loadUserAvatar() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null && user.getPhotoUrl() != null) {
//            Glide.with(this)
//                    .load(user.getPhotoUrl())
//                    .placeholder(R.drawable.default_avatar)
//                    .into(imageAvatar);
//        }
//    }

}



