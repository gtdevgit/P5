package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createProject(Project project);

    @Query("SELECT * FROM project WHERE id = :id")
    LiveData<Project> getProject(long id);

    @Query("SELECT * FROM project")
    LiveData<List<Project>> getProjects();

    @Query("SELECT * FROM project")
    List<Project> getProjectsNow();
}
