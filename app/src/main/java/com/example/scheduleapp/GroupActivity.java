package com.example.scheduleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scheduleapp.adapters.LessonsAdapter;
import com.example.scheduleapp.model.Group;
import com.example.scheduleapp.model.Lesson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GroupActivity extends AppCompatActivity {

    private RecyclerView lessonsRecyclerView;
    private CardView lessBack, lessDate, lessonCardView, cardView3;
    private TextView dateTextView, lessNameTextView, textView4;
    private ImageView lessFavStarImageView;
    private ConstraintLayout lessonsBack;

    private static List<Lesson> list;
    private static Group group;
    private static Date currentDate = new Date(System.currentTimeMillis());
    private static Calendar dateAndTime = Calendar.getInstance();
    private static LessonsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons_group);
        getSupportActionBar().hide();

        Intent i = getIntent();
        ArrayList<Lesson> list1 = (ArrayList<Lesson>) i.getSerializableExtra("array_list");
        this.list = list1;

        group = (Group) i.getSerializableExtra("group");

        initialize();
        if(SplashScreenActivity.Theme.equals("dark")) {
            getWindow().setStatusBarColor(Color.parseColor("#3F3F3F"));
            lessonsBack.setBackgroundColor(Color.parseColor("#404040"));
            lessBack.setCardBackgroundColor(Color.parseColor("#545454"));
            lessDate.setCardBackgroundColor(Color.parseColor("#545454"));
            cardView3.setCardBackgroundColor(Color.parseColor("#404040"));
            dateTextView.setTextColor(Color.parseColor("#FFFFFF"));
            textView4.setTextColor(Color.parseColor("#FFFFFF"));
            lessNameTextView.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            getWindow().setStatusBarColor(Color.parseColor("#EFEFF9"));
            lessonsBack.setBackgroundColor(Color.parseColor("#FFFFFF"));
            lessBack.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            lessDate.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            cardView3.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
            dateTextView.setTextColor(Color.parseColor("#FF000000"));
            textView4.setTextColor(Color.parseColor("#FF000000"));
            lessNameTextView.setTextColor(Color.parseColor("#FF000000"));
        }
    }
    private void initialize() {
        lessonsRecyclerView = findViewById(R.id.lessonsRecyclerView);
        lessBack = findViewById(R.id.lessBack);
        lessDate = findViewById(R.id.lessDate);
        dateTextView = findViewById(R.id.dateTextView);
        lessNameTextView = findViewById(R.id.lessNameTextView);
        lessFavStarImageView = findViewById(R.id.lessFavStarImageView);
        lessonCardView = findViewById(R.id.lessonCardView);
        lessonsBack = findViewById(R.id.lessonsBack);
        cardView3 = findViewById(R.id.cardView3);
        textView4 = findViewById(R.id.textView4);

        currentDate = new Date(System.currentTimeMillis());
        dateAndTime = Calendar.getInstance();

        lessNameTextView.setText(group.getName());
        if(group.isFavorite()) {
            lessFavStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.star2));
        } else {
            lessFavStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.star1));
        }
        lessFavStarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(group.isFavorite()) {
                    SplashScreenActivity.removeFavoriteGroup(group);
                    lessFavStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.star1));
                    group.setFavorite(false);
                } else {
                    SplashScreenActivity.saveGroup(group);
                    lessFavStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.star2));
                    group.setFavorite(true);
                }
            }
        });

        adapter = new LessonsAdapter(this, list);
        lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lessonsRecyclerView.setAdapter(adapter);

        if(list.size() == 0) {
            adapter.setNoItems();
            lessonCardView.setVisibility(View.VISIBLE);
        } else {
            lessonCardView.setVisibility(View.INVISIBLE);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        dateTextView.setText(sdf.format(currentDate));

        lessDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(v);
            }
        });
        lessBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setDate(View v) {
        int style = R.style.blackDialogTheme;
        if(SplashScreenActivity.Theme.equals("dark")) {
            style = R.style.blackDialogTheme;
        } else {
            style = R.style.whiteDialogTheme;
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, style, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            currentDate = new Date(dateAndTime.getTimeInMillis());
            dateTextView.setText(sdf.format(currentDate));

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                list = MainActivity.mainRepository.getScheduleByGroup(group.getId(), sdf2.format(currentDate));
                adapter.newItems(list);
                if(list.size() == 0) {
                    adapter.setNoItems();
                    lessonCardView.setVisibility(View.VISIBLE);
                } else {
                    lessonCardView.setVisibility(View.INVISIBLE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}