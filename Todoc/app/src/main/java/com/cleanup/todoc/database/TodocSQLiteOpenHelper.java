package com.cleanup.todoc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;

import androidx.annotation.Nullable;

import com.cleanup.todoc.model.Project;

public class TodocSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "Todoc TodocSQLiteOpenHelper";
    private  static  final int DB_VERSION = 1;
    private static final String DB_NAME = "todocBdd.db";

    public TodocSQLiteOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(TAG, "TodocSQLite() called with: context = [" + context + "], name = [" + getDbName() + "], version = [" + getDbVersion() + "]");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate() called with: sqLiteDatabase = [" + sqLiteDatabase + "]");
        sqLiteDatabase.execSQL(TaskTableDefinition.getCreateTable());
        sqLiteDatabase.execSQL(ProjectTableDefinition.getCreateTable());
        populateProjects(sqLiteDatabase);
    }

    public void populateProjects(SQLiteDatabase sqLiteDatabase){
        Project[] projects = Project.getAllProjects();
        for (Project project : projects){
            ContentValues values = new ContentValues();
            values.put(ProjectTableDefinition.getColId(), project.getId());
            values.put(ProjectTableDefinition.getColName(), project.getName());
            values.put(ProjectTableDefinition.getColColor(), project.getColor());
            sqLiteDatabase.insert(ProjectTableDefinition.getTableName(), null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // when version change
    }

    public static int getDbVersion() {
        return DB_VERSION;
    }

    public static String getDbName() {
        return DB_NAME;
    }
}
