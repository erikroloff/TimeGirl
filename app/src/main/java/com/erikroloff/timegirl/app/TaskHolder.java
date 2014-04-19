package com.erikroloff.timegirl.app;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

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
}
