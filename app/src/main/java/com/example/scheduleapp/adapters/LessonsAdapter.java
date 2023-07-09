package com.example.scheduleapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleapp.R;
import com.example.scheduleapp.SplashScreenActivity;
import com.example.scheduleapp.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonsViewHolder> {

    private Context context;
    private List<Lesson> list;
    private boolean rasp = false;

    public LessonsAdapter(Context context, List<Lesson> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LessonsAdapter.LessonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.lesson_item, parent, false);
        return new LessonsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsAdapter.LessonsViewHolder holder, int position) {
        holder.numberTextView.setText(String.valueOf(list.get(position).getLess().getNum()));
        if(list.get(position).getLess().getTeacherName().length() > 70) {
            holder.teacherTextView.setText(list.get(position).getLess().getTeacherName().substring(0, 70) + "...");
        } else {
            holder.teacherTextView.setText(list.get(position).getLess().getTeacherName());
        }
        if(list.get(position).getLess().getTitle().length() > 70) {
            holder.lessonTextView.setText(list.get(position).getLess().getTitle().substring(0, 70) + "...");
        } else {
            holder.lessonTextView.setText(list.get(position).getLess().getTitle());
        }
        holder.cabTextView.setText(list.get(position).getLess().getCab());
        if(SplashScreenActivity.Theme.equals("dark")) {
            holder.lessonCardView.setCardBackgroundColor(Color.parseColor("#545454"));
            holder.numberTextView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.teacherTextView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.lessonTextView.setTextColor(Color.parseColor("#FFFFFF"));
            holder.cabTextView.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.lessonCardView.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
            holder.numberTextView.setTextColor(Color.parseColor("#FF000000"));
            holder.teacherTextView.setTextColor(Color.parseColor("#FF000000"));
            holder.lessonTextView.setTextColor(Color.parseColor("#FF000000"));
            holder.cabTextView.setTextColor(Color.parseColor("#FF000000"));
        }
    }

    public void newItems(List<Lesson> list) {
        this.list = list;
        rasp = false;
        notifyDataSetChanged();
    }
    public void setNoItems() {
        this.list = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LessonsViewHolder extends RecyclerView.ViewHolder {

        private TextView numberTextView, teacherTextView, lessonTextView, cabTextView;
        private CardView lessonCardView;

        public LessonsViewHolder(@NonNull View itemView) {
            super(itemView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            teacherTextView = itemView.findViewById(R.id.teacherTextView);
            lessonTextView = itemView.findViewById(R.id.lessonTextView);
            cabTextView = itemView.findViewById(R.id.cabTextView);
            lessonCardView = itemView.findViewById(R.id.lessonCardView);
        }
    }
}
