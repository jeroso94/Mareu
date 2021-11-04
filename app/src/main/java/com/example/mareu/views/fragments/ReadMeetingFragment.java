package com.example.mareu.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mareu.controllers.DI;
import com.example.mareu.controllers.MeetingApi;
import com.example.mareu.databinding.FragmentReadMeetingBinding;
import com.example.mareu.models.MeetingsModel;
import com.example.mareu.models.RoomsModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReadMeetingFragment extends Fragment {

    private FragmentReadMeetingBinding mFragmentReadMeetingBinding;
    private MeetingApi mMeetingApi;
    private MeetingsModel oneMeeting;
    private Long mMeetingId;

    public ReadMeetingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param meetingId
     * @return A new instance of fragment ReadMeetingFragment.
     */

    public static ReadMeetingFragment newInstance(Long meetingId) {
        ReadMeetingFragment fragment = new ReadMeetingFragment();
        Bundle args = new Bundle();
        args.putLong("clickedMeetingId", meetingId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMeetingId = getArguments().getLong("clickedMeetingId");
        }
        mMeetingApi = DI.getService();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mFragmentReadMeetingBinding = FragmentReadMeetingBinding.inflate(inflater, container, false);
        oneMeeting = mMeetingApi.readMeeting(mMeetingId);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar converterCalendar = Calendar.getInstance();
        converterCalendar.setTimeInMillis(oneMeeting.getMeetingDate());


        String requestedRoomName = "";
        for (RoomsModel room:mMeetingApi.getAllRooms()) {
            Log.d("ReadMeetingFragment", "onCreateView: " + room.getId() +" | "+ oneMeeting.getRoomId());
            if  (room.getId() == oneMeeting.getRoomId()) {
                requestedRoomName = room.getName();
            }
        }

        mFragmentReadMeetingBinding.meetingSubject.setText(oneMeeting.getSubject());
        mFragmentReadMeetingBinding.meetingDetails.setText("Enregistré pour le " + sdf.format(converterCalendar.getTime())
                + " de " + oneMeeting.getStartTime() + " à " + oneMeeting.getEndTime() +" en salle " + requestedRoomName);
        mFragmentReadMeetingBinding.meetingGuestsList.setText(oneMeeting.getGuestsList());
        return mFragmentReadMeetingBinding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentReadMeetingBinding = null;
    }

}