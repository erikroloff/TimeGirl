package com.erikroloff.timegirl.app;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by erikroloff on 4/19/14.
 */
public class TaskHolder {

    private ArrayList<Task> mTasks;
    private TaskJSONSerializer mSerializer;
    private static final String TAG = "Tasks";
    private static final String FILENAME = "tasks.json";

    private static TaskHolder sTaskHolder;
    private Context mAppContext;

    private TaskHolder(Context appContext) {
        mAppContext = appContext;
        mSerializer = new TaskJSONSerializer(mAppContext, FILENAME);

        try {
            mTasks = mSerializer.loadTasks();
        } catch (Exception e) {
            mTasks = new ArrayList<Task>();
            Log.e(TAG, "Error loading tasks: ", e);
        }
    }

    public static TaskHolder get(Context c) {
        if (sTaskHolder == null) {
            sTaskHolder = new TaskHolder(c.getApplicationContext());
        }

        return sTaskHolder;
    }

    // Gets Task from Task ArrayList
    public Task getTask(UUID id) {
        for (Task t : mTasks) {
            if (t.getId().equals(id)) {
                return t;
            }
        }

        return null;
    }

    // Adds Task to ArrayList
    public void addTask(Task t) {
        mTasks.add(t);
        saveTasks();
    }

    public ArrayList<Task> getTasks() {
        return mTasks;
    }

    public void deleteTask(Task t) {
        mTasks.remove(t);
    }

    // Saves to JSON File
    public boolean saveTasks() {
        try{
            mSerializer.saveTasks(mTasks);
            Log.d(TAG, "tasks saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving tasks: " + e);
            return false;
        }
    }

}
