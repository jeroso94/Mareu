package com.example.mareu.controllers;

import com.example.mareu.models.MeetingsModel;

import java.util.List;

/**
 * Created by JeroSo94 on 05/10/2021.
 */
public interface MeetingApi {

    /**
     * Get all Meetings
     * @return {@link List}
     */
    List<MeetingsModel> getAllMeetings();

    /**
     * Create a meeting
     * @param oneMeeting
     */

    void createMeeting(MeetingsModel oneMeeting);

    /**
     * Delete a meeting
     * @param oneMeetingId
     */
    void deleteMeeting(Integer oneMeetingId);
}
