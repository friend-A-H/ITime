package com.jnu.itime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.itime.data.model.MyTime;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeDetailActivity extends AppCompatActivity {
    private MyTime selectTime;
    private ImageView detailTimeSetIv;
    private TextView detailTimeTitle, detailTimeDescription;
    private TextView detailTimeSetTime, detailTimeCountdown;
    private String setDateText, setWeek;
    private int difDay, difHour, difMin, difSec;
    private Boolean setDateIsBigger = true;
    private int itemPosition;
    Timer mTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_detail);
        selectTime = (MyTime)getIntent().getSerializableExtra("selectTime");
        itemPosition = getIntent().getIntExtra("itemPosition",0);

        detailTimeSetIv = (ImageView)findViewById(R.id.detail_time_set_image_view);
        detailTimeTitle = (TextView)findViewById(R.id.detail_fixed_title_text);
        detailTimeDescription = (TextView)findViewById(R.id.detail_fixed_description_text);
        detailTimeSetTime = (TextView)findViewById(R.id.detail_fixed_set_time_text);
        detailTimeCountdown = (TextView)findViewById(R.id.detail_fixed_countdown_text);
        calculateDifTime(selectTime.getYear(), selectTime.getMonth(), selectTime.getDay(), selectTime.getHour(), selectTime.getMinute(), 0);
        startRun();

        Toolbar detailToolbar = findViewById(R.id.detail_time_toolbar);
        setSupportActionBar(detailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true);

        if(selectTime.getImageUri().equals("null")){
            detailTimeSetIv.setImageResource(R.drawable.time);
        }
        else{
            Bitmap bitmap = null;
            try {
                Uri imageUri = Uri.parse(selectTime.getImageUri());
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(imageStream);
                detailTimeSetIv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        detailTimeTitle.setText(selectTime.getTitle());
        detailTimeDescription.setText(selectTime.getDescription());

        //显示设置的时间
        setDateText = String.valueOf(selectTime.getYear()) + "年" + String.valueOf(selectTime.getMonth()) + "月" + String.valueOf(selectTime.getDay() + "日");
        if(selectTime.getHour() != 0 || selectTime.getMinute() != 0){
            setDateText += String.valueOf("  " + selectTime.getHour()) + ":" + String.valueOf(selectTime.getMinute());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(selectTime.getYear(), selectTime.getMonth()-1, selectTime.getDay());
        int wek = calendar.get(Calendar.DAY_OF_WEEK);
        if (wek == 1) {
            setWeek = "周日";
        }
        if (wek == 2) {
            setWeek = "周一";
        }
        if (wek == 3) {
            setWeek = "周二";
        }
        if (wek == 4) {
            setWeek = "周三";
        }
        if (wek == 5) {
            setWeek = "周四";
        }
        if (wek == 6) {
            setWeek = "周五";
        }
        if (wek == 7) {
            setWeek = "周六";
        }
        setDateText = setDateText + "  " + setWeek;
        detailTimeSetTime.setText(setDateText);

        detailToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.detail_time_toolbar_delete:
                        new AlertDialog.Builder(TimeDetailActivity.this)
                                .setTitle("提示")
                                .setMessage("是否删除该时刻？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent();
                                        intent.putExtra("deletePosition",itemPosition);
                                        setResult(RESULT_FIRST_USER,intent);
                                        TimeDetailActivity.this.finish();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .create().show();
                        break;
                    case R.id.detail_time_toolbar_share:
                        break;
                    case R.id.detail_time_toolbar_modify:
                        break;
                }
                return false;
            }
        });
    }

    //设置toolbar的menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.detail_time_toolbar_menu, menu);
        return true;
    }

    //设置返回点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void calculateDifTime(int setYear, int setMonth, int setDay, int setHour, int setMin, int setSec){
        Date nowDate = new Date(System.currentTimeMillis());
        Calendar setCalendar=Calendar.getInstance();
        setCalendar.set(setYear,setMonth-1,setDay,setHour,setMin,setSec);
        Date setDate= setCalendar.getTime();
        long difMs;
        if(setDate.getTime() - nowDate.getTime() > 0){
            setDateIsBigger = true;
            difMs = setDate.getTime() - nowDate.getTime();
        }
        else if(setDate.getTime() - nowDate.getTime() < 0){
            setDateIsBigger = false;
            difMs = nowDate.getTime() - setDate.getTime();
        }
        else{
            setDateIsBigger = false;
            difMs = 0;
        }
        difDay = (int)(difMs / (24 * 3600 * 1000));
        difHour = (int)((difMs - difDay * 24 * 3600 * 1000) / (3600 * 1000));
        difMin = (int)((difMs - difDay * 24 * 3600 * 1000 - difHour * 3600 * 1000) / (60 * 1000));
        difSec = (int)((difMs - difDay * 24 * 3600 * 1000 - difHour * 3600 * 1000 - difMin * 60 * 1000) / 1000);
    }

    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                countdownComputeTime();
                detailTimeCountdown.setText(getTv(difDay) + "天 " + getTv(difHour) + "小时" + getTv(difMin) + "分钟" + getTv(difSec) + "秒");
                if(difDay == 0 && difHour == 0 && difMin == 0 && difSec ==0){
                    setDateIsBigger = false;
                }
            }
            else if(msg.what == 1){
                countupComputeTime();
                detailTimeCountdown.setText(getTv(difDay) + "天 " + getTv(difHour) + "小时" + getTv(difMin) + "分钟" + getTv(difSec) + "秒");
            }
        }
    };

    private String getTv(int l){
        if(l >= 10){
            return l + "";
        }else if(l < 10 && l > 0){
            return "0" + l;//小于10,,前面补位一个"0"
        }
        else return "0";
    }

    private void startRun() {
        TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                if(setDateIsBigger == true) {
                    message.what = 0;
                }
                else message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        mTimer.schedule(mTimerTask,0,1000);
    }

    /**
     * 倒计时计算
     */
    private void countdownComputeTime() {
        difSec--;
        if (difSec < 0) {
            difMin--;
            difSec = 59;
            if (difMin < 0) {
                difMin = 59;
                difHour--;
                if (difHour < 0) {
                    // 倒计时结束
                    difHour = 23;
                    difDay--;
                    if(difDay < 0){
                        // 倒计时结束
                        difDay = 0;
                        difHour= 0;
                        difHour = 0;
                        difSec = 0;
                    }
                }
            }
        }
    }

    /**
     * 过期的计算方式
     */
    private void countupComputeTime() {
        difSec++;
        if (difSec == 60) {
            difMin++;
            difSec = 0;
            if (difMin == 60) {
                difMin = 0;
                difHour++;
                if (difHour == 24) {
                    difHour = 0;
                    difDay++;
                }
            }
        }
    }
}
