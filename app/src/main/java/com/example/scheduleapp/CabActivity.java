package com.example.scheduleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.scheduleapp.adapters.LessonsAdapter;
import com.example.scheduleapp.model.Cab;
import com.example.scheduleapp.model.Lesson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CabActivity extends AppCompatActivity {

    private RecyclerView lessonsRecyclerView;
    private CardView lessBack, lessDate, lessonCardView, cardView3, timeScheduleCardView;
    private TextView dateTextView, lessNameTextView, textView4, textView6;
    private ImageView lessFavStarImageView;
    private ConstraintLayout lessonsBack;

    private static List<Lesson> list;
    private static Cab cab;
    private static Date currentDate = new Date(System.currentTimeMillis());
    private static Calendar dateAndTime = Calendar.getInstance();
    private static LessonsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_cab);
        getSupportActionBar().hide();

        Intent i = getIntent();
        ArrayList<Lesson> list1 = (ArrayList<Lesson>) i.getSerializableExtra("array_list");
        this.list = list1;

        cab = (Cab) i.getSerializableExtra("cab");

        initialize();
        if(SplashScreenActivity.Theme.equals("dark")) {
            getWindow().setStatusBarColor(Color.parseColor("#3F3F3F"));
            timeScheduleCardView.setCardBackgroundColor(Color.parseColor("#545454"));
            lessonsBack.setBackgroundColor(Color.parseColor("#404040"));
            lessBack.setCardBackgroundColor(Color.parseColor("#545454"));
            lessDate.setCardBackgroundColor(Color.parseColor("#545454"));
            cardView3.setCardBackgroundColor(Color.parseColor("#404040"));
            dateTextView.setTextColor(Color.parseColor("#FFFFFF"));
            textView4.setTextColor(Color.parseColor("#FFFFFF"));
            lessNameTextView.setTextColor(Color.parseColor("#FFFFFF"));
            textView6.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            getWindow().setStatusBarColor(Color.parseColor("#EFEFF9"));
            lessonsBack.setBackgroundColor(Color.parseColor("#FFFFFF"));
            lessBack.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            lessDate.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            cardView3.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
            dateTextView.setTextColor(Color.parseColor("#FF000000"));
            textView4.setTextColor(Color.parseColor("#FF000000"));
            lessNameTextView.setTextColor(Color.parseColor("#FF000000"));
            textView6.setTextColor(Color.parseColor("#FF000000"));
            timeScheduleCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }


    private void initialize() {
        timeScheduleCardView = findViewById(R.id.timeScheduleCardView);
        textView6 = findViewById(R.id.textView6);
        lessonsRecyclerView = findViewById(R.id.cabLessonsRecyclerView);
        lessBack = findViewById(R.id.cabLessBack);
        lessDate = findViewById(R.id.cabLessDate);
        dateTextView = findViewById(R.id.cabDateTextView);
        lessFavStarImageView = findViewById(R.id.cabLessFavStarImageView);
        lessNameTextView = findViewById(R.id.cabLessNameTextView);
        lessonCardView = findViewById(R.id.cabLessonCardView);
        lessonsBack = findViewById(R.id.cabLessonsBack);
        cardView3 = findViewById(R.id.cabCardView3);
        textView4 = findViewById(R.id.cabTextView4);

        currentDate = new Date(System.currentTimeMillis());
        dateAndTime = Calendar.getInstance();

        lessNameTextView.setText(cab.getName());
        if(cab.isFavorite()) {
            lessFavStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.star2));
        } else {
            lessFavStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.star1));
        }
        lessFavStarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cab.isFavorite()) {
                    SplashScreenActivity.removeFavoriteCab(cab);
                    lessFavStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.star1));
                    cab.setFavorite(false);
                } else {
                    SplashScreenActivity.saveCab(cab);
                    lessFavStarImageView.setImageDrawable(getResources().getDrawable(R.drawable.star2));
                    cab.setFavorite(true);
                }
            }
        });

        adapter = new LessonsAdapter(this, list);
        lessonsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lessonsRecyclerView.setAdapter(adapter);

        if(list != null) {
            if (list.size() == 0) {
                adapter.setNoItems();
                lessonCardView.setVisibility(View.VISIBLE);
            } else {
                lessonCardView.setVisibility(View.INVISIBLE);
            }
        } else {
            adapter.setNoItems();
            lessonCardView.setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(CabActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        timeScheduleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        CabActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = inflater.inflate(R.layout.time_schedule_popup, null);
                popupView.setAnimation(AnimationUtils.loadAnimation(CabActivity.this, R.anim.popup_anim_enter));
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true;
                TimeSchedulePopup popup = null;
                popup = new TimeSchedulePopup(popupView, width, height, focusable);
                popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popup.showAtLocation(view, Gravity.CENTER, 0, 0);
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
                list = MainActivity.mainRepository.getScheduleByCab(cab.getCabNum(), sdf2.format(currentDate));
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