package com.example.scheduleapp.widgets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;

import com.example.scheduleapp.MainRepository;
import com.example.scheduleapp.R;
import com.example.scheduleapp.adapters.CabAdapter;
import com.example.scheduleapp.adapters.GroupAdapter;
import com.example.scheduleapp.adapters.TeacherAdapter;
import com.example.scheduleapp.model.Cab;
import com.example.scheduleapp.model.Group;
import com.example.scheduleapp.model.Teacher;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;


public class WidgetActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    private Intent resultValue;

    private RecyclerView widgetRecyclerView;
    private TabLayout widgetTabLayout;

    private static List<Group> groupsList;
    private static List<Teacher> teachersList;
    private static List<Cab> cabsList;

    public static MainRepository mainRepository;

    private static GroupAdapter searchGroupAdapter;
    private static TeacherAdapter searchTeacherAdapter;
    private static CabAdapter searchCabAdapter;

    public final static String WIDGET_PREF = "widget_pref";
    public final static String WIDGET_CATEGORY = "widget_category_";
    public final static String WIDGET_ID = "widget_id_";
    public static int selected_category = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);

        setResult(RESULT_CANCELED, resultValue);

        setContentView(R.layout.activity_lessons_widget);
        getSupportActionBar().hide();

        widgetRecyclerView = findViewById(R.id.widgetRecyclerView);
        widgetTabLayout = findViewById(R.id.widgetTabLayout);

        TabLayout.Tab groupTab = widgetTabLayout.newTab();
        groupTab.setText("GROUP");
        widgetTabLayout.addTab(groupTab);

        TabLayout.Tab teacherTab = widgetTabLayout.newTab();
        teacherTab.setText("TEACHER");
        widgetTabLayout.addTab(teacherTab);

        TabLayout.Tab cabinetTab = widgetTabLayout.newTab();
        cabinetTab.setText("CABINET");
        widgetTabLayout.addTab(cabinetTab);

        try {
            mainRepository = new MainRepository();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        MainRepository.updateFavoriteGroups();
        MainRepository.updateFavoriteTeachers();
        MainRepository.updateFavoriteCabs();

        groupsList = mainRepository.getAllGroups();
        teachersList = mainRepository.getAllTeachers();
        cabsList = mainRepository.getAllCabs();

        searchGroupAdapter = new GroupAdapter(this, groupsList);
        searchTeacherAdapter = new TeacherAdapter(this, teachersList);
        searchCabAdapter = new CabAdapter(this, cabsList);

        widgetRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        switchTab(0);
        widgetTabLayout.setOnTabSelectedListener(this);
    }

    public void switchTab(int tab) {
        selected_category = tab;
        switch (tab) {
            case 0:
                widgetRecyclerView.setAdapter(searchGroupAdapter);
                break;
            case 1:
                widgetRecyclerView.setAdapter(searchTeacherAdapter);
                break;
            case 2:
                widgetRecyclerView.setAdapter(searchCabAdapter);
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