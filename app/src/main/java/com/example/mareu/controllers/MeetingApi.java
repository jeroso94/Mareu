package com.example.mareu.controllers;

import com.example.mareu.models.MeetingsModel;

/**
 * Created by JeroSo94 on 05/10/2021.
 */
public interface MeetingApi {

    /**
     * Create a meeting
     * @param oneMeeting
     */

    void createMeeting(MeetingsModel oneMeeting);
}
