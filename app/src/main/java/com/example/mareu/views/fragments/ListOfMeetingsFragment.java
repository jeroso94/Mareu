package com.example.mareu.views.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.controllers.DI;
import com.example.mareu.controllers.ItemClickSupport;
import com.example.mareu.controllers.MeetingApi;
import com.example.mareu.databinding.FragmentListOfMeetingsBinding;
import com.example.mareu.models.MeetingsModel;
import com.example.mareu.views.recycler.ListOfMeetingsAdapter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;
import java.util.List;

public class ListOfMeetingsFragment extends Fragment implements ListOfMeetingsAdapter.Listener {

    private FragmentListOfMeetingsBinding mFragmentListOfMeetingsBinding;
    private MaterialDatePicker mDatePicker;
    private MeetingApi mMeetingApi;
    private List<MeetingsModel> mMeetings;
    private ListOfMeetingsAdapter mListOfMeetingsAdapter;
    private int roomListIndex = 0;

    private void filterByDate(){
        mDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.meeting_date_filter_title)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        mDatePicker.show(getChildFragmentManager(), mDatePicker.toString());

        mDatePicker.addOnPositiveButtonClickListener (new MaterialPickerOnPositiveButtonClickListener(){
            @Override
            public void onPositiveButtonClick(Object selection) {

                Calendar selectedDateCalendar = Calendar.getInstance();
                selectedDateCalendar.setTimeInMillis((long) selection);

                mMeetings = mMeetingApi.getMeetingsFilterByDate(selectedDateCalendar.getTimeInMillis());
                for (MeetingsModel oneMeeting:mMeetings) {
                    Log.d("ListOfMeetingsFragment", "onPositiveButtonClick: " + oneMeeting.getMeetingDate() + " " + oneMeeting.getSubject());
                }
                loadListOfMeetings();

                //Toast.makeText(getContext(), "You selected : "+ (Date) selectedDateCalendar.getTime(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void filterByRoom(){

        String[] roomList = new String[mMeetingApi.getAllRooms().size()];
        for (int i = 0; i < mMeetingApi.getAllRooms().size(); i ++) {
            roomList[i] = mMeetingApi.getAllRooms().get(i).getName();
        }
        String[] selectedRoom = {roomList[roomListIndex]};

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext())
                .setTitle(R.string.meeting_room_filter_title)
                .setSingleChoiceItems(roomList, roomListIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        roomListIndex = which;
                        selectedRoom[0] = roomList[which];
                    }
                })
                .setPositiveButton(R.string.action_filter_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), selectedRoom[0] + "Selected", Toast.LENGTH_SHORT).show();
                        mMeetings = mMeetingApi.getMeetingsFilterByRoom(selectedRoom[0]);
                        loadListOfMeetings();
                    }
                })
                .setNeutralButton(R.string.action_filter_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Cancel Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

    @Override
    public void onClickDeleteButton(int position) {
        mMeetingApi.deleteMeeting(position);
        loadListOfMeetings();
    }

    /**
     * Configure onClick listener for RecyclerView
     */
    private void recyclerViewOnClickListener(){
        ItemClickSupport.addTo(mFragmentListOfMeetingsBinding.listOfMeetings, R.layout.fragment_meeting_headline)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        MeetingsModel meeting = mListOfMeetingsAdapter.getMeeting(position);
                        Toast.makeText(getContext(), "You clicked on user : "+meeting.getSubject(), Toast.LENGTH_SHORT).show();
                        //ReadMeetingFragment.newInstance(meeting.getId());
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        ReadMeetingFragment readMeetingFragment = ReadMeetingFragment.newInstance(meeting.getId());
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.meetings_manager_container,readMeetingFragment).addToBackStack(null).commit();
                    }
                });
    }

    /**
     * Configure RecyclerView to load the List of meetings
     */
    private void loadListOfMeetings() {
        // Create adapter passing in the sample user data
        mListOfMeetingsAdapter = new ListOfMeetingsAdapter(this.mMeetings, this);
        // Attach the adapter to the recyclerview to populate items
        mFragmentListOfMeetingsBinding.listOfMeetings.setAdapter(this.mListOfMeetingsAdapter);
        // Set layout manager to position the items
        mFragmentListOfMeetingsBinding.listOfMeetings.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Activation de l'affichage du menu des filtres
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
        mMeetingApi = DI.getService();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        AppCompatActivity activity = (AppCompatActivity) getContext();
        ListOfMeetingsFragment listOfMeetingsFragment = new ListOfMeetingsFragment();


        switch (item.getItemId()){
            case R.id.action_menu_item_date:
                //Toast.makeText(getContext(), "FilterByDate", Toast.LENGTH_SHORT).show();
                filterByDate();
                return true;
            case R.id.action_menu_item_room:
                // Toast.makeText(getContext(), "FilterByRoom", Toast.LENGTH_SHORT).show();
                filterByRoom();
                return true;
            case R.id.action_menu_item_reset:
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.meetings_manager_container,listOfMeetingsFragment).addToBackStack(null).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        mFragmentListOfMeetingsBinding = FragmentListOfMeetingsBinding.inflate(inflater, container, false);
        mMeetings = mMeetingApi.getAllMeetings();
        loadListOfMeetings();
        recyclerViewOnClickListener();
        return mFragmentListOfMeetingsBinding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentListOfMeetingsBinding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        loadListOfMeetings();
    }
}