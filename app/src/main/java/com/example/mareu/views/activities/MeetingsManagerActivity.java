package com.example.mareu.views.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.mareu.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mareu.databinding.ActivityMeetingsManagerBinding;
import com.example.mareu.views.fragments.ListOfMeetingsFragment;

public class MeetingsManagerActivity extends AppCompatActivity {

    private ActivityMeetingsManagerBinding mActivityMeetingsManagerBinding;

    public void startCreateMeetingActivity (FragmentActivity activity){
        Intent intent = new Intent(activity, CreateMeetingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMeetingsManagerBinding = ActivityMeetingsManagerBinding.inflate(getLayoutInflater());
        setContentView(mActivityMeetingsManagerBinding.getRoot());

        setSupportActionBar(mActivityMeetingsManagerBinding.toolbar);


        //1 - Get our FragmentManager & FragmentTransaction (Inside an activity)
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //2 - Load a new fragment inside a layout container and add it into activty
        fragmentTransaction.replace(R.id.meetings_manager_container, ListOfMeetingsFragment.class, null).addToBackStack(null).commit();

        mActivityMeetingsManagerBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateMeetingActivity(MeetingsManagerActivity.this);
                /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                 */
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}