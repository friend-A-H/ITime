package com.jnu.itime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class AddTimeActivity extends AppCompatActivity {
    private int nowYear, nowMonth, nowDay, nowHour, nowMinute;
    private String dateText,timeText;
    private TextView testTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        Toolbar mToolbar = findViewById(R.id.mToolbar);
        testTimeTextView = (TextView)findViewById(R.id.test_text_view);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true);

        Calendar ca = Calendar.getInstance();
        nowYear = ca.get(Calendar.YEAR);
        nowMonth = ca.get(Calendar.MONTH);
        nowDay = ca.get(Calendar.DAY_OF_MONTH);
        nowHour = ca.get(Calendar.HOUR_OF_DAY);
        nowMinute = ca.get(Calendar.MINUTE);

        testTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTimeActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                timeText = String.format(" %d:%d",hourOfDay,minute);
                                testTimeTextView.setText(dateText + " " + timeText);
                            }
                        }, nowHour, nowMinute, true);
                timePickerDialog.show();

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTimeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateText = String.format("%d年%d月%d日",year,monthOfYear+1,dayOfMonth);
                    }
                }, nowYear, nowMonth, nowDay);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_time_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
