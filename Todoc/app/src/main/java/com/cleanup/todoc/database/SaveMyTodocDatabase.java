package com.cleanup.todoc.database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.Date;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class SaveMyTodocDatabase extends RoomDatabase {

    private static final String TAG = "Todoc SaveMyTodocDatabase";
    private  static  final int DB_VERSION = 1;
    private static final String DB_NAME = "todocBdd.db";

    // --- SINGLETON ---
    private static volatile SaveMyTodocDatabase INSTANCE;

    // --- DAO ---
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // --- INSTANCE ---
    public static SaveMyTodocDatabase getInstance(Context context) {
        Log.d(TAG, "getInstance() called");

        if (INSTANCE == null) {
            synchronized (SaveMyTodocDatabase.class) {
                if (INSTANCE == null) {
                    Log.d(TAG, "getInstance() Room.databaseBuilder create instance with: context.getApplicationContext() = [" + context.getApplicationContext() + "]");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveMyTodocDatabase.class, DB_NAME)
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                populateProjects(db);
                populateSampleTasks(db);
            }
        };
    }

    private static void populateProjects(SupportSQLiteDatabase db) {
        // populate projects with initials projects
        Project[] projects = Project.getAllProjects();
        for (Project project : projects){
            Log.d(TAG, "onCreate() called with: projects = [" + projects + "]");
            ContentValues values = new ContentValues();
            values.put("id", project.getId());
            values.put("name", project.getName());
            values.put("color", project.getColor());
            db.insert("project", OnConflictStrategy.IGNORE, values);
        }
    }

    private static void populateSampleTasks(SupportSQLiteDatabase db) {
        // POUR TEST A SUPPRIMER
        Task[] sampleTasks = new Task[]{
                new Task(1L, 1L, "tache 1", new Date().getTime()),
                new Task(2L, 1L, "tache 2", new Date().getTime()),
                new Task(3L, 1L, "tache 3", new Date().getTime()),
                new Task(4L, 1L, "tache 4", new Date().getTime()),
                new Task(5L, 1L, "tache 5", new Date().getTime()),
                new Task(6L, 1L, "tache 6", new Date().getTime())
        };
        for (Task task : sampleTasks){
            Log.d(TAG, "onCreate() called with: task = [" + task + "]");
            ContentValues values = new ContentValues();
            values.put("id", task.getId());
            values.put("projectId", task.getProjectId());
            values.put("name", task.getName());
            values.put("creationTimestamp", task.getCreationTimestamp());
            db.insert("task", OnConflictStrategy.IGNORE, values);
        }
    }
}
