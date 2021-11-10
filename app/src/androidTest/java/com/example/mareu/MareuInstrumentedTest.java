package com.example.mareu;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareu.controllers.DI;
import com.example.mareu.controllers.MeetingApi;
import com.example.mareu.tools.DeleteViewAction;
import com.example.mareu.views.activities.MeetingsManagerActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withParentIndex;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.tools.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MareuInstrumentedTest {

    private ActivityScenario<MeetingsManagerActivity> mMeetingsManagerActivity;
    private MeetingApi service;

    @Rule
    public ActivityScenarioRule<MeetingsManagerActivity> mMeetingsManagerActivityRule =
            new ActivityScenarioRule<MeetingsManagerActivity>(MeetingsManagerActivity.class);

    @Before
    public void setUp() {
        mMeetingsManagerActivity = mMeetingsManagerActivityRule.getScenario();
        service = DI.getTestDedicatedService();
        assertThat(mMeetingsManagerActivity, notNullValue());
    }


    /** LIST OF MEETINGS --
     * Ensure that RecyclerView is displaying at least on item
     */
    @Test
    public void list_of_meetings_shouldNotBeEmpty() {
        // Based on the RecyclerView layout ID, checks if one "meeting_headline layout" item is count, at least
        // When : the list is displaying
        onView(withId(R.id.list_of_meetings)).check(matches(isDisplayed()));
        // Then : at least, 3 meetings are available
        onView(withId(R.id.list_of_meetings)).check(matches(hasMinimumChildCount(service.getAllMeetings().size())));
    }

    /** LIST OF MEETINGS -- Filter by date
     * Ensure the action is displaying the Material Date Picker
     */
    @Test
    public void list_of_meetings_filteredByDate_withSuccess() {
        // Given : the list is displaying
        onView(withId(R.id.list_of_meetings)).check(matches(isDisplayed()));
        // When : the action "Filtre par date" is selected form OptionsMenu
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Filtre par date")).perform(click());

            // Click year selector button to open year selection view
            onView(withId(R.id.month_navigation_fragment_toggle)).perform(click());
            // Scroll to year (=2021) and click it
            onView(withId(R.id.mtrl_calendar_year_selector_frame))
                    .perform(RecyclerViewActions.actionOnItem(withText("2021"),click()));

            // Scroll to month = 11/2021
            onView(withId(R.id.mtrl_calendar_months))
                    .perform(RecyclerViewActions.scrollToPosition(1462));
                    //.perform(RecyclerViewActions.scrollTo((hasDescendant(withText("NOVEMBER 2021")))));
                    //.perform(RecyclerViewActions.scrollTo((withChild(withText("NOVEMBER 2021")))));

            // Click day of month = 19
            onView(allOf(withText("19"),isDisplayed())).perform(click());
            onView(withId(R.id.confirm_button)).perform(click());

        // Then : only the item "Sprint retrospective du projet précédent" should be listed
        onView(withId(R.id.list_of_meetings)).check(withItemCount(1));
    }

    /** LIST OF MEETINGS -- Filter by room
     * Ensure the action is displaying the Material Alert Dialog to select a room
     */
    @Test
    public void list_of_meetings_filteredByRoom_withSuccess() {
        // Given : the list is displaying
        onView(withId(R.id.list_of_meetings)).check(matches(isDisplayed()));

        // When : the action "Filtre par salle" is selected
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Filtre par salle")).perform(click());

            // Click the room = "Mario"
            onView(withText("Mario")).perform(click());
            // Click Ok to confirm
            onView(withText("Ok")).perform(click());
        // Then : Only 2 items from the sample of meetings should be listed
        onView(withId(R.id.list_of_meetings)).check(withItemCount(2));
    }

    /** ACCESS TO THE FORM TO ADD A NEW MEETING
     * Ensure the FAB is intending the CreateMeetingActivity
     */
    @Test
    public void starts_CreateMeetingActivity_withSuccess() {
        // Given : the list is displaying
        onView(withId(R.id.list_of_meetings)).check(matches(isDisplayed()));
        // When : the "add a new meeting" FAB is clicked
        onView(withId(R.id.fab)).perform(click());
        // Then : the CreateMeetingActivity is opening
        onView(withId(R.id.form_layout)).check(matches(isDisplayed()));
    }

    /** CREATE A MEETING
     * Ensure the action is adding the item into the RecyclerView
     */
    @Test
    public void newMeeting_ShouldIncreaseListOfMeetings() {
        // Given : the list is displaying
        onView(withId(R.id.list_of_meetings)).check(matches(isDisplayed()));
        // Given : the "add a new meeting" FAB is clicked
        onView(withId(R.id.fab)).perform(click());
        // Given : the CreateMeetingActivity is opening
        onView(withId(R.id.form_layout)).check(matches(isDisplayed()));

        // When : New meeting form is completing
        onView(withId(R.id.meeting_subject_editText)).perform(click());
        onView(withId(R.id.meeting_subject_editText)).perform(typeTextIntoFocusedView("Sujet de test"), closeSoftKeyboard(), pressImeActionButton());
        onView(withId(R.id.meeting_subject_editText)).check(matches(withText("Sujet de test")));

        onView(withId(R.id.meeting_date)).perform(click());
            // Click year selector button to open year selection view
            onView(withId(R.id.month_navigation_fragment_toggle)).perform(click());
            // Scroll to year (=2021) and click it
            onView(withId(R.id.mtrl_calendar_year_selector_frame))
                    .perform(RecyclerViewActions.actionOnItem(withText("2021"),click()));

            // Scroll to month = 12/2021
            onView(withId(R.id.mtrl_calendar_months))
                    .perform(RecyclerViewActions.scrollToPosition(1463));

            // Click day of month = 05
            onView(allOf(withText("5"),isDisplayed())).perform(click());
            onView(withId(R.id.confirm_button)).perform(click());

        onView(withId(R.id.meeting_start_time)).check(matches(isDisplayed()));
        onView(withId(R.id.meeting_start_time_editText)).perform(doubleClick());

        onView(withResourceName("material_timepicker_view")).check(matches(isDisplayed()));
        onView(withResourceName("material_timepicker_ok_button")).perform(click());
        onView(withId(R.id.meeting_start_time_editText)).check(matches(withText("10:30")));

        onView(withId(R.id.meeting_end_time)).check(matches(isDisplayed()));
        onView(withId(R.id.meeting_end_time_editText)).perform(replaceText("11:30"));
        onView(withId(R.id.meeting_end_time_editText)).check(matches(withText("11:30")));

        onView(withId(R.id.meeting_room)).check(matches(isDisplayed()));
        onView(withId(R.id.meeting_room)).perform(click());
        onView(withId(R.id.meeting_room_dropdown_list)).check(matches(isDisplayed()));
        onView(withId(R.id.meeting_room_dropdown_list)).perform(replaceText("Lugi"));

        onView(withId(R.id.meeting_guests_list_editText)).perform(click());
        onView(withId(R.id.meeting_guests_list_editText)).perform(typeTextIntoFocusedView("pwalker@lamzone.com, amorris@lamzone.com, jdoe@lamzone.com"), closeSoftKeyboard(), pressImeActionButton());
        onView(withId(R.id.meeting_guests_list_editText)).check(matches(withText("pwalker@lamzone.com, amorris@lamzone.com, jdoe@lamzone.com")));

        onView(withId(R.id.saveButton)).check(matches(isDisplayed()));
        onView(withId(R.id.saveButton)).perform(click());

        // Then : the list of meetings count is one more
        onView(allOf(ViewMatchers.withId(R.id.list_of_meetings), isDisplayed())).check(withItemCount(service.getAllMeetings().size() + 1));

/*
        onView(withResourceName("material_timepicker_view")).check(matches(isDisplayed()));
        onView(withResourceName("material_hour_tv")).perform(typeText("11"));
        onView(withResourceName("material_minute_tv")).perform(typeText("30"));
        onView(withResourceName("material_timepicker_ok_button")).perform(click());

 */
    }

    /** READ A MEETING
     * Ensure meeting details fragment is displaying on item click
     */
    @Test
    public void starts_ReadMeetingFragment_withSuccess() {
        // Given : the list is displaying
        onView(withId(R.id.list_of_meetings)).check(matches(isDisplayed()));
        // When : the second item is selected
        onView(withId(R.id.list_of_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));
        // Then : The correct layout is checked through it's ressource ID
        onView(withId(R.id.meetings_manager_container)).check(matches(isDisplayed()));
    }

    /** DELETE A MEETING
     * Ensure the action is removing the item from the RecyclerView
     */
    @Test
    public void deleteMeeting_shouldRemoveFromListOfMeetings() {
        // Given : the list is displaying
        onView(withId(R.id.list_of_meetings)).check(matches(isDisplayed()));
        // When : on the 3rd item, the delete button is clicked
        onView(allOf(withParent(withId(R.id.list_of_meetings)), withParentIndex(2)));
        onView(withId(R.id.list_of_meetings)).perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));
        // Then : the list of meetings count is one less
        onView(allOf(ViewMatchers.withId(R.id.list_of_meetings), isDisplayed())).check(withItemCount(service.getAllMeetings().size() - 1));
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.mareu", appContext.getPackageName());
    }

}