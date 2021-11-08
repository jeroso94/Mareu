package com.example.mareu.controllers;

import android.util.Log;

import com.example.mareu.models.MeetingsModel;
import com.example.mareu.models.RoomsModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by JeroSo94 on 05/10/2021.
 */
public class MeetingControllers implements MeetingApi{
    private List<MeetingsModel> mMeetings = SampleMeetingGenerator.generateBacklog();
    private List<RoomsModel> mRooms = SampleMeetingGenerator.generateRooms();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoomsModel> getAllRooms() {
        return mRooms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MeetingsModel> getAllMeetings() {
        return mMeetings;
    }

    /**
     * {@inheritDoc}
     * @param oneDate
     */
    @Override
    public List<MeetingsModel> getMeetingsFilterByDate(long oneDate) {
        List<MeetingsModel> result = new ArrayList<>();

        Calendar selectedDateCalendar = Calendar.getInstance();
        selectedDateCalendar.setTimeInMillis(oneDate);

        for (int i = 0; i < mMeetings.size(); i++) {
            Calendar meetingDateCalendar = Calendar.getInstance();
            meetingDateCalendar.setTimeInMillis(mMeetings.get(i).getMeetingDate());
            boolean sameDay = selectedDateCalendar.get(Calendar.DAY_OF_YEAR) == meetingDateCalendar.get(Calendar.DAY_OF_YEAR)
                    && selectedDateCalendar.get(Calendar.YEAR) == meetingDateCalendar.get(Calendar.YEAR);
            if (sameDay) result.add(mMeetings.get(i));
        }

        return result;
    }

    @Override
    public List<MeetingsModel> getMeetingsFilterByRoom(String room) {
        List<MeetingsModel> result = new ArrayList<>();

        for (int i = 0; i < mRooms.size(); i++) {
            if (mRooms.get(i).getName().toLowerCase().contains(room.toLowerCase())){
                for (MeetingsModel meeting: mMeetings) {
                    if (meeting.getRoomId() == mRooms.get(i).getId()) {
                        result.add(meeting);
                    }
                }
            }
        }
        return result;
    }


    /**
     * {@inheritDoc}
     * CRUD - CREATE A MEETING
     * @param oneMeeting
     */

    @Override
    public void createMeeting(MeetingsModel oneMeeting) {
        mMeetings.add(oneMeeting);
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
     * @param oneMeeting
     */
    @Override
    public void deleteMeeting(MeetingsModel oneMeeting) {
        mMeetings.remove(oneMeeting);
    }

}