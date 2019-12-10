package com.jnu.itime;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import com.jnu.itime.data.TimeAdapter;

public class TimeListViewActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_NEW_TIME = 902;
    private ListView timeListView;
    private List<TimeItem> listTime = new ArrayList<>();
    private TimeAdapter timeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_list_view);

        init();
        timeListView = (ListView)findViewById(R.id.time_list_view);
        timeAdapter = new TimeAdapter(TimeListViewActivity.this, R.layout.list_view_time_item, listTime);
        timeListView.setAdapter(timeAdapter);

        FloatingActionButton addItemButton = (FloatingActionButton)findViewById(R.id.add_time_button);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(TimeListViewActivity.this, "test", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TimeListViewActivity.this,AddTimeActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_TIME);
            }
        });

        timeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TimeListViewActivity.this, "test", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_NEW_TIME:
                if(resultCode == RESULT_OK){
                    int setYear = data.getIntExtra("year",0);
                    int setMonth = data.getIntExtra("month",0);
                    int setDay = data.getIntExtra("day",0);
                    int setHour = data.getIntExtra("hour",0);
                    int setMinute = data.getIntExtra("minute",0);
                    String uri = data.getStringExtra("imageUri");
                    String addTitle = data.getStringExtra("title");
                    String addDescription = data.getStringExtra("description");
                    listTime.add(new TimeItem(setYear,setMonth,setDay,setHour,setMinute,uri,addTitle,addDescription,"还有1天"));
                    timeAdapter.notifyDataSetChanged();
                }
                break;

        }
    }
}
