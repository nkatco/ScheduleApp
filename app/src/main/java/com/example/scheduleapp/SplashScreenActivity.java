package com.example.scheduleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.scheduleapp.DB.DB;
import com.example.scheduleapp.model.Cab;
import com.example.scheduleapp.model.Group;
import com.example.scheduleapp.model.Teacher;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    private static SharedPreferences myPreferences;
    private static SharedPreferences.Editor myEditor;

    public static String Theme;
    private static Context context;

    private static DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setStatusBarColor(Color.parseColor("#000000"));
        getSupportActionBar().hide();
        context = this;

        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myEditor = myPreferences.edit();

        Theme = getPreferenceTheme();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                db = new DB(SplashScreenActivity.this);
            }
        });
        thread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MainActivity.mainRepository = new MainRepository();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).start();
    }
    public static String getPreferenceTheme() {
        int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            return myPreferences.getString("theme", "dark");
        } else {
            return myPreferences.getString("theme", "light");
        }
    }
    public static void setPreferenceTheme(String theme) {
        myEditor.putString("theme", theme);
        myEditor.commit();
    }
    public static void removeFavoriteGroup(Group group) {
        db.removeGroup(String.valueOf(group.getId()));
        try {
            db.removeTable(group.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void removeFavoriteTeacher(Teacher teacher) {
        db.removeTeacher(String.valueOf(teacher.getId()));
        try {
            db.removeTable(teacher.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void removeFavoriteCab(Cab cab) {
        db.removeCab(cab.getCabNum());
        try {
            db.removeTable(cab.getCabNum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void saveGroup(Group group) {
        db.saveGroup(group.getId(), group.getName());
        try {
            db.createTable(group.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void saveTeacher(Teacher teacher) {
        db.saveTeacher(teacher.getId(), teacher.getName());
        try {
            db.createTable(teacher.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void saveCab(Cab cab) {
        db.saveCab(cab.getCabNum(), cab.getName());
        try {
            db.createTable(cab.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<Group> getSavedGroups() {
        Cursor cursor = db.readAllData();
        List<Group> list = new ArrayList<>();
        if(cursor.getCount() != 0) {
            int count = 0;
            while(cursor.moveToNext()) {
                try {
                    list.add(new Group(cursor.getInt(0), cursor.getString(1), false));
                    count++;
                } catch (Exception e) {

                }
            }
        }
        System.out.println("Size favorites groups: " + list.size());
        return list;
    }
    public static List<Teacher> getSavedTeachers() {
        Cursor cursor = db.readAllData2();
        List<Teacher> list = new ArrayList<>();
        if(cursor.getCount() != 0) {
            int count = 0;
            while(cursor.moveToNext()) {
                try {
                    list.add(new Teacher(cursor.getInt(0), cursor.getString(1), false));
                    count++;
                } catch (Exception e) {

                }
            }
        }
        System.out.println("Size favorites teachers: " + list.size());
        return list;
    }
    public static List<Cab> getSavedCabs() {
        List<Cab> list = new ArrayList<>();
        Cursor cursor = db.readAllData3();
        if(cursor.getCount() != 0) {
            int count = 0;
            while(cursor.moveToNext()) {
                try {
                    list.add(new Cab(cursor.getString(0), cursor.getString(1), false));
                    count++;
                } catch (Exception e) {

                }
            }
        }
        System.out.println("Size favorites cabs: " + list.size());
        return list;
    }
}