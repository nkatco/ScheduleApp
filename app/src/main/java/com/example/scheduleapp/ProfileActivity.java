package com.example.scheduleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.scheduleapp.adapters.CabAdapter;
import com.example.scheduleapp.adapters.GroupAdapter;
import com.example.scheduleapp.adapters.TeacherAdapter;
import com.example.scheduleapp.model.Cab;
import com.example.scheduleapp.model.Group;
import com.example.scheduleapp.model.Teacher;

import java.util.List;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView groupsRecyclerView, teachersRecyclerView, cabsRecyclerView;
    private ConstraintLayout profileBack;
    private CardView backCardView, cardView4, settingsCardView;

    private static List<Group> groupsList;
    private static List<Teacher> teachersList;
    private static List<Cab> cabsList;

    private static GroupAdapter searchGroupAdapter;
    private static TeacherAdapter searchTeacherAdapter;
    private static CabAdapter searchCabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        initialize();

        if(SplashScreenActivity.Theme.equals("dark")) {
            getWindow().setStatusBarColor(Color.parseColor("#3F3F3F"));
            profileBack.setBackgroundColor(Color.parseColor("#404040"));
            cardView4.setCardBackgroundColor(Color.parseColor("#404040"));
            backCardView.setCardBackgroundColor(Color.parseColor("#545454"));
            settingsCardView.setCardBackgroundColor(Color.parseColor("#545454"));
        } else {
            getWindow().setStatusBarColor(Color.parseColor("#EFEFF9"));
            profileBack.setBackgroundColor(Color.parseColor("#FFFFFF"));
            cardView4.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
            backCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            settingsCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                groupsList = SplashScreenActivity.getSavedGroups();
                for (Group group : groupsList) {
                    group.setFavorite(true);
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                teachersList = SplashScreenActivity.getSavedTeachers();
                for (Teacher teacher : teachersList) {
                    teacher.setFavorite(true);
                }
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                cabsList = SplashScreenActivity.getSavedCabs();
                for (Cab cab : cabsList) {
                    cab.setFavorite(true);
                }
            }
        });
        Thread[] threads = new Thread[]{thread1, thread2, thread3};
        threads[0].start();
        threads[1].start();
        threads[2].start();
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void initialize()  {
        backCardView = findViewById(R.id.backCardView);
        groupsRecyclerView = findViewById(R.id.profileGoupsRecyclerVIew);
        teachersRecyclerView = findViewById(R.id.profileTeachersRecyclerView);
        cabsRecyclerView = findViewById(R.id.profileCabsRecyclerView);
        profileBack = findViewById(R.id.profileBack);
        cardView4 = findViewById(R.id.cardView4);
        settingsCardView = findViewById(R.id.settingsCardView);

        groupsList = SplashScreenActivity.getSavedGroups();
        for (Group group : groupsList) {
            group.setFavorite(true);
        }
        teachersList = SplashScreenActivity.getSavedTeachers();
        for (Teacher teacher : teachersList) {
            teacher.setFavorite(true);
        }
        cabsList = SplashScreenActivity.getSavedCabs();
        for (Cab cab : cabsList) {
            cab.setFavorite(true);
        }

        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teachersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cabsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        groupsRecyclerView.setAdapter(new GroupAdapter(this, groupsList));
        teachersRecyclerView.setAdapter(new TeacherAdapter(this, teachersList));
        cabsRecyclerView.setAdapter(new CabAdapter(this, cabsList));

        backCardView.setOnClickListener(this);
        settingsCardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backCardView:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.settingsCardView:
                Intent intent1 = new Intent(this, SettingsActivity.class);
                startActivity(intent1);
                break;
        }
    }
}