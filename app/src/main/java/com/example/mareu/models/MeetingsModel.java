package com.example.mareu.models;

/**
 * Created by JeroSo94 on 04/10/2021.
 */

/**
 * Model object representing a MeetingsModel
 */
public class MeetingsModel {

    /**
     * Identifier
     */
    private long mId;
    /**
     * Subject of the meeting
     */
    private String mSubject;
    /**
     * Day of the meeting
     */
    private String mMeetingDate;
    /**
     * Start Time
     */
    private String mStartTime;
    /**
     * End Time
     */
    private String mEndTime;
    /**
     * Identifier of the room meeting
     */
    private long mRoomId;
    /**
     * Guests
     */
    private String mGuestsList;


    /**
     * Constructor
     * @param id
     * @param subject
     * @param meetingDate
     * @param startTime
     * @param endTime
     * @param roomId
     * @param guestsList
     */
    public MeetingsModel(long id, String subject, String meetingDate, String startTime, String endTime, long roomId, String guestsList){
        this.mId = id;
        this.mSubject = subject;
        this.mMeetingDate = meetingDate;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mRoomId = roomId;
        this.mGuestsList = guestsList;
    }


    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public String getMeetingDate() {
        return mMeetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        mMeetingDate = meetingDate;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public long getRoomId() {
        return mRoomId;
    }

    public void setRoomId(long roomId) {
        mRoomId = roomId;
    }

    public String getGuestsList() {
        return mGuestsList;
    }

    public void setGuestsList(String guestsList) {
        mGuestsList = guestsList;
    }
}



