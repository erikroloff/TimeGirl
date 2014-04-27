package com.erikroloff.timegirl.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by erikroloff on 4/20/14.
 */
public class TaskFragment extends Fragment {
    public static final String EXTRA_TASK_ID = "timegirl.TASK_ID";
    private ArrayList<Task> tasks = null;
    private Task mTask;
    private TextView tvnumberOfDays;
    private TextView tvnumberOfHours;
    private TextView tvnumberOfMinutes;
    private TextView tvnumberOfSeconds;
    private EditText edtTaskName;
    private final Handler myHandler = new Handler();
    private int i = 0;
    private Timer myTimer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (TaskHolder.get(getActivity()).getTasks() == null) {
            // Make new mTask if mTask id ...

//            task.setmTaskName("Test");


        } else {
            tasks = TaskHolder.get(getActivity()).getTasks();
        }

        mTask = new Task();
        TaskHolder.get(getActivity()).addTask(mTask);



    }

    public static TaskFragment newInstance(UUID taskId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TASK_ID, taskId);

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.task_detail_fragment, container, false);

        if (savedInstanceState == null || !savedInstanceState.containsKey("key")) {
            tasks = new ArrayList<Task>();
        } else {

        }

        Button startButton = (Button) v.findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                onStartButtonClicked();
            }
        });

        Button stopButton = (Button) v.findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                onStopButtonClicked();
            }
        });

        edtTaskName = (EditText) v.findViewById(R.id.edtTaskName);

        tvnumberOfDays = (TextView) v.findViewById(R.id.tvnumberOfDays);
        tvnumberOfHours = (TextView) v.findViewById(R.id.tvnumberOfHours);
        tvnumberOfMinutes = (TextView) v.findViewById(R.id.tvnumberOfMinutes);
        tvnumberOfSeconds = (TextView) v.findViewById(R.id.tvnumberOfSeconds);

        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {UpdateGUI();}
        }, 0, 1000);


//        edtTaskName.setText(mTask.getmTaskName());

        // Need to add setText methods for the Days, Hours, Minutes, and Seconds




        return v;
    }

    private void UpdateGUI() {
        i++;
        //tv.setText(String.valueOf(i));
        myHandler.post(myRunnable);
    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            Period amountOnTask = mTask.getTimeOnTask();
            int seconds = amountOnTask.getSeconds();
            int minutes = amountOnTask.getMinutes();
            int hours = amountOnTask.getHours();
            int days = amountOnTask.getDays();

            tvnumberOfSeconds.setText(Integer.toString(seconds));
            tvnumberOfMinutes.setText(Integer.toString(minutes));
            tvnumberOfHours.setText(Integer.toString(hours));
            tvnumberOfDays.setText(Integer.toString(days));
//            tvnumberOfSeconds.setText(String.valueOf(i));
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putParcelableArrayList("key",contacts);

        getTaskInfoFromInput();

        outState.putString("taskName", mTask.getmTaskName());


        super.onSaveInstanceState(outState);
    }

    private void getTaskInfoFromInput() {
        String taskName = edtTaskName.getText().toString();
        Log.e("Debug", " taskName is : " + taskName);
        mTask.setmTaskName(taskName);

    }

    public void onStartButtonClicked() {
        mTask.setmLastStart(DateTime.now());
    }

    public void onStopButtonClicked() {
        mTask.setmLastStop(DateTime.now());
    }

    @Override
    public void onPause() {
        super.onPause();
        myTimer.cancel();  // Terminates this timer, discarding any currently scheduled tasks.
        myTimer.purge();
        getTaskInfoFromInput();
        TaskHolder.get(getActivity()).saveTasks();
    }
}
