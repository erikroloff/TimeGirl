package com.erikroloff.timegirl.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class TaskListActivity extends SingleFragmentActivity
    implements TaskListFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    public void onTaskSelected(Task task) {
        if (findViewById(R.id.detailFragmentContainer) == null) {
            // start an instance of TaskPagerActivity
            Intent i = new Intent(this, TaskPagerActivity.class);
            i.putExtra(TaskDetailFragment.EXTRA_TASK_ID, task.getId());
            startActivityForResult(i, 0);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = TaskDetailFragment.newInstance(task.getId());

            if (oldDetail != null) {
                ft.remove(oldDetail);
            }

            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }

    public void onTaskUpdated(Task task) {
        FragmentManager fm = getSupportFragmentManager();
        TaskListFragment listFragment = (TaskListFragment)
                fm.findFragmentById(R.id.fragmentContainer);
        listFragment.updateUI();
    }

}
