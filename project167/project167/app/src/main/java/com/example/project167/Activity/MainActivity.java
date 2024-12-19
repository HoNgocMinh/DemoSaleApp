package com.example.project167.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project167.Adapter.PopularAdapter;
import com.example.project167.Helper.ManagmentCart;
import com.example.project167.R;
import com.example.project167.databinding.ActivityMainBinding;
import com.example.project167.domain.PopularDomain;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ManagmentCart managmentCart;
    ActivityMainBinding binding;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    TextView txtUserFullName, txticonNumber, txtHello;

    ImageView imgLapTrinh, imgNgonNgu,imgThietKe, imgNauAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

         statusBarColor();
         initRecyclerView();
         CartNavigation();
         CourseNavigation();
         ProfileNavigation();
         SeeMoreCourseNavigation();
         SeeMoreCategoryNavigation();

        // Liên kết TextView
        txticonNumber = findViewById(R.id.txt_iconNumber);
        txtUserFullName = findViewById(R.id.txt_UserName);
        txtHello = findViewById(R.id.txt_Hello);

        //Liên kết ImageView
        imgLapTrinh = findViewById(R.id.imageViewProgramme);
        imgNgonNgu = findViewById(R.id.imageViewLanguage);
        imgThietKe = findViewById(R.id.imageViewDesign);
        imgNauAn = findViewById(R.id.imageViewCook);

        imgLapTrinh.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CourseCategoryIdActivity.class);
            intent.putExtra("CATEGORY_ID", 5);
            startActivity(intent);
        });

        imgNgonNgu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CourseCategoryIdActivity.class);
            intent.putExtra("CATEGORY_ID", 4);
            startActivity(intent);
        });

        imgThietKe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CourseCategoryIdActivity.class);
            intent.putExtra("CATEGORY_ID", 2);
            startActivity(intent);
        });

        imgNauAn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CourseCategoryIdActivity.class);
            intent.putExtra("CATEGORY_ID", 1);
            startActivity(intent);
        });

        if (currentUser == null){
            txtUserFullName.setText("Khách");
        }

        //Lấy giờ hiện tại, Xác định câu chào dựa vào giờ
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // Giờ hiện tại (0 - 23)

        String greeting;
        if (hour >= 5 && hour < 12) {
            greeting = "Chào buổi sáng,";
        } else if (hour >= 12 && hour < 14) {
            greeting = "Chào buổi trưa,";
        } else if (hour >= 14 && hour < 18) {
            greeting = "Chào buổi chiều,";
        } else {
            greeting = "Chào buổi tối,";
        }

        txtHello.setText(greeting);
        txtUserFullName.setText("Khách");
        // Lấy thông tin từ Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            // Lấy Display Name (Full Name)
            String fullName = user.getDisplayName();
            if (fullName != null) {
                txtUserFullName.setText(fullName);
            } else {
                txtUserFullName.setText("Tên người dùng chưa được cập nhật");
            }
            if(!firebaseAuth.getCurrentUser().isEmailVerified()){
                Toast.makeText(this, "Vui lòng xác thực Email.", Toast.LENGTH_SHORT).show();
            }
        } else {
            txtUserFullName.setText("Khách");
        }

        //check veriy mail

    }

    private void updateCartItemCount() {
        ManagmentCart cart = new ManagmentCart(this);
        int cartItemCount = cart.getCartItemCount(); // Lấy số lượng khóa học trong giỏ
        txticonNumber.setText(String.valueOf(cartItemCount)); // Hiển thị số lượng trên giao diện
    }

    private void SeeMoreCourseNavigation(){
        binding.btnSeeMorePopularCourse.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CoursesPopularListActivity.class)));
    }

    private void SeeMoreCategoryNavigation(){
        binding.btnSeeAllCategory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CategoryListActivity.class)));
    }

    private void CourseNavigation() {
        binding.currentCourse.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CoursesPopularListActivity.class)));
    }

    private void CartNavigation() {
        binding.btnCart.setOnClickListener(v->startActivity(new Intent(MainActivity.this, CartActivity.class)));
    }

    private void ProfileNavigation() {
        binding.btnMyProfile.setOnClickListener(v -> {

            // Lấy trạng thái đăng nhập
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();

            if (currentUser == null) {
                // Nếu người dùng chưa đăng nhập, chuyển về trang Login
                Toast.makeText(MainActivity.this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginUserActivity.class));
            }
            // Nếu đã đăng nhập, chuyển sang trang
            else {
                startActivity(new Intent(MainActivity.this, ProfileUserActivity.class));
            }
        });
    }


    private void statusBarColor() {
        Window window=MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.dark_blue));
    }

    private void initRecyclerView() {
        ArrayList<PopularDomain> items=new ArrayList<>();

        items.add(new PopularDomain("Lập trình trên Mobile","ic_dev_mobile",1,5,1700,"Không cần phải là “thần code”, bạn vẫn có thể xây dựng các ứng dụng chạy mượt mà trên Android chỉ với những cú click chuột và vài dòng lệnh. Thầy Dương Thái Bảo – giảng viên cực kỳ tâm huyết (và cũng không thiếu sự đẹp trai) sẽ hướng dẫn bạn từ những bước đầu tiên của lập trình di động cho đến khi bạn có thể tự tay tạo ra ứng dụng cực “ngon”. Hãy chuẩn bị tinh thần vừa học vừa cười, vì “code” trong tay, tiếng cười (mếu) trong lòng!"));
        items.add(new PopularDomain("Kỹ thuật nấu ăn cơ bản","ic_cook_coban",1,5,1200,"Khóa học này cung cấp những kỹ năng cần thiết cho người mới bắt đầu. Bạn sẽ học cách cắt thái, chế biến nguyên liệu và áp dụng các phương pháp nấu ăn đơn giản như xào, luộc, hầm. Đây là nền tảng vững chắc giúp bạn tự tin vào bếp và sáng tạo với những món ăn ngon mỗi ngày."));
        items.add(new PopularDomain("Thiết kế thời trang","ic_design_fashion",1,3,1500,"Khóa học này sẽ giúp bạn hiểu rõ về quy trình thiết kế thời trang, từ việc vẽ phác thảo ý tưởng đến việc hoàn thiện bộ sưu tập. Bạn sẽ được học các kỹ năng may mặc cơ bản và cách thức phát triển một bộ sưu tập thời trang đầy sáng tạo."));
        items.add(new PopularDomain("Ngữ pháp tiếng Anh","ic_course_eng",1,4,1200,"Khóa học ngữ pháp tiếng Anh này sẽ giúp bạn hiểu rõ các cấu trúc ngữ pháp từ cơ bản đến nâng cao. Bạn sẽ cải thiện khả năng viết và nói tiếng Anh một cách chính xác, từ đó giao tiếp tự tin hơn trong mọi tình huống."));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //binding.PopularView.setLayoutManager(gridLayoutManager);
        binding.PopularView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.PopularView.setAdapter(new PopularAdapter(items));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartItemCount();
    }
}