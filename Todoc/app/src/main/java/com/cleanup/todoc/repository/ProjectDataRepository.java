package com.cleanup.todoc.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectDataRepository {
    private static final String TAG = "Todoc ProjectDataRepository";

    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        Log.d(TAG, "ProjectDataRepository() called with: projectDao = [" + projectDao + "]");
        this.projectDao = projectDao;
    }

    public LiveData<Project> getProject(long projectId) {
        return this.projectDao.getProject(projectId);
    }

    public LiveData<List<Project>> getProjects() {
        return this.projectDao.getProjects();
    }
}
