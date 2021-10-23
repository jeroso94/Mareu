package com.example.mareu.controllers;

/**
 * Created by JeroSo94 on 21/10/2021.
 */


/**
 * Dependency injector to get instance of services
 */
public class DI {
    private static MeetingApi service = new MeetingControllers();

    /**
     * Get an instance on @{@link MeetingApi}
     * @return
     */
    public static MeetingApi getService() {
        return service;
    }
}
