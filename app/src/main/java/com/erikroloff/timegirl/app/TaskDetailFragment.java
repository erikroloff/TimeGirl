package com.erikroloff.timegirl.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by erikroloff on 4/20/14.
 */
public class TaskDetailFragment extends Fragment {
    public static final String EXTRA_TASK_ID = "timegirl.TASK_ID";
    ArrayList<Task> tasks = null;
    Task mTask;
    private TextView tvnumberOfDays;
    private TextView tvnumberOfHours;
    private TextView tvnumberOfMinutes;
    private TextView tvnumberOfSeconds;
    private EditText edtTaskName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (TaskHolder.get(getActivity()).getTasks() == null) {
            Task task = new Task();
            task.setmTaskName("Test");

            TaskHolder.get(getActivity()).addTask(task);
        } else {
            tasks = TaskHolder.get(getActivity()).getTasks();
        }

        UUID taskId = (UUID)getArguments().getSerializable(EXTRA_TASK_ID);
        mTask = TaskHolder.get(getActivity()).getTask(taskId);
    }

    public static TaskDetailFragment newInstance(UUID taskId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TASK_ID, taskId);

        TaskDetailFragment fragment = new TaskDetailFragment();
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

        edtTaskName = (EditText) v.findViewById(R.id.edtTaskName);

        tvnumberOfDays = (TextView) v.findViewById(R.id.tvnumberOfDays);
        tvnumberOfHours = (TextView) v.findViewById(R.id.tvnumberOfHours);
        tvnumberOfMinutes = (TextView) v.findViewById(R.id.tvnumberOfMinutes);
        tvnumberOfSeconds = (TextView) v.findViewById(R.id.tvnumberOfSeconds);


        edtTaskName.setText(mTask.getmTaskName());

        // Need to add setText methods for the Days, Hours, Minutes, and Seconds




        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putParcelableArrayList("key",contacts);

        getTaskInfoFromInput();

        outState.putString("taskName", mTask.getmTaskName());


        super.onSaveInstanceState(outState);
    }

    private void getTaskInfoFromInput() {
        mTask.setmTaskName(edtTaskName.getText().toString());

    }

    @Override
    public void onPause() {
        super.onPause();
        TaskHolder.get(getActivity()).saveTasks();
    }
}
