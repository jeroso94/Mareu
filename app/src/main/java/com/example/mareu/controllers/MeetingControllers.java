package com.example.mareu.controllers;

import android.util.Log;

import com.example.mareu.models.MeetingsModel;

import java.util.List;

/**
 * Created by JeroSo94 on 05/10/2021.
 */
public class MeetingControllers implements MeetingApi{
    private List<MeetingsModel> mMeetings = SampleMeetingGenerator.generateBacklog();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeetingsModel> getAllMeetings() {
        return mMeetings;
    }


    /**
     * {@inheritDoc}
     * CRUD - CREATE A MEETING
     * @param oneMeeting
     */

    @Override
    public void createMeeting(MeetingsModel oneMeeting) {
        mMeetings.add(oneMeeting);
        Log.d("MeetingControllers", "createMeeting: " + mMeetings.get(3).getSubject());
    }


    /**
     * {@inheritDoc}
     * CRUD - READ A MEETING
     * @return MeetingsModel
     */
    @Override
    public MeetingsModel readMeeting(Long meetingId) {
        for (MeetingsModel oneMeeting:mMeetings) {
            if (oneMeeting.getId() == meetingId) return oneMeeting;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * CRUD - UPDATE A MEETING
     */


    /**
     * {@inheritDoc}
     * CRUD - DELETE A MEETING
     * @param index
     */
    @Override
    public void deleteMeeting(Integer index) {
        mMeetings.remove(index);
    }

}
