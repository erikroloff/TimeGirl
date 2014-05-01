package com.erikroloff.timegirl.app;


import android.util.Log;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;
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
    private Period mTotalTime;
    private boolean mActiveStatus;
    private DateTime mLastStart;
    private DateTime mLastStop;
    private DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    private Period timeOnTask;


    public UUID getId() {
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

    public Period getmTotalTime() {
        if (this.mTotalTime == null) {
            return new Period(0);
        } else {
            return mTotalTime;
        }

    }

    public void setmTotalTime(Period timeOnTask) {
        this.mTotalTime = timeOnTask;

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
        this.mActiveStatus = true;

    }

    public DateTime getmLastStop() {
        return mLastStop;
    }

    public void setmLastStop(DateTime mLastStop) {
        this.setmTotalTime(getTimeOnTask());
        this.mActiveStatus = false;
        this.mLastStop = mLastStop;
    }

    public boolean isActive() {

        if (mLastStart == null) {
            return false;
        } else {
            if (mLastStart.isBefore(mLastStop)) {
                return false;
            } else {
                return true;
            }
        }
    }

    public Period getTimeOnTask() {
        timeOnTask = new Period(0);

//        Period totalTimeOnTask;
        if (mActiveStatus) {
//            if (this.isActive()) {
                timeOnTask = new Period(mLastStart, DateTime.now());
                Log.d("Task", "Calling Period(mLastStart, DateTime.now())");
//            } else {
//                timeOnTask = new Period(mLastStart,mLastStop);
//            }
        } else {
            Log.d("Task", "Calling new Period(0)");
//            timeOnTask = new Period(0);
        }

        timeOnTask = timeOnTask.plus(this.getmTotalTime());
//        Duration oldTotal = this.getmTotalTime().toStandardDuration();
//        Duration lastInterval = timeOnTask.toStandardDuration();
//        Duration newTotal = new Duration(oldTotal.getMillis() + lastInterval.getMillis());
//        Period newTotalTime = newTotal.toPeriod();
//        totalTimeOnTask = newTotalTime;


        return timeOnTask;
    }


    public Task() {
        mId = UUID.randomUUID();
        mActiveStatus = false;
        this.setmTotalTime(getTimeOnTask());
    }

    public Task(JSONObject json) throws JSONException, ParseException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTaskName = json.getString("taskName");
        mTotalTime = PeriodFormat.getDefault().parsePeriod(json.getString("totalTime"));
        mActiveStatus = Boolean.getBoolean(json.getString("activeStatus"));
        mLastStart = fmt.parseDateTime(json.getString("lastStart"));
        mLastStop = fmt.parseDateTime(json.getString("lastStop"));
    }

    public JSONObject toJSON() throws JSONException {
        if (!this.isActive()) {
            this.setmTotalTime(getTimeOnTask());
        }

        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TASKNAME, mTaskName);
        json.put(JSON_TOTALTIME, PeriodFormat.getDefault().print(this.getmTotalTime()));
        json.put(JSON_ACTIVESTATUS, String.valueOf(mActiveStatus));
        json.put(JSON_LASTSTART, fmt.print(mLastStart));
        json.put(JSON_LASTSTOP, fmt.print(mLastStop));
        return json;
    }
}
