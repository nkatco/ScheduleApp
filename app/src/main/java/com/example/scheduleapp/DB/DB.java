package com.example.scheduleapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.scheduleapp.model.Lesson;

public class DB extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "SGKScheduleDB";
    private static final int DB_VER = 2;

    private static final String table_name = "favorites_groups";
    private static final String column_id = "_id";
    private static final String column_group = "group_id";

    private static final String table2_name = "favorites_teachers";
    private static final String column2_id = "_id";
    private static final String column2_teacher = "teacher_id";

    private static final String table3_name = "favorites_cabs";
    private static final String column3_CabNum = "_id";
    private static final String column3_CabName = "cab_CabName";

    public DB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
    }
    public void createTable(String name) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "CREATE TABLE " + name +
                " (date TEXT, title TEXT, num TEXT, teacherName TEXT, groupName TEXT, cab TEXT);";
        db.execSQL(query);
    }
    public void removeTable(String name) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE " + name + ";";

        db.execSQL(query);
    }
    public void saveLesson(String name, Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("date", lesson.getDate());
        cv.put("title", lesson.getLess().getTitle());
        cv.put("num", lesson.getLess().getNum());
        cv.put("teacherName", lesson.getLess().getTeacherName());
        cv.put("groupName", lesson.getLess().getNameGroup());
        cv.put("cab", lesson.getLess().getCab());

        long result = db.insert(table_name, null, cv);
    }
    public void updateLesson(String name, Lesson lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + name + "SET date = " + lesson.getDate()
                + ", title = " + lesson.getLess().getTitle()
                + ", num = " + lesson.getLess().getNum()
                + ", teacherName = " + lesson.getLess().getTeacherName()
                + ", groupName = " + lesson.getLess().getNameGroup()
                + ", cab = " + lesson.getLess().getCab() +
                "WHERE cab ='" + lesson.getLess().getNum() + "';";
        db.execSQL(query);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name +
                " (" + column_id + " TEXT, " +
                column_group + " TEXT);";
        db.execSQL(query);

        String query2 = "CREATE TABLE " + table2_name +
                " (" + column2_id + " TEXT, " +
                column2_teacher + " TEXT);";
        db.execSQL(query2);

        String query3 = "CREATE TABLE " + table3_name +
                " (" + column3_CabNum + " TEXT, " +
                column3_CabName + " TEXT);";
        db.execSQL(query3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1 && newVersion == 2) {
            db.execSQL("DROP TABLE IF EXISTS " + table_name);
            db.execSQL("DROP TABLE IF EXISTS " + table2_name);
            db.execSQL("DROP TABLE IF EXISTS " + table3_name);
        }
        onCreate(db);
    }



    public void saveGroup(int group_id, String group_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(column_id, group_id);
        cv.put(column_group, group_name);
        long result = db.insert(table_name, null, cv);
        System.out.println("Preserved group: " + group_name + " code: " + result);
    }
    public void saveTeacher(int teacher_id, String teacher_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column2_id, teacher_id);
        cv.put(column2_teacher, teacher_name);
        long result = db.insert(table2_name, null, cv);
        System.out.println("Preserved teacher: " + teacher_name + " code: " + result);
    }
    public void saveCab(String cab_Num, String cab_Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column3_CabNum, cab_Num);
        cv.put(column3_CabName, cab_Name);
        long result = db.insert(table3_name, null, cv);
        System.out.println("Preserved cab: " + cab_Name + " code: " + result);
    }
    public void removeGroup(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table_name + " WHERE " + column_id + "='" + row_id + "';");
        System.out.println("Removed group: " + row_id);
    }
    public void removeTeacher(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table2_name + " WHERE " + column2_id + "='" + row_id + "';");
        System.out.println("Removed teacher: " + row_id);
    }
    public void removeCab(String row_cabNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table3_name + " WHERE " + column3_CabNum + "='" + row_cabNum + "';");
        System.out.println("Removed cab: " + row_cabNum);
    }
    public Cursor readAllDataByName(String name) {
        String query = "SELECT * FROM " + name;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor readAllData() {
        String query = "SELECT * FROM " + table_name;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor readAllData2() {
        String query = "SELECT * FROM " + table2_name;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor readAllData3() {
        String query = "SELECT * FROM " + table3_name;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
