package com.example.project167.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project167.R;

public class PaymentNotification extends AppCompatActivity {

    TextView txtNotification;
    //ImageView img_payment_status;
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_notification);

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMainActivity();
            }
        });


        txtNotification = findViewById(R.id.textViewNotify);
        //img_payment_status = findViewById(R.id.img_payment_status);

        Intent intent = getIntent();
        txtNotification.setText(intent.getStringExtra("result"));

    }
    private void navigateToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Kết thúc Activity hiện tại (nếu cần)
    }
}
