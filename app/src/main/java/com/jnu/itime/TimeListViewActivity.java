package com.jnu.itime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimeListViewActivity extends AppCompatActivity {
    ListView timeListView;
    private List<TimeItem> listTime = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_list_view);

        init();
        timeListView = (ListView)findViewById(R.id.time_list_view);
        TimeAdapter timeAdapter = new TimeAdapter(TimeListViewActivity.this, R.layout.list_view_time_item, listTime);
        timeListView.setAdapter(timeAdapter);
    }

    private void init() {
        listTime.add(new TimeItem(R.drawable.time,"a","b","c","啊啊啊啊啊"));
    }

    class TimeAdapter extends ArrayAdapter<TimeItem> {
        private int resourceId;

        public TimeAdapter(Context context, int resource, List<TimeItem> objects) {
            super(context, resource, objects);
            resourceId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TimeItem timeItem = getItem(position);//获取当前项的实例
            View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            ((ImageView) view.findViewById(R.id.time_cover)).setImageResource(timeItem.getImageId());
            ((TextView) view.findViewById(R.id.time_title)).setText(timeItem.getTitle());
            ((TextView) view.findViewById(R.id.time_date)).setText(timeItem.getDate());
            ((TextView) view.findViewById(R.id.time_description)).setText(timeItem.getDescription());
            ((TextView) view.findViewById(R.id.time_countdown)).setText(timeItem.getCountdown());
            return view;
        }
    }
}
