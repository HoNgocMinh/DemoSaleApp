package com.example.project167.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SaleCourse extends SQLiteOpenHelper {

    private final ContentValues values = new ContentValues();
    private static final String DATABASE_NAME = "SaleCourse.db";  // Tên cơ sở dữ liệu
    private static final int DATABASE_VERSION = 1; // Phiên bản hiện tại

    //Table và column của category
    public static final String CATEGORIES = "categories";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_PICTURE = "category_picPath";

    // Câu lệnh tạo bảng category
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + CATEGORIES + " (" +
            CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORY_NAME + " TEXT, " +
            CATEGORY_PICTURE + " TEXT);";

    //Table và column của course
    public static final String COURSES = "courses";
    public static final String COURSE_ID = "course_id";
    public static final String COURSE_NAME = "course_name";
    public static final String COURSE_PRICE = "course_price";
    public static final String COURSE_PICTURE = "course_picPath";

    // Cập nhật câu lệnh tạo bảng courses để thêm cột category_id
    private static final String CREATE_TABLE_COURSES = "CREATE TABLE " + COURSES + " (" +
            COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COURSE_NAME + " TEXT, " +
            COURSE_PRICE + " INTEGER, " +
            COURSE_PICTURE + " TEXT, " +
            "category_id INTEGER, " +
            "FOREIGN KEY(" + "category_id" + ") REFERENCES " + CATEGORIES + "(" + CATEGORY_ID + "));";

    public SaleCourse(@Nullable Context context) {
        super(context, "SaleCourse", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng categories
        db.execSQL(CREATE_TABLE_CATEGORIES);

        // Tạo bảng khóa học
        db.execSQL(CREATE_TABLE_COURSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xóa bảng nếu tồn tại trước đó
        db.execSQL("DROP TABLE IF EXISTS " + COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORIES);

        //Tạo lại bảng với versionn mới
        onCreate(db);
    }

    public void BasicCategory(@NonNull SQLiteDatabase db){

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + COURSES, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            if(count == 0){
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

    public void BasicCourse(@NonNull SQLiteDatabase db){

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + COURSES, null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();

            if(count == 0){
                values.put(COURSE_NAME, "Kỹ thuật nấu ăn cơ bản");
                values.put(COURSE_PRICE, 150);
                values.put(COURSE_PICTURE, "ic_1");
                values.put("category_id", 1);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Lập trình trên thiết bị di động");
                values.put(COURSE_PRICE, 69);
                values.put(COURSE_PICTURE, "ic_2");
                values.put("category_id",5);
                db.insert(COURSES, null, values);

                values.put(COURSE_NAME, "Thiết kế nội thất");
                values.put(COURSE_PRICE, 150);
                values.put(COURSE_PICTURE, "ic_3");
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
}
