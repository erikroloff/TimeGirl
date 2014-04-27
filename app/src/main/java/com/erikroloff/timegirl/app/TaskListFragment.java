package com.erikroloff.timegirl.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import java.util.ArrayList;

/**
 * Created by erikroloff on 4/19/14.
 */
public class TaskListFragment extends ListFragment {
    private ArrayList<Task> mTasks;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.title_activity_task_list);
        mTasks = TaskHolder.get(getActivity()).getTasks();
        TaskAdapter adapter = new TaskAdapter(mTasks);
//        adapter.notifyDataSetChanged();
        setListAdapter(adapter);
        setRetainInstance(true);
        mSubtitleVisible = false;
    }
    @Override
    public void onResume() {
        super.onResume();

        TaskAdapter adapter = (TaskAdapter)getListAdapter();
        TaskHolder taskHolder = TaskHolder.get(getActivity());
        mTasks = taskHolder.getTasks();
        adapter.notifyDataSetChanged();
        Log.d("Debug", "List Frag Resumed");
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, parent, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mSubtitleVisible) {
                getActivity().getActionBar().setSubtitle(R.string.subtitle);
            }
        }

        ListView listView = (ListView)v.findViewById(android.R.id.list);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            registerForContextMenu(listView);
        } else {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.task_list_item_context, menu);
                    return true;
                }

                public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                      long id, boolean checked) {
                }
                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_task:
                            TaskAdapter adapter = (TaskAdapter)getListAdapter();
                            TaskHolder taskHolder = TaskHolder.get(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; i--) {
                                if (getListView().isItemChecked(i)) {
                                    taskHolder.deleteTask(adapter.getItem(i));
                                }
                            }
                            mode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }

                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                public void onDestroyActionMode(ActionMode mode) {

                }


            });

        }

        return v;
    }

    private class TaskAdapter extends ArrayAdapter<Task> {
        public TaskAdapter(ArrayList<Task> tasks) {
            super(getActivity(), android.R.layout.simple_list_item_1, tasks);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_task, null);
            }

            // configure the view for this Task
            Task t = getItem(position);

            TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.task_list_item_titleTextView);
            titleTextView.setText(t.getmTaskName());

            return convertView;
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // get the Task from the adapter
        Task t = ((TaskAdapter)getListAdapter()).getItem(position);
        // start an instance of TaskPagerActivity
        Intent i = new Intent(getActivity(), TaskPagerActivity.class);
        i.putExtra(TaskFragment.EXTRA_TASK_ID, t.getId());
        startActivityForResult(i, 0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task_list, menu);
        MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible && showSubtitle != null) {
            showSubtitle.setTitle(R.string.hide_subtitle);
        }
    }

    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_task:
                Intent i = new Intent(getActivity(), TaskActivity.class);

                startActivityForResult(i, 0);
                return true;
            case R.id.menu_item_show_subtitle:
                if (getActivity().getActionBar().getSubtitle() == null) {
                    getActivity().getActionBar().setSubtitle(R.string.subtitle);
                    mSubtitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
                }  else {
                    getActivity().getActionBar().setSubtitle(null);
                    mSubtitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.task_list_item_context, menu);
        Log.d("TaskListFragment", "Got to onCreateContextMenu");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        TaskAdapter adapter = (TaskAdapter)getListAdapter();
        Task task = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete_task:
                TaskHolder.get(getActivity()).deleteTask(task);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
