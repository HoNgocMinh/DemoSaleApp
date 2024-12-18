package com.example.project167.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import com.example.project167.R;

public class ManageCourseActivity extends AppCompatActivity {
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category_list);

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v->{finish();});
    }
}
