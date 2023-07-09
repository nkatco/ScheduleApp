package com.example.scheduleapp.adapters;

import static com.example.scheduleapp.MainActivity.dateAndTime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleapp.CabActivity;
import com.example.scheduleapp.MainActivity;
import com.example.scheduleapp.R;
import com.example.scheduleapp.SplashScreenActivity;
import com.example.scheduleapp.model.Cab;
import com.example.scheduleapp.model.Lesson;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchCabAdapter extends RecyclerView.Adapter<SearchCabAdapter.SearchViewHolder> {

    private Context context;
    private List<Cab> list;

    public SearchCabAdapter(Context context, List<Cab> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchCabAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.search_item2, parent, false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCabAdapter.SearchViewHolder holder, int position) {
        holder.searchNameTextView.setText(list.get(position).getName());
        if(list.get(position).isFavorite()) {
            holder.searchStarImageView.setVisibility(View.VISIBLE);
            holder.searchStarImageView.setTag("1");
        } else {
            holder.searchStarImageView.setVisibility(View.INVISIBLE);
            holder.searchStarImageView.setTag("0");
        }
        if(SplashScreenActivity.Theme.equals("dark")) {
            holder.itemSearchCardView.setCardBackgroundColor(Color.parseColor("#545454"));
            holder.searchNameTextView.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.itemSearchCardView.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
            holder.searchNameTextView.setTextColor(Color.parseColor("#FF000000"));
        }
        holder.itemSearchCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Lesson> list2 = null;
                Date date = new Date(dateAndTime.getTimeInMillis());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    list2 = MainActivity.mainRepository.getScheduleByCab(list.get(position).getCabNum(), sdf.format(date));
                    Intent intent = new Intent(context, CabActivity.class);
                    intent.putExtra("cab", list.get(position));
                    Bundle args = new Bundle();
                    args.putSerializable("array_list", (Serializable) list2);
                    intent.putExtra("bundle",args);
                    context.startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setNoItem() {
        this.list = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setFilteredList(List<Cab> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private TextView searchNameTextView;
        private CardView itemSearchCardView;
        private ImageView searchStarImageView;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            searchStarImageView = itemView.findViewById(R.id.searchStarImageView);
            itemSearchCardView = itemView.findViewById(R.id.itemSearchCardView);
            searchNameTextView = itemView.findViewById(R.id.searchNameTextView);
        }
    }
}
