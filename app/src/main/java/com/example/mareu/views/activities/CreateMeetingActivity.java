package com.example.mareu.views.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;


import com.example.mareu.R;
import com.example.mareu.controllers.MeetingApi;
import com.example.mareu.databinding.ActivityCreateMeetingBinding;
import com.example.mareu.models.MeetingsModel;
import com.example.mareu.models.RoomsModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CreateMeetingActivity extends AppCompatActivity {

    private ActivityCreateMeetingBinding mActivityCreateMeetingBinding;
    private MaterialDatePicker mDatePicker;
    private MaterialTimePicker mStartTimePicker;
    private MaterialTimePicker mEndTimePicker;
    public static List<RoomsModel> sampleRooms;
    private ArrayAdapter mArrayAdapter;
    private MeetingApi mMeetingApi;
    private MeetingsModel oneMeeting;

    void runDatePicker(){
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
                mActivityCreateMeetingBinding.meetingDate.getEditText().setText(mDatePicker.getHeaderText());
            }
        });
    }

    void runTimePicker(){

        mStartTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Heure de début")
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

    void runRoomDropDownList() {
        sampleRooms = Arrays.asList(
                new RoomsModel(1, "Interne", "Mario"),
                new RoomsModel(2, "Interne", "Luigi"),
                new RoomsModel(3, "Interne", "Peach")
        );
        Log.d("runRoomDropDownList()", "sampleRooms=" + sampleRooms.size());




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

    void storeData(){
        oneMeeting = new MeetingsModel(
                System.currentTimeMillis(),
                mActivityCreateMeetingBinding.meetingSubject.getEditText().getText().toString(),
                mActivityCreateMeetingBinding.meetingDate.getEditText().getText().toString(),
                mActivityCreateMeetingBinding.meetingStartTime.getEditText().getText().toString(),
                mActivityCreateMeetingBinding.meetingEndTime.getEditText().getText().toString(),
                2,
                mActivityCreateMeetingBinding.meetingGuestsList.getEditText().getText().toString()
        );
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

        // NAVIGATION ActionBar - Affiche la flêche de retour à l'écran d'accueil (dépendant de AndroidManifest.xml - option de thème ActionBar activée, par défaut + indication parentActivityName)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActivityCreateMeetingBinding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkData() != 1 ) {
                    Snackbar.make(v, "Execution de storeData() et saveMeeting()", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //storeData();
                    //saveMeeting();
                }
            }
        });

        runDatePicker();
        runTimePicker();
        runRoomDropDownList();

    }

    // NAVIGATION ActionBar - Gérer l'action au click sur la fl^che de retour
    @Override
    public boolean onSupportNavigateUp(){
        closeMeetingForm();
        return true;
    }

}