package com.erikroloff.timegirl.app;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

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
    private Date mLastStart;
    private Date mLastStop;


    public Task() {
        mId = UUID.randomUUID();
    }

    public Task(JSONObject json) throws JSONException, ParseException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTaskName = json.getString("taskName");
        mTotalTime = Double.valueOf(json.getString("totalTime"));
        mActiveStatus = Boolean.getBoolean(json.getString("activeStatus"));
        mLastStart = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(json.getString("lastStart"));
        mLastStop = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(json.getString("lastStop"));
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TASKNAME, mTaskName);
        json.put(JSON_TOTALTIME, String.valueOf(mTotalTime));
        json.put(JSON_ACTIVESTATUS, String.valueOf(mActiveStatus));
        json.put(JSON_LASTSTART, )
        return json;
    }
}
