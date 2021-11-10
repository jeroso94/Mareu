package com.example.mareu;

import com.example.mareu.controllers.DI;
import com.example.mareu.controllers.MeetingApi;
import com.example.mareu.controllers.SampleMeetingGenerator;
import com.example.mareu.models.MeetingsModel;
import com.example.mareu.models.RoomsModel;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeetingControllersTest {
    private MeetingApi service;

    @Before
    public void setup() { service = DI.getTestDedicatedService(); }

    /**
     * Ensure methods "getAllRooms" and "getAllMeetings" are returning the full list of sample items
     */
    @Test
    public void getAllRoomsWithSuccess() {
        // Prepare reference sample
        List<RoomsModel> expectedRooms = SampleMeetingGenerator.SAMPLE_ROOMS;
        // Run simulation
        List<RoomsModel> rooms = service.getAllRooms();
        // Evaluate result
        assertThat(rooms, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedRooms.toArray()));
    }

    @Test
    public void getAllMeetingsWithSuccess() {
        // Prepare reference sample
        List<MeetingsModel> expectedMeetings = SampleMeetingGenerator.SAMPLE_MEETINGS;
        // Run simulation
        List<MeetingsModel> meetings = service.getAllMeetings();
        // Evaluate result
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    /**
     * Ensure methods "getMeetingsFilterByDate" and "getMeetingsFilterByRooms" is returning a list filtered based on a provided criteria
     */
    @Test
    public void getMeetingsFilterByDateWithSuccess() {
        // Prepare reference sample
        List<MeetingsModel> meetings = service.getAllMeetings();
        long meetingDate = 1637280000000L; // for Nov. 19th, 2021
        // Run simulation
        List<MeetingsModel> filteredMeetings = service.getMeetingsFilterByDate(meetingDate);
        // Evaluate result
        assertEquals(1, filteredMeetings.size());
    }

    @Test
    public void getMeetingsFilterByRoomWithSuccess() {
        // Prepare reference sample
        List<MeetingsModel> meetings = service.getAllMeetings();
        String room = "Mario";
        // Run simulation
        List<MeetingsModel> filteredMeetings = service.getMeetingsFilterByRoom(room);
        // Evaluate result
        assertEquals(2, filteredMeetings.size());
    }


    /**
     * Ensure method "createMeeting" is adding a new meeting in the meetings list
     */
    @Test
    public void createMeetingWithSuccess() {
        // Prepare reference sample
        String meetingSubject = "First week Christmas deals";
        long meetingDate = 1638486000000L; // for Dec. 3rd, 2021
        String meetingStartTime = "09:30";
        String meetingEndTime = "10:15";
        long roomId = 2050L; // for "Peach" room
        String meetingGuestsList = "pwalker@lamzone.com, amorris@lamzone.com, jdoe@lamzone.com";

        MeetingsModel oneMeeting = new MeetingsModel(
                System.currentTimeMillis(),
                meetingSubject,
                meetingDate,
                meetingStartTime,
                meetingEndTime,
                roomId,
                meetingGuestsList
        );
        // Run simulation
        service.createMeeting(oneMeeting);
        // Evaluate result
        // Assuming it already exists a sample of 3 meetings in the list.
        assertEquals(4, service.getAllMeetings().size());
    }

    /**
     * Ensure method "readMeeting" is retrieving from the list the right meeting
     */
    @Test
    public void readMeetingWithSuccess() {
        // Prepare reference sample
        MeetingsModel oneMeeting = service.getAllMeetings().get(0);
        // Run simulation
        MeetingsModel requestedMeeting = service.readMeeting(oneMeeting.getId());
        // Evaluate result
        assertEquals(requestedMeeting, oneMeeting);
    }

    /**
     * Ensure method "deleteMeeting" is deleting the meeting from the list
     */
    @Test
    public void deleteMeetingWithSuccess() {
        // Prepare reference sample
        MeetingsModel oneMeeting = service.getAllMeetings().get(0);
        // Run simulation
        service.deleteMeeting(oneMeeting);
        // Evaluate result
        // Assuming it already exists a sample of 3 meetings in the list.
        assertEquals(2, service.getAllMeetings().size());
    }




    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}