package com.erikroloff.timegirl.app;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import android.content.Context;

/**
 * Created by erikroloff on 4/19/14.
 */

public class TaskJSONSerializer {

    private Context mContext;
    private String mFilename;

    public TaskJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public ArrayList<Task> loadTasks() throws IOException, JSONException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        BufferedReader reader = null;
        try {
            // open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // build the array of tasks from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                tasks.add(new Task(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // we will ignore this one, since it happens when we start fresh
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
        }
        return tasks;
    }

    public void saveTasks(ArrayList<Task> tasks) throws JSONException, IOException {
        // build an array in JSON
        JSONArray array = new JSONArray();
        for (Task c : tasks)
            array.put(c.toJSON());

        // write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}


