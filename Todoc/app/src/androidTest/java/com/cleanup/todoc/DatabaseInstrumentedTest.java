package com.cleanup.todoc;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.SaveMyTodocDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    private TaskDao taskDao;
    private ProjectDao projectDao;
    private SaveMyTodocDatabase database;

    //@Rule
    //public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        database = Room.inMemoryDatabaseBuilder(context, SaveMyTodocDatabase.class).build();
        taskDao = database.taskDao();
        projectDao = database.projectDao();
    }

    @Before
    public void addData(){
        projectDao.createProject(new Project(1L, "Projet Tartampion", 0xFFEADAD1));
        projectDao.createProject(new Project(2L, "Projet Lucidia", 0xFFB4CDBA));
        projectDao.createProject(new Project(3L, "Projet Circus", 0xFFA3CED2));

        taskDao.insertItem(new Task(1L, 1L, "tache 1.1", new Date().getTime()));
        taskDao.insertItem(new Task(2L, 1L, "tache 1.2", new Date().getTime()));
        taskDao.insertItem(new Task(3L, 2L, "tache 2.1", new Date().getTime()));
        taskDao.insertItem(new Task(4L, 2L, "tache 2.2", new Date().getTime()));
        taskDao.insertItem(new Task(5L, 3L, "tache 3.1", new Date().getTime()));
        taskDao.insertItem(new Task(6L, 3L, "tache 3.2", new Date().getTime()));
    }

    @Test
    public void countProjectInDb() throws InterruptedException {
        List<Project> projects = projectDao.getProjectsNow();
        assertEquals(3, projects.size());
    }

    @Test
    public void countTaskInDb() throws InterruptedException {
        List<Task> tasks = taskDao.getTasksNow();
        assertEquals(6, tasks.size());
    }

    @Test
    public void insertTaskInDb() throws InterruptedException {
        List<Task> tasks = taskDao.getTasksNow();
        int initialCount = tasks.size();
        taskDao.insertItem(new Task(7L, 3L, "tache 3.3", new Date().getTime()));
        tasks = taskDao.getTasksNow();
        assertEquals(initialCount + 1, tasks.size());
    }

    @Test
    public void insertTaskWithBadProjectIdInDb() throws InterruptedException {
        final long BAD_PROJECT_ID = 4L;
        try {
            taskDao.insertItem(new Task(7L, BAD_PROJECT_ID, "tache 3.3", new Date().getTime()));
            fail("Should have thrown SQLiteConstraintException");
        }
        catch (SQLiteConstraintException e) {
        }
    }

    @Test
    public void deleteAllTasksInDb() throws InterruptedException {
        List<Task> tasks = taskDao.getTasksNow();
        for (Task task : tasks) {
            taskDao.deleteTask(task.getId());
        }
        tasks = taskDao.getTasksNow();
        assertEquals(0, tasks.size());
    }
}
