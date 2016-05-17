package com.myplanner.myplanner.MainScreenFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.myplanner.myplanner.R;
import java.util.ArrayList;
import java.util.List;

public class Reminders extends Fragment {
    private List<String> titles;
    private List<String> times;
    private List<String> dates;
    private List<String> bodies;
    private List<Integer> ids;

    ReminderRecycleViewAdapter adapter;

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------------- Interface -----------------------------------------
    // ---------------------------------------------------------------------------------------------

    ReminderInterface mCallback;
    public interface ReminderInterface {
        void reminderClickedAction(int reminderID);
    }

    // ---------------------------------------------------------------------------------------------
    // -------------------------------- Fragment Override Functions --------------------------------
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (ReminderInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.basic_recyclerview, container, false);
        adapter = new ReminderRecycleViewAdapter();
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(adapter);
        Log.i("Reminders", "View created");
        return rv;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    // ---------------------------------------------------------------------------------------------
    // --------------------------------- Functions Called by Main ----------------------------------
    // ---------------------------------------------------------------------------------------------

    public void reloadData() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void clearReminderLists() {
        titles.clear();
        times.clear();
        dates.clear();
        bodies.clear();
        ids.clear();
    }

    public void addReminderInfo(String title, String time, String date, String body, int id) {
        titles.add(title);
        times.add(time);
        dates.add(date);
        bodies.add(body);
        ids.add(id);
    }

    // ---------------------------------------------------------------------------------------------
    // --------------------------- Local Class Required for RecycleView ----------------------------
    // ---------------------------------------------------------------------------------------------

    class ReminderRecycleViewAdapter extends RecyclerView.Adapter<ReminderRecycleViewAdapter.ViewHolder> {

        // class to store the information for one element in the RecycleView
        class ViewHolder extends RecyclerView.ViewHolder {
            private final View view;
            private final TextView title;
            private final TextView time;
            private final TextView date;
            private final TextView body;
            private int id;

            private ViewHolder(View nview) {
                super(nview);
                view = nview;
                title = (TextView) view.findViewById(R.id.reminder_title_txt);
                time = (TextView) view.findViewById(R.id.reminder_time_txt);
                date = (TextView) view.findViewById(R.id.reminder_date_txt);
                body = (TextView) view.findViewById(R.id.reminder_body_txt);
                id = -1;

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCallback.reminderClickedAction(id);
                    }
                });
            }
        }

        private ReminderRecycleViewAdapter() {
            this.notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_reminder_list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.title.setText(titles.get(position));
            holder.time.setText(times.get(position));
            holder.date.setText(dates.get(position));
            holder.body.setText(bodies.get(position));
            holder.id = ids.get(position);
        }

        @Override
        public int getItemCount() {
            if (titles == null || titles.isEmpty()) {
                return 0;
            } else {
                return titles.size();
            }
        }
    }
}
