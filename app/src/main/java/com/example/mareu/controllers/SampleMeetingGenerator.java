package com.example.mareu.controllers;

import com.example.mareu.models.MeetingsModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JeroSo94 on 22/10/2021.
 */
public abstract class SampleMeetingGenerator {
    public static List<MeetingsModel> SAMPLE_MEETINGS = Arrays.asList(
            new MeetingsModel(1, "KickOff du nouveau projet commercial", "08 Nov. 2021", "11:00",
                    "11:50",  1978, "oriana@groupomania.com, fabrice@groupomania.com, leon@groupomania.com"),
            new MeetingsModel(2, "Sprint review du projet Mareu", "12 Nov. 2021", "17:00",
                    "18:00",  1990, "jerome@lamzone.com, lionel@lamzone.com, paul@lamzone.com"),
            new MeetingsModel(3, "Sprint retrospective du projet précédent", "19 Nov. 2021", "18:00",
                    "18:45",  1978, "oriana@groupomania.com, fabrice@groupomania.com, leon@groupomania.com")
            );
    static List<MeetingsModel> generateBacklog() {
        return new ArrayList<>(SAMPLE_MEETINGS);
    }
}
