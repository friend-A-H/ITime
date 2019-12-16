package com.jnu.itime.data;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.itime.AddTimeActivity;
import com.jnu.itime.R;
import com.jnu.itime.TimeDetailActivity;
import com.jnu.itime.TimeListViewActivity;
import com.jnu.itime.data.model.MyTime;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;
import static com.jnu.itime.TimeListViewActivity.REQUEST_CODE_DETAIL_TIME;
import static com.jnu.itime.TimeListViewActivity.REQUEST_CODE_NEW_TIME;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeFragment extends Fragment {
    private TimeAdapter timeAdapter;
    TimeSaver timeSaver;
    private List<MyTime> listTime = new ArrayList<>();
    ListView timeListView;

    public TimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        timeSaver = new TimeSaver(getContext());
        listTime = timeSaver.load();
        timeAdapter = new TimeAdapter(getContext(), R.layout.list_view_time_item, listTime);
        timeListView = (ListView)view.findViewById(R.id.time_list_view);
        timeListView.setAdapter(timeAdapter);

        //每个Item点击事件
        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TimeDetailActivity.class);
                MyTime selectTime = (MyTime)timeAdapter.getItem(position);
                intent.putExtra("selectTime",selectTime);
                intent.putExtra("itemPosition",position);
                startActivityForResult(intent, REQUEST_CODE_DETAIL_TIME);
            }
        });

        //添加按钮点击事件
        FloatingActionButton addItemButton = (FloatingActionButton)view.findViewById(R.id.add_time_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TimeListViewActivity.this, "test", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), AddTimeActivity.class);
                intent.putExtra("parent","TimeListView");
                startActivityForResult(intent, REQUEST_CODE_NEW_TIME);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_NEW_TIME:
                if(resultCode == RESULT_OK){
                    MyTime addTime = (MyTime)data.getSerializableExtra("addTime");
                    listTime.add(addTime);
                    timeSaver.save();
                    timeAdapter.notifyDataSetChanged();
                }
                break;
            case REQUEST_CODE_DETAIL_TIME:
                if(resultCode == RESULT_FIRST_USER){
                    int deletePositon = data.getIntExtra("deletePosition",0);
                    listTime.remove(deletePositon);
                    timeSaver.save();
                    timeAdapter.notifyDataSetChanged();
                }
                else if(resultCode == RESULT_OK){
                    int itemPositon = data.getIntExtra("itemPositon",0);
                    MyTime modifyTime = (MyTime)data.getSerializableExtra("modifyTime");
                    listTime.set(itemPositon,modifyTime);
                    timeSaver.save();
                    timeAdapter.notifyDataSetChanged();
                }
        }
    }
}
