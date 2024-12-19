package com.example.project167.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SaleCourse extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SaleCourse.db";  // Tên cơ sở dữ liệu
    private static final int DATABASE_VERSION = 1; // Phiên bản hiện tại

    //Table và column của category
    public static final String CATEGORIES = "categories";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_PICTURE = "category_picPath";

    //Table và column của course
    public static final String COURSES = "courses";
    public static final String COURSE_ID = "course_id";
    public static final String COURSE_NAME = "course_name";
    public static final String COURSE_PRICE = "course_price";
    public static final String COURSE_PICTURE = "course_picPath";
    public static final String COURSE_SCORE = "course_score";
    public static final String COURSE_COUNT_REVIEW = "course_count_review";
    public static final String COURSE_DESCRIPTION = "course_description";

    // Table và column của reviews
    public static final String REVIEWS = "reviews";
    public static final String REVIEW_ID = "review_id";
    public static final String REVIEW_COURSE_ID = "course_id";
    public static final String REVIEW_USER_NAME = "user_name";
    public static final String REVIEW_RATING = "rating";
    public static final String REVIEW_COMMENT = "comment";

    // Câu lệnh tạo bảng category
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + CATEGORIES + " (" +
            CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORY_NAME + " TEXT NOT NULL , " +
            CATEGORY_PICTURE + " TEXT NOT NULL);";

    // Câu lệnh tạo bảng course
    private static final String CREATE_TABLE_COURSES = "CREATE TABLE " + COURSES + " (" +
            COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COURSE_NAME + " TEXT NOT NULL, " +
            COURSE_PRICE + " INTEGER NOT NULL, " +
            COURSE_PICTURE + " TEXT NOT NULL, " +
            COURSE_SCORE + " REAL NOT NULL DEFAULT 0.0, " +
            COURSE_COUNT_REVIEW + " INTEGER NOT NULL DEFAULT 0, " +
            COURSE_DESCRIPTION + " TEXT NOT NULL, " +
            "category_id INTEGER, " +
            "FOREIGN KEY(category_id) REFERENCES " + CATEGORIES + "(" + CATEGORY_ID + ") ON DELETE SET NULL);";


    // Câu lệnh tạo bảng reviews
    private static final String CREATE_TABLE_REVIEWS = "CREATE TABLE reviews (" +
            "review_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "course_id INTEGER NOT NULL, " +
            "user_name TEXT NOT NULL, " +
            "rating INTEGER CHECK(rating >= 1 AND rating <= 5), " +
            "comment TEXT, " +
            "FOREIGN KEY(course_id) REFERENCES courses(course_id) ON DELETE CASCADE);"; // Xóa khóa học sẽ xóa review liên quan

    public SaleCourse(@Nullable Context context) {
        super(context, "SaleCourse", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng categories
        db.execSQL(CREATE_TABLE_CATEGORIES);

        // Tạo bảng khóa học
        db.execSQL(CREATE_TABLE_COURSES);

        // Tạo bảng reviews
        db.execSQL(CREATE_TABLE_REVIEWS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Xóa bảng nếu tồn tại trước đó
        db.execSQL("DROP TABLE IF EXISTS " + REVIEWS);
        db.execSQL("DROP TABLE IF EXISTS " + COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES);

        //Tạo lại bảng với version mới
        onCreate(db);
    }

    public void BasicCategory(SQLiteDatabase db){

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + CATEGORIES, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            if(count == 0){

                ContentValues values = new ContentValues();

                values.put(CATEGORY_NAME, "Nấu ăn");
                values.put(CATEGORY_PICTURE, "ic_cate_cook");
                db.insert(CATEGORIES, null, values);

                values.put(CATEGORY_NAME, "Thiết kế");
                values.put(CATEGORY_PICTURE, "ic_cate_design");
                db.insert(CATEGORIES, null, values);

                values.put(CATEGORY_NAME, "Thời trang");
                values.put(CATEGORY_PICTURE, "ic_cate_fashion");
                db.insert(CATEGORIES, null, values);

                values.put(CATEGORY_NAME, "Ngôn ngữ");
                values.put(CATEGORY_PICTURE, "ic_cate_language");
                db.insert(CATEGORIES, null, values);

                values.put(CATEGORY_NAME, "Lập trình");
                values.put(CATEGORY_PICTURE, "ic_cate_dev");
                db.insert(CATEGORIES, null, values);
            }
        }
    }

    public void BasicCourse(SQLiteDatabase db){

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + COURSES, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();

            if(count == 0){

                ContentValues values = new ContentValues();

                values.put(COURSE_NAME, "Kỹ thuật nấu ăn cơ bản");
                values.put(COURSE_PRICE, 1200);
                values.put(COURSE_PICTURE, "ic_cook_coban");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học này cung cấp những kỹ năng cần thiết cho người mới bắt đầu. Bạn sẽ học cách cắt thái, chế biến nguyên liệu và áp dụng các phương pháp nấu ăn đơn giản như xào, luộc, hầm. Đây là nền tảng vững chắc giúp bạn tự tin vào bếp và sáng tạo với những món ăn ngon mỗi ngày.");
                values.put("category_id", 1);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Ẩm thực Châu Á");
                values.put(COURSE_PRICE, 1500);
                values.put(COURSE_PICTURE, "ic_cook_chaua");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khám phá sự đa dạng của ẩm thực Châu Á qua các món ăn nổi tiếng từ Nhật Bản, Trung Quốc, Hàn Quốc và các quốc gia khác. Khóa học sẽ hướng dẫn bạn từng bước để chế biến những món ăn mang đậm phong vị Á Đông, từ sushi, ramen đến kimchi.");
                values.put("category_id", 1);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Nấu ăn thuần chay");
                values.put(COURSE_PRICE, 1100);
                values.put(COURSE_PICTURE, "ic_cook_vegan");
                values.put(COURSE_SCORE, 5.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Dành cho những ai yêu thích chế độ ăn thuần chay hoặc muốn thử nghiệm với các nguyên liệu thực vật. Khóa học này sẽ giới thiệu cho bạn cách chế biến các món ăn thuần chay dinh dưỡng và ngon miệng, từ salad tươi mát đến các món ăn chính đầy hương vị.");
                values.put("category_id", 1);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Bánh ngọt và tráng miệng");
                values.put(COURSE_PRICE, 1800);
                values.put(COURSE_PICTURE, "ic_cook_dessert");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học này sẽ đưa bạn vào thế giới của các món bánh ngọt, từ bánh kem mềm mịn, bánh quy giòn tan đến các món tráng miệng thanh mát. Hãy chuẩn bị sẵn sàng để sáng tạo và thể hiện tài năng làm bánh của mình ngay tại nhà.");
                values.put("category_id", 1);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Thiết kế đồ họa");
                values.put(COURSE_PRICE, 1700);
                values.put(COURSE_PICTURE, "ic_design_graphic");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học này sẽ giúp bạn nắm vững các kỹ năng thiết kế đồ họa cơ bản và nâng cao. Bạn sẽ học cách sử dụng các phần mềm thiết kế nổi tiếng như Photoshop và Illustrator để tạo ra các sản phẩm thiết kế bắt mắt, từ logo, tờ rơi đến poster quảng cáo.");
                values.put("category_id", 2);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Thiết kế nội thất");
                values.put(COURSE_PRICE, 2000);
                values.put(COURSE_PICTURE, "ic_design_noithat");
                values.put(COURSE_SCORE, 3.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Nếu bạn yêu thích trang trí không gian sống, khóa học này là dành cho bạn. Bạn sẽ học cách lựa chọn màu sắc, bố trí đồ đạc, kết hợp các vật liệu và tạo ra không gian sống đẹp mắt, tiện nghi. Hãy biến mỗi ngôi nhà thành một tác phẩm nghệ thuật với thiết kế nội thất chuyên nghiệp.");
                values.put("category_id", 2);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Thiết kế thời trang");
                values.put(COURSE_PRICE, 1500);
                values.put(COURSE_PICTURE, "ic_design_fashion");
                values.put(COURSE_SCORE, 3.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học này sẽ giúp bạn hiểu rõ về quy trình thiết kế thời trang, từ việc vẽ phác thảo ý tưởng đến việc hoàn thiện bộ sưu tập. Bạn sẽ được học các kỹ năng may mặc cơ bản và cách thức phát triển một bộ sưu tập thời trang đầy sáng tạo.");
                values.put("category_id", 2);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Thiết kế web");
                values.put(COURSE_PRICE, 1900);
                values.put(COURSE_PICTURE, "ic_design_web");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học thiết kế web sẽ giúp bạn tạo ra giao diện người dùng (UI) và trải nghiệm người dùng (UX) hoàn hảo. Bạn sẽ được học cách tạo ra các trang web dễ sử dụng, hấp dẫn và có khả năng tương tác cao, từ các công cụ thiết kế đến việc triển khai giao diện.");
                values.put("category_id", 2);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Lịch sử thời trang");
                values.put(COURSE_PRICE, 1000);
                values.put(COURSE_PICTURE, "ic_fashion_history");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học này sẽ dẫn bạn qua một hành trình thú vị về sự phát triển của ngành thời trang. Từ những bộ trang phục cổ đại đến các xu hướng thời trang hiện đại, bạn sẽ hiểu rõ về ảnh hưởng văn hóa và xã hội đối với các bộ sưu tập nổi tiếng qua các thập kỷ.");
                values.put("category_id", 3);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Phong cách thời trang cá nhân");
                values.put(COURSE_PRICE, 1400);
                values.put(COURSE_PICTURE, "ic_fashion_personal");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khám phá cách xây dựng phong cách thời trang độc đáo của riêng bạn. Khóa học này sẽ giúp bạn nhận diện cơ thể, lựa chọn trang phục phù hợp và phối đồ một cách tinh tế để tôn vinh cá tính và gu thẩm mỹ của bạn.");
                values.put("category_id", 3);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "May mặc cơ bản");
                values.put(COURSE_PRICE, 1300);
                values.put(COURSE_PICTURE, "ic_fashion_may");
                values.put(COURSE_SCORE, 3.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Nếu bạn yêu thích may mặc, khóa học này là bước khởi đầu hoàn hảo. Bạn sẽ học cách sử dụng máy may, đọc và cắt mẫu, cũng như kỹ thuật may đơn giản để tự tay tạo ra những món đồ thời trang như áo, quần, và váy.");
                values.put("category_id", 3);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Tổ chức sự kiện thời trang");
                values.put(COURSE_PRICE, 1800);
                values.put(COURSE_PICTURE, "ic_fashion_event");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học này sẽ cung cấp những kiến thức về cách tổ chức một sự kiện thời trang từ A đến Z. Bạn sẽ học cách quản lý các show diễn, chuẩn bị cho buổi trình diễn bộ sưu tập và thu hút sự chú ý từ giới truyền thông và khách mời.");
                values.put("category_id", 3);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Ngữ pháp tiếng Anh");
                values.put(COURSE_PRICE, 1200);
                values.put(COURSE_PICTURE, "ic_course_eng");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học ngữ pháp tiếng Anh này sẽ giúp bạn hiểu rõ các cấu trúc ngữ pháp từ cơ bản đến nâng cao. Bạn sẽ cải thiện khả năng viết và nói tiếng Anh một cách chính xác, từ đó giao tiếp tự tin hơn trong mọi tình huống.");
                values.put("category_id", 4);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Tiếng Pháp cơ bản");
                values.put(COURSE_PRICE, 1300);
                values.put(COURSE_PICTURE, "ic_course_france");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Bắt đầu học tiếng Pháp với khóa học này, nơi bạn sẽ làm quen với các từ vựng cơ bản, ngữ pháp và các câu giao tiếp thông dụng. Khóa học này là nền tảng tuyệt vời giúp bạn xây dựng khả năng giao tiếp và khám phá văn hóa Pháp.");
                values.put("category_id", 4);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Tiếng Nhật giao tiếp");
                values.put(COURSE_PRICE, 1500);
                values.put(COURSE_PICTURE, "ic_course_japn");
                values.put(COURSE_SCORE, 3.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học tiếng Nhật giao tiếp cung cấp những bài học dễ hiểu về các câu giao tiếp cơ bản trong cuộc sống hàng ngày, từ việc chào hỏi đến việc mua sắm, đi du lịch. Hãy bắt đầu hành trình học tiếng Nhật để khám phá một nền văn hóa độc đáo.");
                values.put("category_id", 4);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Dịch thuật và biên dịch");
                values.put(COURSE_PRICE, 1900);
                values.put(COURSE_PICTURE, "ic_course_dichthuat");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học này sẽ trang bị cho bạn kỹ năng dịch thuật và biên dịch chuyên nghiệp. Bạn sẽ học cách dịch các văn bản từ ngôn ngữ này sang ngôn ngữ khác, từ các tài liệu đơn giản đến các bài báo, tài liệu chuyên ngành.");
                values.put("category_id", 4);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Lập trình Python");
                values.put(COURSE_PRICE, 1600);
                values.put(COURSE_PICTURE, "ic_dev_python");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học này sẽ giúp bạn làm quen với ngôn ngữ lập trình Python, một trong những ngôn ngữ dễ học và mạnh mẽ nhất. Bạn sẽ học cách viết các chương trình đơn giản, xử lý dữ liệu và giải quyết các vấn đề lập trình cơ bản. Python là ngôn ngữ tuyệt vời cho người mới bắt đầu và cũng được sử dụng rộng rãi trong khoa học dữ liệu, trí tuệ nhân tạo, và phát triển web.");
                values.put("category_id", 5);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "lập trình JavaScript");
                values.put(COURSE_PRICE, 1800);
                values.put(COURSE_PICTURE, "ic_dev_js");
                values.put(COURSE_SCORE, 5.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học này sẽ giúp bạn xây dựng ứng dụng web động với JavaScript. Bạn sẽ học cách sử dụng JavaScript để tạo các trang web tương tác, thao tác với DOM, xử lý sự kiện và sử dụng các thư viện phổ biến như jQuery. Đây là khóa học lý tưởng cho những ai muốn làm việc trong lĩnh vực phát triển web.");
                values.put("category_id", 5);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Lập trình C++");
                values.put(COURSE_PRICE, 2000);
                values.put(COURSE_PICTURE, "ic_dev_c__");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học lập trình C++ này sẽ giúp bạn hiểu và làm việc với một trong những ngôn ngữ lập trình mạnh mẽ và phổ biến. Bạn sẽ học các khái niệm như biến, hàm, mảng, lớp đối tượng và các thuật toán cơ bản, cùng với các kỹ thuật tối ưu hóa mã nguồn trong C++. C++ thường được sử dụng trong phát triển phần mềm hệ thống, game, và ứng dụng hiệu năng cao.");
                values.put("category_id", 5);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, " Lập trình với React Native");
                values.put(COURSE_PRICE, 1900);
                values.put(COURSE_PICTURE, "ic_dev_react");
                values.put(COURSE_SCORE, 4.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Khóa học này sẽ dạy bạn cách phát triển ứng dụng di động cho cả Android và iOS bằng React Native. Bạn sẽ học cách sử dụng JavaScript và React để xây dựng các ứng dụng di động mượt mà và hiệu quả, từ việc thiết kế giao diện người dùng đến việc kết nối với các API.");
                values.put("category_id", 5);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Lập trình trên Mobile");
                values.put(COURSE_PRICE, 1700);
                values.put(COURSE_PICTURE, "ic_dev_mobile");
                values.put(COURSE_SCORE, 5.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Không cần phải là “thần code”, bạn vẫn có thể xây dựng các ứng dụng chạy mượt mà trên Android chỉ với những cú click chuột và vài dòng lệnh. Thầy Dương Thái Bảo – giảng viên cực kỳ tâm huyết (và cũng không thiếu sự đẹp trai) sẽ hướng dẫn bạn từ những bước đầu tiên của lập trình di động cho đến khi bạn có thể tự tay tạo ra ứng dụng cực “ngon”. Hãy chuẩn bị tinh thần vừa học vừa cười, vì “code” trong tay, tiếng cười (mếu) trong lòng!");
                values.put("category_id", 5);
                db.insert(COURSES, null, values);
            }
        }
    }

    // Lấy tất cả khóa học từ cơ sở dữ liệu
    public Cursor getAllCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + COURSES, null);
    }

    // Lấy tất cả danh mục từ cơ sở dữ liệu
    public Cursor getAllCategory(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + CATEGORIES, null);
    }

    // Lấy khóa học có id của danh mục
    public Cursor getCoursesByCategoryId(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM courses WHERE category_id = ?", new String[]{String.valueOf(categoryId)});
    }

    //Xóa danh mục ra khỏi data
    public boolean deleteCategory(int categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete("categories", "category_id = ?", new String[]{String.valueOf(categoryId)});
        db.close();
        return rowsAffected > 0; // Trả về true nếu xóa thành công
    }
}
