package com.erikroloff.timegirl.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class TaskListActivity extends SingleFragmentActivity {

    @Override
    protected TaskListFragment createFragment() {
        return new TaskListFragment();
    }

}
