package com.example.scheduleapp;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TimeSchedulePopup extends PopupWindow implements View.OnClickListener {

    private RecyclerView groupsRecyclerView;
    private CardView popupBack;
    private TextView goGroupsTextView;
    private CardView goBackCardView;
    private ImageView scheduleImageView;
    private RelativeLayout schedulePopup;
    private Context context;

    public TimeSchedulePopup(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);

        context = contentView.getContext();

        schedulePopup = contentView.findViewById(R.id.schedulePopup);
        goGroupsTextView = contentView.findViewById(R.id.scheduleGoGroupsTextView);
        popupBack = contentView.findViewById(R.id.schedulePopupBack);
        groupsRecyclerView = contentView.findViewById(R.id.groupsRecyclerView);
        goBackCardView = contentView.findViewById(R.id.scheduleGoBackCardView);
        scheduleImageView = contentView.findViewById(R.id.scheduleImageView);

        if(SplashScreenActivity.Theme.equals("dark")) {
            popupBack.setCardBackgroundColor(Color.parseColor("#3D3D3D"));
            goBackCardView.setCardBackgroundColor(Color.parseColor("#3D3D3D"));
            scheduleImageView.setImageResource(R.drawable.blackschedule1);
        } else {
            popupBack.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            goBackCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            scheduleImageView.setImageResource(R.drawable.whiteschedule1);
        }
        schedulePopup.setOnClickListener(this);
        goBackCardView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        dismiss();
    }
}
