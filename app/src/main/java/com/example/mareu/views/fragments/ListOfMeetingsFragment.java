package com.example.mareu.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.util.List;

public class ListOfMeetingsFragment extends Fragment implements ListOfMeetingsAdapter.Listener {

    private FragmentListOfMeetingsBinding mFragmentListOfMeetingsBinding;
    private MeetingApi mMeetingApi;
    private List<MeetingsModel> mMeetings;
    private ListOfMeetingsAdapter mListOfMeetingsAdapter;

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
        mMeetings = mMeetingApi.getAllMeetings();
        // Create adapter passing in the sample user data
        mListOfMeetingsAdapter = new ListOfMeetingsAdapter(this.mMeetings, this);
        // Attach the adapter to the recyclerview to populate items
        mFragmentListOfMeetingsBinding.listOfMeetings.setAdapter(this.mListOfMeetingsAdapter);
        // Set layout manager to position the items
        mFragmentListOfMeetingsBinding.listOfMeetings.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMeetingApi = DI.getService();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        mFragmentListOfMeetingsBinding = FragmentListOfMeetingsBinding.inflate(inflater, container, false);
        loadListOfMeetings();
        recyclerViewOnClickListener();
        return mFragmentListOfMeetingsBinding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*
        mFragmentListOfMeetingsBinding.buttonMeetingDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ListOfMeetingsFragment.this)
                        .navigate(R.id.action_ListOfMeetingsFragment_to_ReadMeetingFragment);
            }
        });

         */
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mFragmentListOfMeetingsBinding = null;
    }

}