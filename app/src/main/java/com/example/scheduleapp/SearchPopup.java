package com.example.scheduleapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduleapp.adapters.SearchCabAdapter;
import com.example.scheduleapp.adapters.SearchGroupAdapter;
import com.example.scheduleapp.adapters.SearchTeacherAdapter;
import com.example.scheduleapp.model.Cab;
import com.example.scheduleapp.model.Group;
import com.example.scheduleapp.model.Teacher;

import java.util.ArrayList;
import java.util.List;

public class SearchPopup extends PopupWindow implements View.OnClickListener {

    private RecyclerView groupsRecyclerView;
    private EditText groupsSearchView;
    private TextView goGroupsTextView;
    private CardView popupSearchCardView, popupBack, cardView4;
    private CardView goBackCardView;
    private Context context;

    private SearchGroupAdapter searchGroupAdapter;
    private RelativeLayout ProductPopupRelativeLayout;
    private SearchTeacherAdapter searchTeacherAdapter;
    private SearchCabAdapter searchCabAdapter;

    public SearchPopup(View contentView, int width, int height, boolean focusable, List<Cab> list) {
        super(contentView, width, height, focusable);

        context = contentView.getContext();
        searchCabAdapter = new SearchCabAdapter(context, list);

        goGroupsTextView = contentView.findViewById(R.id.goGroupsTextView);
        groupsRecyclerView = contentView.findViewById(R.id.groupsRecyclerView);
        groupsSearchView = contentView.findViewById(R.id.groupsSearchView);
        popupSearchCardView = contentView.findViewById(R.id.searchCardView);
        ProductPopupRelativeLayout = contentView.findViewById(R.id.schedule);
        goBackCardView = contentView.findViewById(R.id.goBackCardView);
        popupBack = contentView.findViewById(R.id.popupBack);
        cardView4 = contentView.findViewById(R.id.cardView4);
        popupSearchCardView = contentView.findViewById(R.id.searchCardView);

        groupsSearchView.getBackground().setColorFilter(Color.parseColor("#287EFF"), PorterDuff.Mode.MULTIPLY);

        if(SplashScreenActivity.Theme.equals("dark")) {
            popupBack.setCardBackgroundColor(Color.parseColor("#3D3D3D"));
            cardView4.setCardBackgroundColor(Color.parseColor("#404040"));
            popupSearchCardView.setCardBackgroundColor(Color.parseColor("#545454"));
            goBackCardView.setCardBackgroundColor(Color.parseColor("#3D3D3D"));
            groupsSearchView.setBackgroundColor(Color.parseColor("#545454"));
            groupsSearchView.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            popupBack.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            cardView4.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
            popupSearchCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            goBackCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            groupsSearchView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            groupsSearchView.setTextColor(Color.parseColor("#FF000000"));
        }

        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(contentView.getContext()));
        groupsRecyclerView.setAdapter(searchCabAdapter);

        groupsSearchView.clearFocus();

        groupsSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterListCab(String.valueOf(groupsSearchView.getText()), list);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ProductPopupRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        goBackCardView.setOnClickListener(this);
    }

    public SearchPopup(View contentView, int width, int height, boolean focusable, List<Teacher> list, String a) {
        super(contentView, width, height, focusable);

        context = contentView.getContext();
        searchTeacherAdapter = new SearchTeacherAdapter(context, list);

        goGroupsTextView = contentView.findViewById(R.id.goGroupsTextView);
        groupsRecyclerView = contentView.findViewById(R.id.groupsRecyclerView);
        groupsSearchView = contentView.findViewById(R.id.groupsSearchView);
        popupSearchCardView = contentView.findViewById(R.id.searchCardView);
        ProductPopupRelativeLayout = contentView.findViewById(R.id.schedule);
        goBackCardView = contentView.findViewById(R.id.goBackCardView);
        popupBack = contentView.findViewById(R.id.popupBack);
        cardView4 = contentView.findViewById(R.id.cardView4);
        popupSearchCardView = contentView.findViewById(R.id.searchCardView);

        if(SplashScreenActivity.Theme.equals("dark")) {
            popupBack.setCardBackgroundColor(Color.parseColor("#3D3D3D"));
            cardView4.setCardBackgroundColor(Color.parseColor("#404040"));
            popupSearchCardView.setCardBackgroundColor(Color.parseColor("#545454"));
            goBackCardView.setCardBackgroundColor(Color.parseColor("#3D3D3D"));
            groupsSearchView.setBackgroundColor(Color.parseColor("#545454"));
            groupsSearchView.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            popupBack.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            cardView4.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
            popupSearchCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            goBackCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            groupsSearchView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            groupsSearchView.setTextColor(Color.parseColor("#FF000000"));
        }

        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(contentView.getContext()));
        groupsRecyclerView.setAdapter(searchTeacherAdapter);

        groupsSearchView.clearFocus();
        groupsSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterListTeacher(String.valueOf(groupsSearchView.getText()), list);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ProductPopupRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        goGroupsTextView.setOnClickListener(this);
    }

    public SearchPopup(View contentView, int width, int height, boolean focusable, List<Group> list, String a, String a2) {
        super(contentView, width, height, focusable);

        context = contentView.getContext();
        searchGroupAdapter = new SearchGroupAdapter(context, list);

        goGroupsTextView = contentView.findViewById(R.id.goGroupsTextView);
        groupsRecyclerView = contentView.findViewById(R.id.groupsRecyclerView);
        groupsSearchView = contentView.findViewById(R.id.groupsSearchView);
        popupSearchCardView = contentView.findViewById(R.id.searchCardView);
        ProductPopupRelativeLayout = contentView.findViewById(R.id.schedule);
        goBackCardView = contentView.findViewById(R.id.goBackCardView);
        popupBack = contentView.findViewById(R.id.popupBack);
        cardView4 = contentView.findViewById(R.id.cardView4);
        popupSearchCardView = contentView.findViewById(R.id.searchCardView);

        if(SplashScreenActivity.Theme.equals("dark")) {
            popupBack.setCardBackgroundColor(Color.parseColor("#3D3D3D"));
            cardView4.setCardBackgroundColor(Color.parseColor("#404040"));
            popupSearchCardView.setCardBackgroundColor(Color.parseColor("#545454"));
            goBackCardView.setCardBackgroundColor(Color.parseColor("#3D3D3D"));
            groupsSearchView.setBackgroundColor(Color.parseColor("#545454"));
            groupsSearchView.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            popupBack.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            cardView4.setCardBackgroundColor(Color.parseColor("#EFEFF9"));
            popupSearchCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            goBackCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            groupsSearchView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            groupsSearchView.setTextColor(Color.parseColor("#FF000000"));
        }

        groupsRecyclerView.setLayoutManager(new LinearLayoutManager(contentView.getContext()));
        groupsRecyclerView.setAdapter(searchGroupAdapter);

        groupsSearchView.clearFocus();

        groupsSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterListGroup(String.valueOf(charSequence.toString()), list);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ProductPopupRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        goGroupsTextView.setOnClickListener(this);
    }

    private void filterListGroup(String newText, List<Group> list) {
        List<Group> filteredList = new ArrayList<>();
        for(Group group : list) {
            if(group.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(group);
            }
        }
        if(!filteredList.isEmpty()) {
            searchGroupAdapter.setFilteredList(filteredList);
        } else {
            searchGroupAdapter.setNoItem();
        }
    }
    private void filterListTeacher(String newText, List<Teacher> list) {
        List<Teacher> filteredList = new ArrayList<>();
        for(Teacher teacher : list) {
            if(teacher.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(teacher);
            }
        }
        if(!filteredList.isEmpty()) {
            searchTeacherAdapter.setFilteredList(filteredList);
        } else {
            searchTeacherAdapter.setNoItem();
        }
    }
    private void filterListCab(String newText, List<Cab> list) {
        List<Cab> filteredList = new ArrayList<>();
        for(Cab cab : list) {
            if(cab.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(cab);
            }
        }
        if(!filteredList.isEmpty()) {
            searchCabAdapter.setFilteredList(filteredList);
        } else {
            searchCabAdapter.setNoItem();
        }
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
