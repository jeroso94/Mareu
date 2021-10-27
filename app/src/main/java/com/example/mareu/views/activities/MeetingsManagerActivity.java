package com.example.mareu.views.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.mareu.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mareu.databinding.ActivityMeetingsManagerBinding;
import com.example.mareu.views.fragments.ListOfMeetingsFragment;
import com.example.mareu.views.fragments.ReadMeetingFragment;

import android.view.Menu;
import android.view.MenuItem;

public class MeetingsManagerActivity extends AppCompatActivity {

    private ActivityMeetingsManagerBinding binding;

    public void startCreateMeetingActivity (FragmentActivity activity){
        Intent intent = new Intent(activity, CreateMeetingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMeetingsManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        //1 - Get our FragmentManager & FragmentTransaction (Inside an activity)
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //2 - Load a new fragment inside a layout container and add it into activty
        fragmentTransaction.add(R.id.list_of_meetings_container, ListOfMeetingsFragment.class, null);
        fragmentTransaction.add(R.id.ReadMeeting_container, ReadMeetingFragment.class, null);
        fragmentTransaction.commit();

        binding.fab.setOnClickListener(new View.OnClickListener() {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
/*
        NavController navController = Navigation.findNavController(this, R.id.fragment_navigation);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();

 */
    }

}