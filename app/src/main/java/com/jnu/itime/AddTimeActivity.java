package com.jnu.itime;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class AddTimeActivity extends AppCompatActivity {
    public static final int RC_CHOOSE_PHOTO = 901;
    private int nowYear, nowMonth, nowDay, nowHour, nowMinute;
    private int setYear, setMonth, setDay, setHour, setMinute;
    private EditText addTitleText,addDescriptionText;
    private String uri = "null";
    private String dateText,timeText;
    private TextView testTimeTextView, testPicTextView;
    private ImageView selectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        Toolbar mToolbar = findViewById(R.id.mToolbar);

        addTitleText = (EditText)findViewById(R.id.add_title_text);
        addDescriptionText = (EditText)findViewById(R.id.add_description_text);
        testTimeTextView = (TextView)findViewById(R.id.test_text_view);
        testPicTextView = (TextView)findViewById(R.id.test_pic_text_view);
        selectImage = (ImageView)findViewById(R.id.add_image_view);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_time_toolbar_comfirm:
                        Intent intent = new Intent();
                        intent.putExtra("year", setYear);
                        intent.putExtra("month", setMonth);
                        intent.putExtra("day", setDay);
                        intent.putExtra("hour", setHour);
                        intent.putExtra("minute", setMinute);
                        intent.putExtra("imageUri",uri);
                        intent.putExtra("title", addTitleText.getText().toString());
                        intent.putExtra("description", addDescriptionText.getText().toString());
                        setResult(RESULT_OK,intent);
                        AddTimeActivity.this.finish();
                        break;
                }
                return false;
            }
        });

        Calendar ca = Calendar.getInstance();
        nowYear = ca.get(Calendar.YEAR);
        nowMonth = ca.get(Calendar.MONTH);
        nowDay = ca.get(Calendar.DAY_OF_MONTH);
        nowHour = ca.get(Calendar.HOUR_OF_DAY);
        nowMinute = ca.get(Calendar.MINUTE);

        setYear = ca.get(Calendar.YEAR);
        setMonth = ca.get(Calendar.MONTH)+1;
        setDay = ca.get(Calendar.DAY_OF_MONTH);
        setHour = 0;
        setMinute = 0;

        testTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTimeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateText = String.format("%d年%d月%d日",year,monthOfYear+1,dayOfMonth);
                        setYear = year;
                        setMonth = monthOfYear+1;
                        setDay = dayOfMonth;
                        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTimeActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        timeText = String.format(" %d:%d",hourOfDay,minute);
                                        testTimeTextView.setText(dateText + " " + timeText);
                                        setHour = hourOfDay;
                                        setMinute = minute;
                                    }
                                }, nowHour, nowMinute, true);
                        timePickerDialog.show();
                    }
                }, nowYear, nowMonth, nowDay);
                datePickerDialog.show();
            }
        });

        testPicTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //动态申请获取访问 读写磁盘的权限
                if (ContextCompat.checkSelfPermission(AddTimeActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddTimeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
                }

                //打开相册
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //Intent.ACTION_GET_CONTENT = "android.intent.action.GET_CONTENT"
                intent.setType("image/*");
                startActivityForResult(intent, RC_CHOOSE_PHOTO); // 打开相册
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                uri = imageUri.toString();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(imageStream);
                selectImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
