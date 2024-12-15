package com.example.project167.Activity;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project167.Adapter.CoursesAdapter;
import com.example.project167.R;
import com.example.project167.domain.CoursesDomain;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterCourceList;
    private RecyclerView recyclerViewCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        statusBarColor();
        initRecyclerView();
    }
    private void statusBarColor() {
        Window window= CategoryListActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(CategoryListActivity.this,R.color.white));
    }
    private void initRecyclerView() {
        ArrayList<CoursesDomain> items = new ArrayList<>();
        items.add(new CoursesDomain("Advanced Certification Program in AI", 169, "ic_1"));
        items.add(new CoursesDomain("Google Cloud Platform Architecture", 69, "ic_2"));
        items.add(new CoursesDomain("Fundamental of Java Programming", 150, "ic_3"));
        items.add(new CoursesDomain("Introduction to UI design history", 79, "ic_4"));
        items.add(new CoursesDomain("PG Program in Big Data Engineering", 49, "ic_5"));
        items.add(new CoursesDomain("PG Program in Big Data Engineering2", 100, "ic_1"));
        items.add(new CoursesDomain("PG Program in Big Data Engineering3", 419, "ic_2"));

        recyclerViewCourse = findViewById(R.id.view);
        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapterCourceList = new CoursesAdapter(items);
        recyclerViewCourse.setAdapter(adapterCourceList);
    }
}