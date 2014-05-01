package com.erikroloff.timegirl.app;

import android.support.v4.app.Fragment;
import android.view.View;

public class TaskActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TaskFragment();
    }

}