package com.example.project167.Activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project167.Adapter.CoursesAdapter;
import com.example.project167.Database.SaleCourse;
import com.example.project167.R;
import com.example.project167.domain.CoursesDomain;

import java.util.ArrayList;

public class CategoryListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterCategoryList;
    private RecyclerView recyclerViewCategory;
    private SaleCourse dbHelper; // Đối tượng SaleCourse để kết nối cơ sở dữ liệu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        statusBarColor();

        // Khởi tạo đối tượng SaleCourse
        dbHelper = new SaleCourse(this);
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        dbHelper.BasicCategory(db);
        // Khởi tạo RecyclerView
        initRecyclerView();
    }

    private void statusBarColor() {
        Window window = CategoryListActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(CategoryListActivity.this, R.color.white));
    }

    private void initRecyclerView() {
        // Lấy dữ liệu từ cơ sở dữ liệu
        ArrayList<CoursesDomain> items = getCategoriesFromDatabase();

        recyclerViewCategory = findViewById(R.id.view);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Tạo adapter và thiết lập cho RecyclerView
        adapterCategoryList = new CoursesAdapter(items);
        recyclerViewCategory.setAdapter(adapterCategoryList);
    }

    private ArrayList<CoursesDomain> getCategoriesFromDatabase() {
        ArrayList<CoursesDomain> categories = new ArrayList<>();

        // Lấy cơ sở dữ liệu ở chế độ chỉ đọc
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Truy vấn tất cả các danh mục từ bảng "CATEGORIES"
            cursor = db.rawQuery("SELECT * FROM " + SaleCourse.CATEGORIES, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Lấy chỉ mục cột
                    int nameIndex = cursor.getColumnIndex(SaleCourse.CATEGORY_NAME);
                    int iconIndex = cursor.getColumnIndex(SaleCourse.CATEGORY_PICTURE);

                    if (nameIndex >= 0 && iconIndex >= 0) {
                        // Lấy dữ liệu từ cursor
                        String name = cursor.getString(nameIndex);
                        String iconPath = cursor.getString(iconIndex);

                        // Thêm vào danh sách danh mục
                        categories.add(new CoursesDomain(name, 0, iconPath));
                    } else {
                        Log.e("CategoryListActivity", "Column index is invalid!");
                    }
                } while (cursor.moveToNext());
            } else {
                // Hiển thị thông báo nếu không có dữ liệu
                Toast.makeText(this, "No categories found in the database", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("CategoryListActivity", "Error reading database: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return categories;
    }
}
