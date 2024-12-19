package com.example.project167.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project167.R;
import com.example.project167.databinding.ActivityCartBinding;
import com.example.project167.databinding.ActivityMyProfileBinding;
import com.example.project167.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyProfile extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    private FirebaseFirestore db;

    private ActivityMyProfileBinding binding;
    private TextView input_Name, input_UserName, input_Phone, txt_UserFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        binding = ActivityMyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Lấy thông tin từ Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            // Lấy Display Name (Full Name)
            String fullName = user.getDisplayName();
            if (fullName != null) {
                input_Name.setText(fullName);

            } else {
                input_Name.setText("Tên người dùng chưa được cập nhật");
            }
        }

    }

}