package com.example.mareu.views.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.databinding.MeetingHeadlineBinding;
import com.example.mareu.models.MeetingsModel;

import java.util.List;

/**
 * Created by JeroSo94 on 12/10/2021.
 */
public class ListOfMeetingsAdapter extends RecyclerView.Adapter<MeetingHeadlineHolder> {

    // FOR DATA
    MeetingHeadlineBinding mMeetingHeadlineBinding;
    private List<MeetingsModel> mMeetings;

    // CONSTRUCTOR
    public ListOfMeetingsAdapter(List<MeetingsModel> meetings) {
        this.mMeetings = meetings; // list of meetings that the screen can display
    }

    @Override
    public MeetingHeadlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        mMeetingHeadlineBinding = MeetingHeadlineBinding.inflate(inflater, parent, false);
        return new MeetingHeadlineHolder(mMeetingHeadlineBinding);
    }

    // UPDATE VIEW HOLDER WITH A RECORDED MEETING
    @Override
    public void onBindViewHolder(MeetingHeadlineHolder holder, int position) {
        Log.d("ListOfMeetingsAdapter", "onBindViewHolder: mMeetings_position"+ position);
        holder.updateWithMeetingDetails(this.mMeetings.get(position));
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.mMeetings.size();
    }

    // PICKUP A MEETING FROM THE DISPLAYING LIST
    public MeetingsModel getMeeting(int position){
        return this.mMeetings.get(position);
    }
}
