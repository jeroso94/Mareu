package com.example.mareu.controllers;

import com.example.mareu.models.MeetingsModel;
import com.example.mareu.models.RoomsModel;

import java.util.List;

/**
 * Created by JeroSo94 on 05/10/2021.
 */
public interface MeetingApi {

    /**
     * Get all Meetings
     * @return {@link List}
     */
    List<RoomsModel> getAllRooms();

    /**
     * Get all Meetings
     * @return {@link List}
     */
    List<MeetingsModel> getAllMeetings();

    /**
     * Get Meetings based on a date filter
     * @return {@link List}
     */
    List<MeetingsModel> getMeetingsFilterByDate(long date);

    /**
     * Get Meetings based on a room filter
     * @return {@link List}
     */
    List<MeetingsModel> getMeetingsFilterByRoom(String room);

    /**
     * Create a meeting
     * @param oneMeeting
     */

    void createMeeting(MeetingsModel oneMeeting);

    /**
     * Read a meeting
     * @param meetingId
     * @return {@Link MeetingsModel}
     */

    MeetingsModel readMeeting(Long meetingId);

    /**
     * Delete a meeting
     * @param index
     */
    void deleteMeeting(Integer index);
}
