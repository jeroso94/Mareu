package com.example.mareu.views.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;

/**
 * Created by JeroSo94 on 12/10/2021.
 */
public class MeetingHeadlineAdapter extends RecyclerView.Adapter<MeetingHeadlineHolder> {

    @Override
    public MeetingHeadlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_meeting_headline, parent, false);

        return new MeetingHeadlineHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A RECORDED MEETING
    @Override
    public void onBindViewHolder(MeetingHeadlineHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
