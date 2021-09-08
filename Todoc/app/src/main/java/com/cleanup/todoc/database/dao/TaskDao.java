package com.cleanup.todoc.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getTasksNow();

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getTasks();

    @Insert
    long insertItem(Task task);

    @Update
    int updateItem(Task task);

    @Query("DELETE FROM task WHERE id = :id")
    int deleteTask(long id);
}
