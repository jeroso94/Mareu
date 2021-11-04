package com.example.mareu.controllers;

import com.example.mareu.models.MeetingsModel;
import com.example.mareu.models.RoomsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JeroSo94 on 22/10/2021.
 */
public abstract class SampleMeetingGenerator {
    public static List<RoomsModel> SAMPLE_ROOMS = Arrays.asList(
            new RoomsModel(1978, "Interne", "Mario"),
            new RoomsModel(1990, "Interne", "Luigi"),
            new RoomsModel(2050, "Interne", "Peach")
    );

    public static List<MeetingsModel> SAMPLE_MEETINGS = Arrays.asList(
            new MeetingsModel(1, "KickOff du nouveau projet commercial",1636329600000L, "11:00",
                    "11:50",  1978, "oriana@groupomania.com, fabrice@groupomania.com, leon@groupomania.com"),
            new MeetingsModel(2, "Sprint review du projet Mareu", 1636675200000L, "17:00",
                    "18:00",  1990, "jerome@lamzone.com, lionel@lamzone.com, paul@lamzone.com"),
            new MeetingsModel(3, "Sprint retrospective du projet précédent", 1637280000000L, "18:00",
                    "18:45",  1978, "oriana@groupomania.com, fabrice@groupomania.com, leon@groupomania.com")
            );

    static List<RoomsModel> generateRooms() {
        return new ArrayList<>(SAMPLE_ROOMS);
    }
    static List<MeetingsModel> generateBacklog() {
        return new ArrayList<>(SAMPLE_MEETINGS);
    }
}
