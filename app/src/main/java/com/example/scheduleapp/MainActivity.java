package com.example.scheduleapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleapp.adapters.CabAdapter;
import com.example.scheduleapp.adapters.GroupAdapter;
import com.example.scheduleapp.adapters.TeacherAdapter;
import com.example.scheduleapp.model.Cab;
import com.example.scheduleapp.model.Group;
import com.example.scheduleapp.model.Teacher;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    private TabLayout mainTabLayout; //dark3 / white3
    private static RecyclerView mainRecyclerView;
    private ImageView mainSearchImageView, mainCalendarImageView;
    private CardView searchCardView, profileCardView; //dark3 / white3
    private TextView textView2, textView3;

    private static List<Group> groupsList;
    private static List<Teacher> teachersList;
    private static List<Cab> cabsList;

    public static MainRepository mainRepository;

    public static Date currentDate = new Date(System.currentTimeMillis());
    public static Calendar dateAndTime = Calendar.getInstance();

    private static GroupAdapter searchGroupAdapter;
    private static TeacherAdapter searchTeacherAdapter;
    private static CabAdapter searchCabAdapter;

    private static Context context;
    public static int selected_category = 0;

    // design kostily edition

    private CardView cardView2; //dark2 / white2
    private ConstraintLayout mainBack; //dark1 / white1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        context = this;

        initialize();

        if(SplashScreenActivity.Theme.equals("dark")) {
            getWindow().setStatusBarColor(Color.parseColor("#3F3F3F"));
            cardView2.setCardBackgroundColor(Color.parseColor("#404040")); //dark1
            mainBack.setBackgroundColor(Color.parseColor("#3F3F3F")); //dark2
            searchCardView.setCardBackgroundColor(Color.parseColor("#545454")); //dark3
            profileCardView.setCardBackgroundColor(Color.parseColor("#545454"));
            mainTabLayout.setSelectedTabIndicator(R.drawable.tabs_shape);
            mainTabLayout.setBackground(getResources().getDrawable(R.drawable.menu_shape));
            mainTabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
            textView2.setTextColor(Color.parseColor("#FFFFFF"));
            textView3.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            getWindow().setStatusBarColor(Color.parseColor("#EFEFF9"));
            cardView2.setCardBackgroundColor(Color.parseColor("#EFEFF9")); //white1
            mainBack.setBackgroundColor(Color.parseColor("#FFFFFF")); //white2
            searchCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF")); //white3
            profileCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            mainTabLayout.setSelectedTabIndicator(R.drawable.tabs_shape_light);
            mainTabLayout.setBackground(getResources().getDrawable(R.drawable.menu_shape_light));
            mainTabLayout.setTabTextColors(Color.parseColor("#FF000000"), Color.parseColor("#FFFFFF"));
            textView2.setTextColor(Color.parseColor("#FF000000"));
            textView3.setTextColor(Color.parseColor("#FF000000"));
        }

        MainRepository.updateFavoriteGroups();
        MainRepository.updateFavoriteTeachers();
        MainRepository.updateFavoriteCabs();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MainRepository.updateFavoriteGroups();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                MainRepository.updateFavoriteTeachers();
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                MainRepository.updateFavoriteCabs();
            }
        });
        Thread[] threads = new Thread[]{thread, thread2, thread3};
        threads[0].start();
        threads[1].start();
        threads[2].start();
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        groupsList = mainRepository.getAllGroups();
        teachersList = mainRepository.getAllTeachers();
        cabsList = mainRepository.getAllCabs();

        searchGroupAdapter.setFilteredList(groupsList);
        searchTeacherAdapter.setFilteredList(teachersList);
        searchCabAdapter.setFilteredList(cabsList);

        switchTab(selected_category);
    }
    private void initialize() {
        mainTabLayout = findViewById(R.id.widgetTabLayout);
        mainRecyclerView = findViewById(R.id.mainRecyclerView);
        mainSearchImageView = findViewById(R.id.mainSearchImageView);
        mainCalendarImageView = findViewById(R.id.mainCalendarImageView);
        profileCardView = findViewById(R.id.profileCardView);
        searchCardView = findViewById(R.id.searchCardView);
        cardView2 = findViewById(R.id.cardView2);
        mainBack = findViewById(R.id.mainBack);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        TabLayout.Tab groupTab = mainTabLayout.newTab();
        groupTab.setText("GROUP");
        mainTabLayout.addTab(groupTab);

        TabLayout.Tab teacherTab = mainTabLayout.newTab();
        teacherTab.setText("TEACHER");
        mainTabLayout.addTab(teacherTab);

        TabLayout.Tab cabinetTab = mainTabLayout.newTab();
        cabinetTab.setText("AUDITORIUM");
        mainTabLayout.addTab(cabinetTab);

        MainRepository.updateFavoriteGroups();
        MainRepository.updateFavoriteTeachers();
        MainRepository.updateFavoriteCabs();

        groupsList = mainRepository.getAllGroups();
        teachersList = mainRepository.getAllTeachers();
        cabsList = mainRepository.getAllCabs();

        searchGroupAdapter = new GroupAdapter(this, groupsList);
        searchTeacherAdapter = new TeacherAdapter(this, teachersList);
        searchCabAdapter = new CabAdapter(this, cabsList);

        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        switchTab(0);
        dateAndTime.setTime(currentDate);

        mainTabLayout.setOnTabSelectedListener(this);
        mainSearchImageView.setOnClickListener(this);
        profileCardView.setOnClickListener(this);

        searchCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)
                        MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = inflater.inflate(R.layout.search_popup, null);
                popupView.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.popup_anim_enter));
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                SearchPopup popup = null;
                switch (selected_category) {
                    case 0:
                        popup = new SearchPopup(popupView, width, height, focusable, groupsList, "", "");
                        break;
                    case 1:
                        popup = new SearchPopup(popupView, width, height, focusable, teachersList, "");
                        break;
                    case 2:
                        popup = new SearchPopup(popupView, width, height, focusable, cabsList);
                        break;
                }
                popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popup.showAtLocation(v, Gravity.CENTER, 0, 0);
                popup.setElevation(50);
                if(SplashScreenActivity.Theme.equals("dark")) {
                    getWindow().setStatusBarColor(Color.parseColor("#2C2C2C"));
                } else {
                    getWindow().setStatusBarColor(Color.parseColor("#A4A5AA"));
                }
                popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        if(SplashScreenActivity.Theme.equals("dark")) {
                            getWindow().setStatusBarColor(Color.parseColor("#3F3F3F"));
                        } else {
                            getWindow().setStatusBarColor(Color.parseColor("#EFEFF9"));
                        }
                    }
                });
            }
        });
    }

    public void switchTab(int tab) {
        selected_category = tab;
        switch (tab) {
            case 0:
                mainRecyclerView.setAdapter(searchGroupAdapter);

                break;
            case 1:
                mainRecyclerView.setAdapter(searchTeacherAdapter);

                break;
            case 2:
                mainRecyclerView.setAdapter(searchCabAdapter);

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profileCardView:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.mainSearchImageView:
                mainTabLayout.setOnTabSelectedListener(null);
                searchCardView.setVisibility(View.VISIBLE);
                mainSearchImageView.findFocus();
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switchTab(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        switchTab(tab.getPosition());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        switchTab(tab.getPosition());
    }
}