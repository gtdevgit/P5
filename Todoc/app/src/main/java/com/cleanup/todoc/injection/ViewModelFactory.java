package com.cleanup.todoc.injection;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.MainApplication;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;
import com.cleanup.todoc.ui.TaskViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static final String TAG = "Todoc ViewModelFactory";

    private volatile static ViewModelFactory sInstance;

    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    public ViewModelFactory(ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor) {
        Log.d(TAG, "ViewModelFactory() called");

        this.projectDataSource = projectDataRepository;
        this.taskDataSource = taskDataRepository;
        this.executor = executor;
    }

    public static ViewModelFactory getInstance() {
        if (sInstance == null) {
            // Double Checked Locking singleton pattern with Volatile works on Android since Honeycomb
            synchronized (ViewModelFactory.class) {
                if (sInstance == null) {
                    //Application application = MainApplication.getApplication();
                    sInstance = Injection.provideViewModelFactory(MainApplication.getApplication());
                }
            }
        }

        return sInstance;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Log.d(TAG, "create() called");

        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(projectDataSource, taskDataSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
