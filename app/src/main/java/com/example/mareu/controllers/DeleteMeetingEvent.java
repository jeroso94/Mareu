package com.example.mareu.controllers;

import android.util.Log;

import com.example.mareu.models.MeetingsModel;

/**
 * Created by JeroSo94 on 05/11/2021.
 * Event fired when a user deletes a Meeting
 */
public class DeleteMeetingEvent {
    /**
     * Meeting to delete
     */
    public MeetingsModel oneMeeting;

    /**
     * Constructor.
     * @param meeting
     */
    public DeleteMeetingEvent(MeetingsModel meeting) {
        this.oneMeeting = meeting;
    }
}
