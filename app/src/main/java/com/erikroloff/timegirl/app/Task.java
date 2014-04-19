package com.erikroloff.timegirl.app;


import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.UUID;
import org.joda.time.DateTime;

/**
 * Created by erikroloff on 4/5/14.
 */
public class Task {

    private static final String JSON_ID = "id";
    private static final String JSON_TASKNAME = "taskName";
    private static final String JSON_TOTALTIME = "totalTime";
    private static final String JSON_ACTIVESTATUS = "activeStatus";
    private static final String JSON_LASTSTART = "lastStart";
    private static final String JSON_LASTSTOP = "lastStop";

    private UUID mId;
    private String mTaskName;
    private double mTotalTime;
    private boolean mActiveStatus;
    private DateTime mLastStart;
    private DateTime mLastStop;

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public String getmTaskName() {
        return mTaskName;
    }

    public void setmTaskName(String mTaskName) {
        this.mTaskName = mTaskName;
    }

    public double getmTotalTime() {
        return mTotalTime;
    }

    public void setmTotalTime(double mTotalTime) {
        this.mTotalTime = mTotalTime;
    }

    public boolean ismActiveStatus() {
        return mActiveStatus;
    }

    public void setmActiveStatus(boolean mActiveStatus) {
        this.mActiveStatus = mActiveStatus;
    }

    public DateTime getmLastStart() {
        return mLastStart;
    }

    public void setmLastStart(DateTime mLastStart) {
        this.mLastStart = mLastStart;
    }

    public DateTime getmLastStop() {
        return mLastStop;
    }

    public void setmLastStop(DateTime mLastStop) {
        this.mLastStop = mLastStop;
    }

    public boolean isActive() {
        if (mLastStart.isBefore(mLastStop)) {
            return true;
        } else {
            return false;
        }
    }

    public Period getTimeOnTask() {
        Period timeOnTask;
        if (this.isActive()) {
            timeOnTask = new Period(mLastStart, DateTime.now());
        } else {
            timeOnTask = new Period(mLastStart, mLastStop);
        }

        return timeOnTask;
    }


    public Task() {
        mId = UUID.randomUUID();
    }

    public Task(JSONObject json) throws JSONException, ParseException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTaskName = json.getString("taskName");
        mTotalTime = Double.valueOf(json.getString("totalTime"));
        mActiveStatus = Boolean.getBoolean(json.getString("activeStatus"));
        mLastStart = DateTime.parse(json.getString("lastStart"));
        mLastStop = DateTime.parse(json.getString("lastStop"));
    }

    public JSONObject toJSON() throws JSONException {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:SS");

        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TASKNAME, mTaskName);
        json.put(JSON_TOTALTIME, String.valueOf(mTotalTime));
        json.put(JSON_ACTIVESTATUS, String.valueOf(mActiveStatus));
        json.put(JSON_LASTSTART, fmt.print(mLastStart));
        json.put(JSON_LASTSTOP, fmt.print(mLastStop));
        return json;
    }
}