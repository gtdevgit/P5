package com.cleanup.todoc;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.cleanup.todoc.ui.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 * This is the first scenario
 * - remove all tasks to having empty list
 * - add 3 tasks
 *      => check there is 3 tasks
 * - use the 4 differents kind of sort task
 *      => check the task is sorted
 * - remove first tasks
 *      => check tasks count equal 2
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTestFirstScenario {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    private void removeFirstTask(){
        RecyclerViewMatcher recyclerViewMatcher = new RecyclerViewMatcher(R.id.list_tasks);
        Matcher<View> viewMatcher = recyclerViewMatcher.atPositionOnView(0, R.id.img_delete);
        onView(viewMatcher).perform(click());
    }

    private void removeAllTask(RecyclerView listTasks){
        int count = listTasks.getAdapter().getItemCount();
        while (count > 0){
            removeFirstTask();
            count = listTasks.getAdapter().getItemCount();
        }
    }

    private void addTaskProject(String name, int projectPosition){
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText(name));

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.project_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                projectPosition),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        onView(withId(android.R.id.button1)).perform(click());
    }

    private void addTask(String name){
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText(name));
        onView(withId(android.R.id.button1)).perform(click());
    }

    private void sort(int id){
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(id)).perform(click());
    }

    private void checkTaskName(int position, String name){
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(position, R.id.lbl_task_name))
                .check(matches(withText(name)));
    }

    private void checkTaskCount(RecyclerView listTasks, int count){
        assertThat(listTasks.getAdapter().getItemCount(), equalTo(count));
    }

    @Test
    public void executeFirstScenario() {
        MainActivity activity = rule.getActivity();
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);

        // Clear list tasks
        removeAllTask(listTasks);
        // Check that it contains one element only
        assertThat(listTasks.getAdapter().getItemCount(), equalTo(0));
        // Check that lblTask is displayed
        assertThat(lblNoTask.getVisibility(), equalTo(View.VISIBLE));
        // Check that recyclerView is not displayed anymore
        assertThat(listTasks.getVisibility(), equalTo(View.GONE));

        // add tasks
        //addTaskProject("aaa Tâche example", 2);
        addTask("aaa Tâche example");
        addTask("zzz Tâche example");
        addTask("hhh Tâche example");

        // Check that it contains 3 elements
        checkTaskCount(listTasks, 3);

        // Sort alphabetical
        //onView(withId(R.id.action_filter)).perform(click());
        //onView(withText(R.string.sort_alphabetical)).perform(click());
        sort(R.string.sort_alphabetical);
        checkTaskName(0, "aaa Tâche example");
        checkTaskName(1, "hhh Tâche example");
        checkTaskName(2, "zzz Tâche example");

        // Sort alphabetical inverted
        sort(R.string.sort_alphabetical_invert);
        checkTaskName(0, "zzz Tâche example");
        checkTaskName(1, "hhh Tâche example");
        checkTaskName(2, "aaa Tâche example");

        // Sort old first
        sort(R.string.sort_oldest_first);
        checkTaskName(0, "aaa Tâche example");
        checkTaskName(1, "zzz Tâche example");
        checkTaskName(2, "hhh Tâche example");

        // Sort recent first
        sort(R.string.sort_recent_first);
        checkTaskName(0, "hhh Tâche example");
        checkTaskName(1, "zzz Tâche example");
        checkTaskName(2, "aaa Tâche example");

        removeFirstTask();;
        checkTaskCount(listTasks, 2);
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
