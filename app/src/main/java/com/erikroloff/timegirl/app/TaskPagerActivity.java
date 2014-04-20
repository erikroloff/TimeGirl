package com.erikroloff.timegirl.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;
import java.util.UUID;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.view.ViewPager;

/**
 * Created by erikroloff on 4/19/14.
 */
public class TaskPagerActivity extends FragmentActivity {

    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        final ArrayList<Task> tasks = TaskHolder.get(this).getTasks();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return tasks.size();
            }
            @Override
            public Fragment getItem(int pos) {
                UUID contactId =  tasks.get(pos).getId();
                return TaskDetailFragment.newInstance(contactId);
            }
        });

        UUID contactId = (UUID)getIntent().getSerializableExtra(TaskDetailFragment.EXTRA_CONTACT_ID);
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(contactId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }


}
