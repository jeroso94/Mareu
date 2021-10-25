package com.example.mareu.views.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.models.MeetingsModel;
import com.example.mareu.models.RoomsModel;

import java.util.List;

/**
 * Created by JeroSo94 on 12/10/2021.
 */
public class MeetingHeadlineAdapter extends RecyclerView.Adapter<MeetingHeadlineHolder> {

    public interface Listener {
        void onClickDeleteButton(int position);
    }

    // FOR COMMUNICATION
    private final Listener mCallback;

    // FOR DATA
    private List<MeetingsModel> mMeetings;

    // CONSTRUCTOR
    public MeetingHeadlineAdapter(List<MeetingsModel> meetings, Listener callback) {
        this.mMeetings = meetings;
        this.mCallback = callback;
    }

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
        Log.d("MeetingHeadlineAdapter", "onBindViewHolder: mMeetings_position"+ position);
        holder.updateWithMeetingDetails(this.mMeetings.get(position), this.mCallback);
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
