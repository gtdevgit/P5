package com.cleanup.todoc.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private static final String TAG = "Todoc TaskDataRepository";

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        Log.d(TAG, "TaskDataRepository() called with: taskDao = [" + taskDao + "]");
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getTasks() {
        return this.taskDao.getTasks();
    }

    public List<Task> getTasksNow() {
        return this.taskDao.getTasksNow();
    }

    public void createTask(Task task){
        this.taskDao.insertItem(task);
    }

    public void updateItem(Task task){
        this.taskDao.updateItem(task);
    }

    public void deleteTask(long id){
        this.taskDao.deleteTask(id);
    }
}
