package com.example.mareu.views.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.databinding.FragmentMeetingHeadlineBinding;
import com.example.mareu.models.MeetingsModel;
import com.example.mareu.models.RoomsModel;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JeroSo94 on 12/10/2021.
 */
public class MeetingHeadlineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private FragmentMeetingHeadlineBinding mFragmentMeetingHeadlineBinding;
    private WeakReference<ListOfMeetingsAdapter.Listener> callbackWeakRef;
    private List<RoomsModel> sampleRooms;

    public MeetingHeadlineHolder(View itemView) {
        super(itemView);
        Context context = itemView.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        mFragmentMeetingHeadlineBinding = FragmentMeetingHeadlineBinding.inflate(inflater);
    }

    public void updateWithMeetingDetails(MeetingsModel oneMeeting, ListOfMeetingsAdapter.Listener callback){

        sampleRooms = Arrays.asList(
                new RoomsModel(1978, "Interne", "Mario"),
                new RoomsModel(1990, "Interne", "Luigi"),
                new RoomsModel(2050, "Interne", "Peach")
        );
        String requestedRoomName = "";
        for (RoomsModel room:sampleRooms) {
            if  (room.getId() == oneMeeting.getRoomId()) {
                requestedRoomName = room.getName();
            }
        }

        Log.d("MeetingHeadlineHolder", "updateWithMeetingDetails: "+ oneMeeting.getSubject());

        mFragmentMeetingHeadlineBinding.meetingHeadlineTitle.setText(oneMeeting.getSubject());
        mFragmentMeetingHeadlineBinding.meetingHeadlineGuestsList.setText(oneMeeting.getGuestsList());
        mFragmentMeetingHeadlineBinding.meetingHeadlineDeleteButton.setOnClickListener(this);
        this.callbackWeakRef = new WeakReference<>(callback);
    }

    @Override
    public void onClick(View view) {
        ListOfMeetingsAdapter.Listener callback = callbackWeakRef.get();
        if (callback != null) callback.onClickDeleteButton(getAdapterPosition());
    }
}
