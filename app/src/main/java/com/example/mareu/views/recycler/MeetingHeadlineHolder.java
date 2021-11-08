package com.example.mareu.views.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.controllers.DI;
import com.example.mareu.controllers.DeleteMeetingEvent;
import com.example.mareu.controllers.MeetingApi;
import com.example.mareu.databinding.MeetingHeadlineBinding;
import com.example.mareu.models.MeetingsModel;
import com.example.mareu.models.RoomsModel;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by JeroSo94 on 12/10/2021.
 */
public class MeetingHeadlineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private MeetingHeadlineBinding mMeetingHeadlineBinding;
    private MeetingApi mMeetingApi;
    private MeetingsModel oneMeeting;

    public MeetingHeadlineHolder(@NonNull MeetingHeadlineBinding meetingHeadlineBinding) {
        super(meetingHeadlineBinding.getRoot());
        this.mMeetingHeadlineBinding = meetingHeadlineBinding;
        mMeetingApi = DI.getService();
    }

    public void updateWithMeetingDetails(MeetingsModel meeting){

        oneMeeting = meeting;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar converterCalendar = Calendar.getInstance();
        converterCalendar.setTimeInMillis(meeting.getMeetingDate());

        String requestedRoomName = "";
        for (RoomsModel room:mMeetingApi.getAllRooms()) {
            if  (room.getId() == oneMeeting.getRoomId()) {
                requestedRoomName = room.getName();
            }
        }

        mMeetingHeadlineBinding.meetingHeadlineTitle.setText(meeting.getSubject()
                +" - "+ sdf.format(converterCalendar.getTime())
                +" à "+ meeting.getStartTime()
                +" - " + requestedRoomName );
        mMeetingHeadlineBinding.meetingHeadlineGuestsList.setText(meeting.getGuestsList());
        mMeetingHeadlineBinding.meetingHeadlineDeleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new DeleteMeetingEvent(oneMeeting));
    }
}
