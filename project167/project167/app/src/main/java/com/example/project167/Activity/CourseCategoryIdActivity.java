package com.example.project167.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project167.Adapter.PopularAdapter;
import com.example.project167.Database.SaleCourse;
import com.example.project167.databinding.ActivityCoursesPopularListBinding;
import com.example.project167.domain.PopularDomain;
import com.example.project167.R;

import java.util.ArrayList;

public class CourseCategoryIdActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterCourceList;
    private RecyclerView recyclerViewCourse;
    private SaleCourse dbHelper; // Đối tượng SaleCourse để kết nối cơ sở dữ liệu
    ActivityCoursesPopularListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_popular_list);
        binding = ActivityCoursesPopularListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        statusBarColor();
        // Khởi tạo đối tượng SaleCourse
        dbHelper = new SaleCourse(this);

        //Fix tối ưu viết lệnh (cần lưu ý)
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        dbHelper.BasicCategory(db);
        dbHelper.BasicCourse(db);

        // Nhận CATEGORY_ID từ Intent
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);

        if (categoryId == -1) {
            Toast.makeText(this, "Invalid category ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initRecyclerView(categoryId);
        setVariable();
    }

    private void statusBarColor() {
        Window window = CourseCategoryIdActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(CourseCategoryIdActivity.this, R.color.white));
    }

    private void initRecyclerView(int categoryId) {
        // Lấy danh sách khóa học theo CATEGORY_ID
        ArrayList<PopularDomain> items = getCoursesByCategoryId(categoryId);

        recyclerViewCourse = findViewById(R.id.view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewCourse.setLayoutManager(gridLayoutManager);

        //Thiets lập adapter
        adapterCourceList = new PopularAdapter(items);
        recyclerViewCourse.setAdapter(adapterCourceList);
    }

    private ArrayList<PopularDomain> getCoursesByCategoryId(int categoryId) {
        ArrayList<PopularDomain> courses = new ArrayList<>();
        Cursor cursor = dbHelper.getCoursesByCategoryId(categoryId);  // Truy vấn tất cả các khóa học có id là category

        // Kiểm tra nếu cursor không null và di chuyển tới dòng đầu tiên
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Lấy chỉ mục cột và kiểm tra giá trị trả về không phải -1
                int titleIndex = cursor.getColumnIndex(SaleCourse.COURSE_NAME);
                int priceIndex = cursor.getColumnIndex(SaleCourse.COURSE_PRICE);
                int picPathIndex = cursor.getColumnIndex(SaleCourse.COURSE_PICTURE);
                int reviewIndex = cursor.getColumnIndex(SaleCourse.COURSE_COUNT_REVIEW);
                int descriptionIndex = cursor.getColumnIndex(SaleCourse.COURSE_DESCRIPTION);
                int scoreIndex = cursor.getColumnIndex(SaleCourse.COURSE_SCORE);

                // Nếu chỉ mục cột hợp lệ (>=0)
                if (titleIndex >= 0 && priceIndex >= 0 && picPathIndex >= 0) {
                    // Lấy dữ liệu từ cursor
                    String title = cursor.getString(titleIndex);
                    int price = cursor.getInt(priceIndex);
                    String picPath = cursor.getString(picPathIndex);
                    int count_review = Integer.parseInt(cursor.getString(reviewIndex));
                    String description = cursor.getString(descriptionIndex);
                    int score = Integer.parseInt(cursor.getString(scoreIndex));

                    // Tạo đối tượng CoursesDomain và thêm vào danh sách
                    courses.add(new PopularDomain(title, picPath, count_review, score, price,description));
                } else {
                    // Nếu một trong các cột không tồn tại, bạn có thể xử lý lỗi ở đây
                    Log.e("CourseCategoryIdActivity", "Column index is invalid!");
                }
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            // Nếu không có dữ liệu trong database, hiển thị thông báo
            Toast.makeText(this, "No courses found in the database", Toast.LENGTH_SHORT).show();
        }
        return courses;
    }

    private void setVariable() {
        binding.backbtn.setOnClickListener(v -> finish());
    }
}
