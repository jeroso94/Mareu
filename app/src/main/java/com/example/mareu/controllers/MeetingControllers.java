package com.example.mareu.controllers;

import android.util.Log;

import com.example.mareu.models.MeetingsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JeroSo94 on 05/10/2021.
 */
public class MeetingControllers implements MeetingApi{
    private List<MeetingsModel> mMeetings = SampleMeetingGenerator.generateBacklog();

    /**
     * {@inheritDoc}
     * @param oneMeeting
     */

    @Override
    public void createMeeting(MeetingsModel oneMeeting) {
        mMeetings.add(oneMeeting);
        Log.d("MeetingControllers", "createMeeting: " + mMeetings.get(3).getSubject());
    }
}
