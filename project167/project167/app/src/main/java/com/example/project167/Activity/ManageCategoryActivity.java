package com.example.project167.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.project167.Adapter.ManageCategoryAdapter;
import com.example.project167.Database.SaleCourse;
import com.example.project167.R;
import com.example.project167.databinding.ActivityAdminCategoryListBinding;
import com.example.project167.domain.CategoryDomain;
import java.util.ArrayList;

public class ManageCategoryActivity extends AppCompatActivity {
    private ActivityAdminCategoryListBinding binding;
    private ArrayList<CategoryDomain> categoryList;
    private ManageCategoryAdapter manageCategoryAdapter;
    private SaleCourse saleCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sử dụng ViewBinding
        binding = ActivityAdminCategoryListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo cơ sở dữ liệu
        saleCourse = new SaleCourse(this);

        // Khởi tạo dữ liệu và hiển thị danh sách
        initCategoryList();
        setupRecyclerView();

        // Nút back
        binding.imgBack.setOnClickListener(v -> finish());
    }

    private void initCategoryList() {
        categoryList = new ArrayList<>();

        // Lấy danh sách danh mục từ cơ sở dữ liệu
        Cursor cursor = saleCourse.getAllCategory();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Kiểm tra chỉ mục cột và đảm bảo không phải -1
                int categoryIdIndex = cursor.getColumnIndex(SaleCourse.CATEGORY_ID);
                int categoryNameIndex = cursor.getColumnIndex(SaleCourse.CATEGORY_NAME);
                int categoryPicIndex = cursor.getColumnIndex(SaleCourse.CATEGORY_PICTURE);

                // Kiểm tra chỉ mục cột hợp lệ
                if (categoryIdIndex >= 0 && categoryNameIndex >= 0 && categoryPicIndex >= 0) {
                    // Lấy dữ liệu từ cursor
                    int categoryId = cursor.getInt(categoryIdIndex);
                    String categoryName = cursor.getString(categoryNameIndex);
                    String categoryPic = cursor.getString(categoryPicIndex);

                    // Thêm vào danh sách category
                    categoryList.add(new CategoryDomain(categoryId, categoryName, categoryPic));
                } else {
                    // Nếu một trong các cột không tồn tại, bạn có thể xử lý lỗi ở đây
                    Log.e("ManageCategoryActivity", "Column index is invalid!");
                }
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            // Nếu không có dữ liệu trong database, hiển thị thông báo
            Toast.makeText(this, "No categories found in the database", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRecyclerView() {
        // Tạo và gán adapter cho RecyclerView
        manageCategoryAdapter = new ManageCategoryAdapter(categoryList);
        binding.view.setLayoutManager(new LinearLayoutManager(this));
        binding.view.setAdapter(manageCategoryAdapter);
    }
}
