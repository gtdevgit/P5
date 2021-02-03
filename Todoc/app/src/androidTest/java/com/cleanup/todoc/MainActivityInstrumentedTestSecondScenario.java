package com.cleanup.todoc;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.rule.ActivityTestRule;

import com.cleanup.todoc.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 * This is the second scenario.
 * Must be executed after first scenario to checking data persistence
 * - open activity and check tasks count equal 2
 */

public class MainActivityInstrumentedTestSecondScenario {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void executeSecondScenario() {
        MainActivity activity = rule.getActivity();
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);

        assertThat(listTasks.getAdapter().getItemCount(), equalTo(2));
    }
}
