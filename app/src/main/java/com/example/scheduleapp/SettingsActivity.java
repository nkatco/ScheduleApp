package com.example.scheduleapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private CardView backCardView, themeCard, cardView4;
    private ConstraintLayout settingsBack;
    private TextView textView;
    private Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        initialize();
        if(SplashScreenActivity.Theme.equals("dark")) {
            getWindow().setStatusBarColor(Color.parseColor("#3F3F3F"));
            settingsBack.setBackgroundColor(Color.parseColor("#404040"));
            cardView4.setCardBackgroundColor(Color.parseColor("#404040"));
            backCardView.setCardBackgroundColor(Color.parseColor("#545454"));
            themeCard.setCardBackgroundColor(Color.parseColor("#545454"));
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            switch1.setChecked(false);
        } else {
            getWindow().setStatusBarColor(Color.parseColor("#EFEFF9"));
            settingsBack.setBackgroundColor(Color.parseColor("#FFFFFF"));
            cardView4.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
            backCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            themeCard.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
            textView.setTextColor(Color.parseColor("#FF000000"));
            switch1.setChecked(true);
        }
    }

    private void initialize() {
        backCardView = findViewById(R.id.backCardView);
        themeCard = findViewById(R.id.themeCard);
        cardView4 = findViewById(R.id.cardView4);
        settingsBack = findViewById(R.id.settingsBack);
        switch1 = findViewById(R.id.switch1);
        textView = findViewById(R.id.textView);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    SplashScreenActivity.setPreferenceTheme("light");
                } else {
                    SplashScreenActivity.setPreferenceTheme("dark");
                }
                SplashScreenActivity.Theme = SplashScreenActivity.getPreferenceTheme();
                if(SplashScreenActivity.Theme.equals("dark")) {
                    getWindow().setStatusBarColor(Color.parseColor("#3F3F3F"));
                    settingsBack.setBackgroundColor(Color.parseColor("#404040"));
                    cardView4.setCardBackgroundColor(Color.parseColor("#404040"));
                    backCardView.setCardBackgroundColor(Color.parseColor("#545454"));
                    themeCard.setCardBackgroundColor(Color.parseColor("#545454"));
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    switch1.setChecked(false);
                } else {
                    getWindow().setStatusBarColor(Color.parseColor("#EFEFF9"));
                    settingsBack.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    cardView4.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
                    backCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    themeCard.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
                    textView.setTextColor(Color.parseColor("#FF000000"));
                    switch1.setChecked(true);
                }
            }
        });

        backCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}