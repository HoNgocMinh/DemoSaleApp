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
                values.put(CATEGORY_PICTURE, "cat1");
                db.insert(CATEGORIES, null, values);

                values.put(CATEGORY_NAME, "Thiết kế");
                values.put(CATEGORY_PICTURE, "cat2");
                db.insert(CATEGORIES, null, values);

                values.put(CATEGORY_NAME, "Thời trang");
                values.put(CATEGORY_PICTURE, "cat3");
                db.insert(CATEGORIES, null, values);

                values.put(CATEGORY_NAME, "Ngôn ngữ");
                values.put(CATEGORY_PICTURE, "cat4");
                db.insert(CATEGORIES, null, values);

                values.put(CATEGORY_NAME, "Lập trình");
                values.put(CATEGORY_PICTURE, "cat5");
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
                values.put(COURSE_PRICE, 150);
                values.put(COURSE_PICTURE, "ic_1");
                values.put(COURSE_SCORE, 1.0);
                values.put(COURSE_COUNT_REVIEW,1);
                values.put(COURSE_DESCRIPTION,"Học cùng thầy BẺO");
                values.put("category_id", 1);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Lập trình Mobile");
                values.put(COURSE_PRICE, 69);
                values.put(COURSE_PICTURE, "ic_2");
                values.put(COURSE_SCORE, 2.0);
                values.put(COURSE_COUNT_REVIEW,2);
                values.put(COURSE_DESCRIPTION,"Học cùng cô Y");
                values.put("category_id",5);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Thiết kế nội thất");
                values.put(COURSE_PRICE, 150);
                values.put(COURSE_PICTURE, "ic_3");
                values.put(COURSE_SCORE, 3.0);
                values.put(COURSE_COUNT_REVIEW,3);
                values.put(COURSE_DESCRIPTION,"Học cùng thầy X");
                values.put("category_id",2);
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
