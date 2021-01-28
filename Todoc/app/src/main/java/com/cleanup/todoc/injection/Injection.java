package com.cleanup.todoc.injection;

import android.content.Context;
import android.util.Log;

import com.cleanup.todoc.database.SaveMyTodocDatabase;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    private static final String TAG = "Todoc Injection";

    public static ProjectDataRepository provideProjectDataRepository(Context context) {
        Log.d(TAG, "provideProjectDataRepository() called with: context = [" + context + "]");
        SaveMyTodocDatabase db = SaveMyTodocDatabase.getInstance(context);
        return new ProjectDataRepository(db.projectDao());
    }

    public static TaskDataRepository provideTaskDataRepository(Context context) {
        Log.d(TAG, "provideTaskDataRepository() called with: context = [" + context + "]");
        SaveMyTodocDatabase db = SaveMyTodocDatabase.getInstance(context);
        return new TaskDataRepository(db.taskDao());
    }

    public static Executor provideExecutor() {
        Log.d(TAG, "provideExecutor() called");
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {

        Log.d(TAG, "provideViewModelFactory() called");

        ProjectDataRepository projectDataRepository = provideProjectDataRepository(context);
        TaskDataRepository taskDataRepository = provideTaskDataRepository(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(projectDataRepository, taskDataRepository, executor);
    }
}
