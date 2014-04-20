package com.erikroloff.timegirl.app;

import android.view.View;

public class TaskActivity extends SingleFragmentActivity {

    @Override
    protected TaskFragment createFragment() {
        return new TaskFragment();
    }

}