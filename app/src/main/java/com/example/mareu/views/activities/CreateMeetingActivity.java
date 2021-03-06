package com.example.mareu.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;


import com.example.mareu.R;
import com.example.mareu.controllers.DI;
import com.example.mareu.controllers.MeetingApi;
import com.example.mareu.databinding.ActivityCreateMeetingBinding;
import com.example.mareu.models.MeetingsModel;
import com.example.mareu.models.RoomsModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class CreateMeetingActivity extends AppCompatActivity {

    private ActivityCreateMeetingBinding mActivityCreateMeetingBinding;
    private MaterialDatePicker mDatePicker;
    private MaterialTimePicker mStartTimePicker;
    private MaterialTimePicker mEndTimePicker;
    private ArrayAdapter mArrayAdapter;
    private MeetingApi mMeetingApi;
    private MeetingsModel oneMeeting;
    private SimpleDateFormat sdf;
    private Calendar myCalendar;

    void buildDatePicker(){
        mDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.meeting_date_picker_title)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        mActivityCreateMeetingBinding.meetingDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.show(getSupportFragmentManager(), mDatePicker.toString());
            }
        });

        mDatePicker.addOnPositiveButtonClickListener (new MaterialPickerOnPositiveButtonClickListener(){
            @Override
            public void onPositiveButtonClick(Object selection) {

                sdf = new SimpleDateFormat("dd/MM/yyyy");
                myCalendar = Calendar.getInstance();
                myCalendar.setTimeInMillis((long) selection);
                mActivityCreateMeetingBinding.meetingDate.getEditText().setText(sdf.format(myCalendar.getTime()));
            }
        });
    }

    void buildTimePicker(){

        mStartTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Heure de d??but")
                .setHour(10)
                .setMinute(30)
                .build();

        mEndTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Heure de fin")
                .setHour(17)
                .setMinute(00)
                .build();

        mActivityCreateMeetingBinding.meetingStartTime.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTimePicker.show(getSupportFragmentManager(), mStartTimePicker.toString());
            }
        });

        mStartTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityCreateMeetingBinding.meetingStartTime.getEditText().setText(mStartTimePicker.getHour()+":"+mStartTimePicker.getMinute());
                mActivityCreateMeetingBinding.meetingEndTime.getEditText().setText(mStartTimePicker.getHour() + ":" + mStartTimePicker.getMinute());
            }
        });

        mActivityCreateMeetingBinding.meetingEndTime.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEndTimePicker.show(getSupportFragmentManager(), mEndTimePicker.toString());
            }
        });

        mEndTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityCreateMeetingBinding.meetingEndTime.getEditText().setText(mEndTimePicker.getHour()+":"+ mEndTimePicker.getMinute());
            }
        });
    }

    void buildRoomDropDownList() {

        List<String> roomNames = new ArrayList<String>();
        for (RoomsModel extractedRoom:mMeetingApi.getAllRooms()) {
            roomNames.add(extractedRoom.getName());
        }

        mArrayAdapter = new ArrayAdapter(CreateMeetingActivity.this, R.layout.list_of_rooms, roomNames);
        mActivityCreateMeetingBinding.meetingRoomDropdownList.setAdapter(mArrayAdapter);
    }

    int checkData(){
        if (mActivityCreateMeetingBinding.meetingSubject.getEditText().getText().toString().isEmpty()) {
            mActivityCreateMeetingBinding.meetingSubject.setError(getString(R.string.empty_field_error));
            return 1;
        }

        if (mActivityCreateMeetingBinding.meetingDate.getEditText().getText().toString().isEmpty()) {
            mActivityCreateMeetingBinding.meetingDate.setError(getString(R.string.empty_field_error));
            return 1;
        }

        if (mActivityCreateMeetingBinding.meetingStartTime.getEditText().getText().toString().isEmpty()) {
            mActivityCreateMeetingBinding.meetingStartTime.setError(getString(R.string.empty_field_error));
            return 1;
        }

        if (mActivityCreateMeetingBinding.meetingEndTime.getEditText().getText().toString().isEmpty()) {
            mActivityCreateMeetingBinding.meetingEndTime.setError(getString(R.string.empty_field_error));
            return 1;
        }

        if (mActivityCreateMeetingBinding.meetingRoom.getEditText().getText().toString().isEmpty()) {
            mActivityCreateMeetingBinding.meetingRoom.setError(getString(R.string.empty_field_error));
            return 1;
        }

        if (mActivityCreateMeetingBinding.meetingGuestsList.getEditText().getText().toString().isEmpty()) {
            mActivityCreateMeetingBinding.meetingGuestsList.setError(getString(R.string.empty_field_error));
            return 1;
        } else { return 0; }
    }

    void storeData() {

        long requestedRoomId = 0;
        for (RoomsModel room:mMeetingApi.getAllRooms()) {
            /*
            Log.d("storeData()", "storeData: room="+ room.getName());
            Log.d("storeData()", "storeData: roomSelected="+ mActivityCreateMeetingBinding.meetingRoomDropdownList.getText().toString());

             */
            if  (room.getName().equals(mActivityCreateMeetingBinding.meetingRoomDropdownList.getText().toString())) {
                requestedRoomId = room.getId();
            }
        }

        oneMeeting = new MeetingsModel(
                System.currentTimeMillis(),
                mActivityCreateMeetingBinding.meetingSubject.getEditText().getText().toString(),
                myCalendar.getTimeInMillis(),
                mActivityCreateMeetingBinding.meetingStartTime.getEditText().getText().toString(),
                mActivityCreateMeetingBinding.meetingEndTime.getEditText().getText().toString(),
                requestedRoomId,
                mActivityCreateMeetingBinding.meetingGuestsList.getEditText().getText().toString()
        );
        saveMeeting();
    }

    void saveMeeting(){
        mMeetingApi.createMeeting(oneMeeting);
        closeMeetingForm();
    }

    void closeMeetingForm(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityCreateMeetingBinding = ActivityCreateMeetingBinding.inflate(getLayoutInflater());
        setContentView(mActivityCreateMeetingBinding.getRoot());

        // NAVIGATION ActionBar - Affiche la fl??che de retour ?? l'??cran d'accueil (d??pendant de AndroidManifest.xml - option de th??me ActionBar activ??e, par d??faut + indication parentActivityName)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMeetingApi = DI.getService();
        buildDatePicker();
        buildTimePicker();
        buildRoomDropDownList();

        mActivityCreateMeetingBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData() != 1 ) {
                    /*Snackbar.make(v, "Execution de storeData() et saveMeeting()", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                     */
                    storeData();
                }
            }
        });
    }

    // NAVIGATION ActionBar - G??rer l'action au click sur la fl^che de retour
    @Override
    public boolean onSupportNavigateUp(){
        closeMeetingForm();
        return true;
    }
}